package com.nkt.foodster;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
String instructions;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String item_name = extras.getString("item_name");
            String ingredients = extras.getString("ingredients");
            instructions = extras.getString("instructions");
            TextView textView = (TextView) findViewById(R.id.txtDashboard);
            textView.setText(item_name);


            TextView ingredientsTextView = (TextView) findViewById(R.id.ingredientsTextView);
            ingredientsTextView.setText(ingredients);

            Button button = (Button) findViewById(R.id.instructionButton);
            button.setOnClickListener(v->{
                if(instructions.length()>0){
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(RecipeActivity.this)
                            .setTitle("Instructions")
                            .setMessage(instructions)
                            .setNegativeButton("Got it",new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog,int which){
                                    //cancel

                                }
                            });


                    alertDialog.show();
                }
                else{
                    Toast.makeText(this, "No instructions available.\n Check again later!", Toast.LENGTH_SHORT).show();
                }

            });


            //The key argument here must match that used in the other activity
        }
    }




    public void openGoogleMaps(View view){
        String uri = "http://maps.google.com/maps?daddr=supermarket";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    public void shareRecipe(View view){
        Bundle extras = getIntent().getExtras();
        String item_name = extras.getString("item_name");
        String ingredients = extras.getString("ingredients");
        ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setChooserTitle("Chooser title")
                .setText(item_name+"\nIngredients:\n"+ingredients)
                .startChooser();
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

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(RecipeActivity.this)
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
                            Intent intent = new Intent(RecipeActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    });

            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

}
