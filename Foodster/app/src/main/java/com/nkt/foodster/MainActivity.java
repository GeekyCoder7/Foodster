package com.nkt.foodster;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.core.app.ShareCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;
    private AppBarConfiguration mAppBarConfiguration;
    private boolean isAdmin = false;
    SharedPreferences sharedpreferences;
    NavigationView navigationView;
    public static final String MyPREFERENCES = "MyPrefs" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isAdmin = extras.getBoolean("isAdmin");
        }

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putBoolean("isAdmin",isAdmin);
        editor.apply();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.hide();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewCuisineActivity.class);
                startActivity(intent);
            }
        });

        if(isAdmin){
            Toast.makeText(this, "Welcome admin", Toast.LENGTH_SHORT).show();
            fab.show();
        }
        if(!isAdmin){
            drawer = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.nav_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.

            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_favorite_cuisines, R.id.nav_favorite_recipes)
                    .setDrawerLayout(drawer)
                    .build();

            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        if(!isAdmin){
            menu.removeItem(R.id.switchId);
        }
        else{

            MenuItem item = (MenuItem) menu.findItem(R.id.switchId);

            item.setActionView(R.layout.switch_item);
            Switch switchAB = item.getActionView().findViewById(R.id.switchAB);
            switchAB.setChecked(true);

            switchAB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
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
    public void onBackPressed() {
        if(isAdmin){
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this)
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
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    });

            alertDialog.show();
        }
        else{
            if(drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
            else{
                super.onBackPressed();
            }
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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

    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this)
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
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });

                    alertDialog.show();
        }
//        else if(item.getItemId() == R.id.admin_toggle){
//            Toast.makeText(this, "hi "+!item.isChecked(), Toast.LENGTH_SHORT).show();
//                item.setChecked(!item.isChecked());
//                SharedPreferences settings = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
//                SharedPreferences.Editor editor = settings.edit();
//                editor.putBoolean("isAdmin", item.isChecked());
//                editor.apply();
//                return true;
//        }
        return super.onOptionsItemSelected(item);
    }




}

