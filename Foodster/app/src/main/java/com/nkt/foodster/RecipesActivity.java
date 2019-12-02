package com.nkt.foodster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ShareCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

import static com.nkt.foodster.MainActivity.MyPREFERENCES;

public class RecipesActivity extends AppCompatActivity {
    String cuisine_type="";
    SharedPreferences sharedpreferences;
    private boolean isAdmin = false;
    private RecyclerView mRecyclerView;
    private String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
//        Button mapButton = (Button) findViewById(R.id.mapButton);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cuisine_type = extras.getString("cuisine_type");
            key = extras.getString("key");
            System.out.println("Parent key is: "+key);
//            TextView textView = (TextView) findViewById(R.id.txtDashboard);
//            textView.setText(cuisine_type+" cuisine");


//            mapButton.setText("Explore "+cuisine_type+" cuisines near you");
            //The key argument here must match that used in the other activity
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_recipes);

        FloatingActionButton fab = findViewById(R.id.fab1);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipesActivity.this, NewRecipeActivity.class);
                intent.putExtra("key",key);
                startActivity(intent);
            }
        });
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("parentKey",key);
        editor.apply();
        System.out.println("Parent key is: "+sharedpreferences.getString("parentKey",""));
        if(sharedpreferences.getBoolean("is_really_admin", false)){
            fab.show();
            System.out.println("I am an admin "+sharedpreferences.getBoolean("is_really_admin", false));
        }

       new FirebaseDatabaseHelper().readRecipes(cuisine_type,new FirebaseDatabaseHelper.DataStatus1() {
            @Override
            public void DataIsLoaded1(List<Recipe> recipes, List<String> keys) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("recipe_size",recipes.size());
                editor.apply();
                new RecyclerView_Config().setConfig1(mRecyclerView, RecipesActivity.this, recipes, keys);
            }

            @Override
            public void DataIsInserted1() {

            }

            @Override
            public void DataIsUpdated1() {

            }

            @Override
            public void DataIsDeleted1() {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if(!isAdmin&&!sharedpreferences.getBoolean("is_really_admin", false)){
            menu.removeItem(R.id.switchId);
        }
        else{

            MenuItem item = (MenuItem) menu.findItem(R.id.switchId);

            item.setActionView(R.layout.switch_item);
            Switch switchAB = item.getActionView().findViewById(R.id.switchAB);
            if(isAdmin){
                switchAB.setChecked(true);
            }



            switchAB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {

                    if (isChecked) {
                        isAdmin= true;
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean("isAdmin", true);
                        editor.putBoolean("is_really_admin", true);
                        Log.d("key","Hellooo3"+ sharedpreferences.getBoolean("isAdmin", false));
                        editor.apply();
                        Toast.makeText(getApplication(), "Admin mode: ON", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        isAdmin=false;
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.remove("isAdmin");
                        editor.putBoolean("is_really_admin", true);
                        editor.apply();

                        Log.d("key","Hellooo3"+ sharedpreferences.getBoolean("isAdmin", false));
                        Toast.makeText(getApplication(), "Admin mode: OFF", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            });

        }

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            //Do your stuff here
            ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setChooserTitle("Chooser title")
                    .setText("http://play.google.com/store/apps/details?id=" + this.getPackageName())
                    .startChooser();
            return true;
        }
        else if (item.getItemId() == R.id.action_logout){

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(RecipesActivity.this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog,int which){
                            //cancel

                        }
                    })
                    .setPositiveButton("LOGOUT",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog,int which){
                            LoginActivity lga = new LoginActivity();
                            lga.Logout();
                            Intent intent = new Intent(RecipesActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    });

            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

}
