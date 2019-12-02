package com.nkt.foodster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {

    private EditText titleEditTxt, image_urlEditTxt, caloriesEditTxt, ingredientsEditTxt, instructionsEditTxt, idEditText ;
    private Button update_btn, delete_btn;

    private String parentKey,key, id;
    private String title, image_url_txt, ingredients, calories,instructions;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        id = getIntent().getStringExtra("id");
        key = getIntent().getStringExtra("key");
        title = getIntent().getStringExtra("title");
        image_url_txt = getIntent().getStringExtra("imageURL");
        ingredients = getIntent().getStringExtra("ingredients");
        calories = getIntent().getStringExtra("calories");
        instructions = getIntent().getStringExtra("instructions");
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        parentKey = sharedpreferences.getString("parentKey", "");
        System.out.println("Parent key is: "+parentKey);


        titleEditTxt = (EditText) findViewById(R.id.recipeTitle_editText);
        image_urlEditTxt = (EditText) findViewById(R.id.recipeImageURL_editText);
        caloriesEditTxt = (EditText) findViewById(R.id.recipeCalories_editText);
        ingredientsEditTxt = (EditText) findViewById(R.id.recipeIngredients_editText);
        instructionsEditTxt = (EditText) findViewById(R.id.recipeInstructions_editText);
        idEditText = (EditText) findViewById(R.id.recipeId_editText);

        titleEditTxt.setText(title);
        image_urlEditTxt.setText(image_url_txt);
        caloriesEditTxt.setText(calories);
        ingredientsEditTxt.setText(ingredients);
        instructionsEditTxt.setText(instructions);
        idEditText.setText(id);
        System.out.println("Recipe key is: "+id);
        update_btn = (Button) findViewById(R.id.updateButton);
        delete_btn = (Button) findViewById(R.id.deleteButton);

        update_btn.setOnClickListener(v->{

            new FirebaseDatabaseHelper().updateRecipe(parentKey,idEditText.getText().toString(),titleEditTxt.getText().toString(), image_urlEditTxt.getText().toString(),
                    caloriesEditTxt.getText().toString(),ingredientsEditTxt.getText().toString(),
                    instructionsEditTxt.getText().toString(),new FirebaseDatabaseHelper.DataStatus1() {
                @Override
                public void DataIsLoaded1(List<Recipe> recipes, List<String> keys) {

                }

                @Override
                public void DataIsInserted1() {

                }

                @Override
                public void DataIsUpdated1() {
                    Toast.makeText(RecipeDetailsActivity.this, "Recipe updated successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                @Override
                public void DataIsDeleted1() {

                }
            });
        });

        delete_btn.setOnClickListener(v->{
            new FirebaseDatabaseHelper().deleteRecipe(parentKey,key, new FirebaseDatabaseHelper.DataStatus1() {
                @Override
                public void DataIsLoaded1(List<Recipe> recipes, List<String> keys) {

                }

                @Override
                public void DataIsInserted1() {

                }

                @Override
                public void DataIsUpdated1() {

                }

                @Override
                public void DataIsDeleted1() {
                    Toast.makeText(RecipeDetailsActivity.this, "Recipe deleted successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
            });
        });


    }

    private int getIndex_SpinnerItem(Spinner spinner, String item){
        int index = 0;
        for(int i = 0; i < spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).equals(item)){
                index = i;
                break;
            }
        }
        return index;
    }

}
