package com.nkt.yummyrecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
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
}
