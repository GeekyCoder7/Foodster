package com.nkt.foodster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewCuisineActivity extends AppCompatActivity {

    private EditText cuisine_type, imageURL;
    private Button add_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cuisine);

        cuisine_type = (EditText) findViewById(R.id.cuisineTitle_editText);
        imageURL = (EditText) findViewById(R.id.cuisineImageURL_editText);

        add_btn = (Button) findViewById(R.id.addButton);

        add_btn.setOnClickListener(v->{
            Cuisine cuisine = new Cuisine();
            cuisine.setTitle(cuisine_type.getText().toString());
            cuisine.setImageURL(imageURL.getText().toString());
            cuisine.setRecipes(null);
            new FirebaseDatabaseHelper().addCuisine(cuisine, new FirebaseDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<Cuisine> cuisines, List<String> keys) {
                    
                }

                @Override
                public void DataIsInserted() {
                    Toast.makeText(NewCuisineActivity.this, "Cuisine added successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                @Override
                public void DataIsUpdated() {

                }

                @Override
                public void DataIsDeleted() {

                }
            });
        });

    }
}
