package com.nkt.yummyrecipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String item_name = extras.getString("item_name");
            String ingredients = extras.getString("ingredients");
            TextView textView = (TextView) findViewById(R.id.txtDashboard);
            textView.setText(item_name);


            TextView ingredientsTextView = (TextView) findViewById(R.id.ingredientsTextView);
            ingredientsTextView.setText(ingredients);

            //The key argument here must match that used in the other activity
        }
    }


    public void openGoogleMaps(View view){
        String uri = "http://maps.google.com/maps?daddr=supermarket";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }
}
