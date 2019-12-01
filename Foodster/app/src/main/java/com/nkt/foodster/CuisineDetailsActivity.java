package com.nkt.foodster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class CuisineDetailsActivity extends AppCompatActivity {

    private EditText cuisine_type;
    private Button update_btn, delete_btn;

    private String key;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine_details);

        key = getIntent().getStringExtra("key");
        title = getIntent().getStringExtra("cuisine_type");
        System.out.println("Helloo"+title);

        cuisine_type = (EditText) findViewById(R.id.cuisineTitle_editText);
        cuisine_type.setText(title);
        update_btn = (Button) findViewById(R.id.updateButton);
        delete_btn = (Button) findViewById(R.id.deleteButton);

        update_btn.setOnClickListener(v->{
//            Cuisine cuisine = new Cuisine();
//            cuisine.setTitle(cuisine_type.getText().toString());
            new FirebaseDatabaseHelper().updateCuisineTitle(key, cuisine_type.getText().toString(), new FirebaseDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<Cuisine> cuisines, List<String> keys) {

                }

                @Override
                public void DataIsInserted() {

                }

                @Override
                public void DataIsUpdated() {
                    Toast.makeText(CuisineDetailsActivity.this, "Cuisine updated successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                @Override
                public void DataIsDeleted() {

                }
            });
        });

        delete_btn.setOnClickListener(v->{
            new FirebaseDatabaseHelper().deleteCuisine(key, new FirebaseDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<Cuisine> cuisines, List<String> keys) {

                }

                @Override
                public void DataIsInserted() {

                }

                @Override
                public void DataIsUpdated() {

                }

                @Override
                public void DataIsDeleted() {
                    Toast.makeText(CuisineDetailsActivity.this, "Cuisine deleted successfully!", Toast.LENGTH_SHORT).show();
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
