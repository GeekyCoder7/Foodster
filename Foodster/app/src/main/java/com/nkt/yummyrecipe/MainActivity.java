package com.nkt.yummyrecipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ShareCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity{

    private String sharedPrefFile="com.example";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SharedPreferences mPreferences=getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        if(savedInstanceState!=null){
            //load data from savedInstanceState
            //example String count = savedInstanceState.getString("count");
            String ID = mPreferences.getString("ID","");
        }

        Button hamMenu = findViewById(R.id.actionBtn);
        final DrawerLayout drawer =  findViewById(R.id.drawer_layout);

        hamMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });



        CardView americanCV = (CardView) findViewById(R.id.americanCard);
        americanCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor preferenceEditor=mPreferences.edit();
                preferenceEditor.putString("ID","201701143");
                preferenceEditor.apply();
                Intent intent = new Intent(view.getContext(),RecipesActivity.class);
                intent.putExtra("cuisine_type", "American");
                view.getContext().startActivity(intent);
            }
        });

        CardView italianCV = (CardView) findViewById(R.id.italianCard);
        italianCV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Connect to Provider")
                        .setMessage("Hello Alert!")
                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog,int which){
                            //cancel
                             }
                        })
                        .setPositiveButton("OK",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog,int which){
                                //OK
                            }
                        });

                alertDialog.show();

            }
        });


        CardView armenianCV = (CardView) findViewById(R.id.armenianCard);
        armenianCV.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view){
                Uri uri = Uri.parse("tel:71531019");
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);

            }
        });

    }

   

    public void onResume(){ //as well as onRestart(), onStart(), onStop(), onPause(), onDestroy()
        super.onResume();
    }




}
