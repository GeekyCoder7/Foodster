package com.nkt.foodster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import static com.nkt.foodster.MainActivity.MyPREFERENCES;

public class NewRecipeActivity extends AppCompatActivity {

    private EditText titleEditTxt, image_urlEditTxt, caloriesEditTxt, ingredientsEditTxt, instructionsEditTxt ;
    private Button add_btn;
    private String parentKey, key;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        titleEditTxt = (EditText) findViewById(R.id.recipeTitle_editText);
        image_urlEditTxt = (EditText) findViewById(R.id.recipeImageURL_editText);
        caloriesEditTxt = (EditText) findViewById(R.id.recipeCalories_editText);
        ingredientsEditTxt = (EditText) findViewById(R.id.recipeIngredients_editText);
        instructionsEditTxt = (EditText) findViewById(R.id.recipeInstructions_editText);

        parentKey = getIntent().getStringExtra("key");
        System.out.println("Parent: "+ parentKey);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        System.out.println("Current: "+ sharedPreferences.getInt("recipe_size",-1));

        key = sharedPreferences.getInt("recipe_size",-1)+"";
        System.out.println("HELA: "+key);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("recipe_size");
        editor.apply();
        add_btn = (Button) findViewById(R.id.addButton);

        add_btn.setOnClickListener(v->{
            Recipe recipe = new Recipe();
            recipe.setTitle(titleEditTxt.getText().toString());
            recipe.setCalories(caloriesEditTxt.getText().toString());
            recipe.setIngredients(ingredientsEditTxt.getText().toString());
            recipe.setInstructions(instructionsEditTxt.getText().toString());
            recipe.setImageURL(image_urlEditTxt.getText().toString());
//            recipe.setId(key);
            new FirebaseDatabaseHelper().addRecipe(parentKey, key, recipe, new FirebaseDatabaseHelper.DataStatus1() {
                @Override
                public void DataIsLoaded1(List<Recipe> recipes, List<String> keys) {

                }

                @Override
                public void DataIsInserted1() {
                    Toast.makeText(NewRecipeActivity.this, "Recipe added successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                @Override
                public void DataIsUpdated1() {

                }

                @Override
                public void DataIsDeleted1() {

                }
            });
        });

    }
}
