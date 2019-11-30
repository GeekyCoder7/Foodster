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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RecipesActivity extends AppCompatActivity {
    String cuisine_type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        Button mapButton = (Button) findViewById(R.id.mapButton);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cuisine_type = extras.getString("cuisine_type");
            TextView textView = (TextView) findViewById(R.id.txtDashboard);
            textView.setText(cuisine_type+" cuisine");


            mapButton.setText("Explore "+cuisine_type+" cuisines near you");
            //The key argument here must match that used in the other activity
        }

        CardView burgerCV = (CardView) findViewById(R.id.hamburger);
        burgerCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),RecipeActivity.class);
                intent.putExtra("item_name", "Hamburger");
                intent.putExtra("ingredients", "1. 1 burger bun\n2. 1 beef\n3. 1 fresh tomato");
                view.getContext().startActivity(intent);
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String uri = "https://www.google.com/maps/search/"+cuisine_type+"+restaurants";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });


        ImageView likeImageView = (ImageView) findViewById(R.id.likeImageView) ;
        likeImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
//                String uri = "https://www.google.com/maps/search/"+cuisine_type+"+restaurants";
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
