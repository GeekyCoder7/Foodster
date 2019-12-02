package com.nkt.foodster;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.nkt.foodster.MainActivity.MyPREFERENCES;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    SharedPreferences sharedPreferences;
    private List<JSONObject> obj;
    private DatabaseReference mReferenceCuisines;
    private List<Cuisine> cuisines = new ArrayList<>();
    private List<Recipe> recipes = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Cuisine> cuisines, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public interface DataStatus1{
        void DataIsLoaded1(List<Recipe> recipes, List<String> keys);
        void DataIsInserted1();
        void DataIsUpdated1();
        void DataIsDeleted1();
    }


    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        obj=new ArrayList<JSONObject>();
        mReferenceCuisines = mDatabase.getReference("cuisines");

    }

    public void readRecipes(String cuisine_type,final DataStatus1 dataStatus){
        mReferenceCuisines.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cuisines.clear();
                recipes.clear();
                List<String> keys = new ArrayList<>();
                Log.d("myTag", "Hellooo1\n"+mReferenceCuisines.child("recipes"));
                Log.d("myTag", "Hellooo\n"+dataSnapshot.getValue());
                Log.d("myTag", "Hellooo\n"+dataSnapshot.getChildren());

                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
//                    Log.d("myTag", "Hellooo\n"+keyNode.getValue(Cuisine.class));
                    keys.add(keyNode.getKey());
                    Cuisine cuisine = keyNode.getValue(Cuisine.class);
                    for (int i = 0; cuisine != null && cuisine.getRecipes() != null && i < cuisine.getRecipes().size(); i++) {
                        if (cuisine.getRecipes().get(i) != null) {
                            Recipe recipe = new Recipe(cuisine.getRecipes().get(i).getTitle(), cuisine.getRecipes().get(i).getIngredients(), cuisine.getRecipes().get(i).getCalories(), cuisine.getRecipes().get(i).getImageURL(), cuisine.getRecipes().get(i).getInstructions());

                            if (cuisine_type.equalsIgnoreCase(cuisine.getTitle())) {
                                recipes.add(recipe);
                            }
                        }

                    }
                }
                dataStatus.DataIsLoaded1(recipes,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void readCuisines(final DataStatus dataStatus){
        mReferenceCuisines.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cuisines.clear();
                List<String> keys = new ArrayList<>();
                Log.d("myTag", "Hellooo\n"+dataSnapshot.getValue());


                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
//                    Log.d("myTag", "Hellooo\n"+keyNode.getValue(Cuisine.class));
                    keys.add(keyNode.getKey());
                    Cuisine cuisine = keyNode.getValue(Cuisine.class);
                    cuisines.add(cuisine);
                }
                dataStatus.DataIsLoaded(cuisines,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addCuisine(Cuisine cuisine, final DataStatus dataStatus){
        String key = mReferenceCuisines.push().getKey();
//        mReferenceCuisines.child(key).child("title").setValue(cuisine)
        mReferenceCuisines.child(key).setValue(cuisine)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsInserted();
                    }
                });
    }

    public void addRecipe(String parentKey, String key, Recipe recipe, final DataStatus1 dataStatus){

        mReferenceCuisines.child(parentKey).child("recipes").child(key).setValue(recipe)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsInserted1();
                    }
                });
    }


    public void updateCuisine(String key,  String newTitleCuisine, String newImageURL, final DataStatus dataStatus){
        mReferenceCuisines.child(key).child("title").setValue(newTitleCuisine)
                .addOnSuccessListener(new OnSuccessListener<Void>() {



                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsUpdated();
                    }
                });


                mReferenceCuisines.child(key).child("imageURL").setValue(newImageURL)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {



                            @Override
                            public void onSuccess(Void aVoid) {
                                dataStatus.DataIsUpdated();
                            }
                        });


    }


    public void updateRecipe(String parentKey, String key,  String newRecipeTitle, String newImageURL, String newCalories, String newIngredients,
                             String newInstructions, final DataStatus1 dataStatus){
        mReferenceCuisines.child(parentKey).child("recipes").child(key).child("title").setValue(newRecipeTitle)
                .addOnSuccessListener(new OnSuccessListener<Void>() {



                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsUpdated1();
                    }
                });

        mReferenceCuisines.child(parentKey).child("recipes").child(key).child("calories").setValue(newCalories)
                .addOnSuccessListener(new OnSuccessListener<Void>() {



                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsUpdated1();
                    }
                });


        mReferenceCuisines.child(parentKey).child("recipes").child(key).child("ingredients").setValue(newIngredients)
                .addOnSuccessListener(new OnSuccessListener<Void>() {



                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsUpdated1();
                    }
                });

        mReferenceCuisines.child(parentKey).child("recipes").child(key).child("instructions").setValue(newInstructions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {



                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsUpdated1();
                    }
                });



        mReferenceCuisines.child(parentKey).child("recipes").child(key).child("imageURL").setValue(newImageURL)
                .addOnSuccessListener(new OnSuccessListener<Void>() {



                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsUpdated1();
                    }
                });


    }

    public void deleteRecipe(String parentKey, String key,  final DataStatus1 dataStatus){
        mReferenceCuisines.child(parentKey).child("recipes").child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsDeleted1();
                    }
                });
    }

    public void deleteCuisine(String key, final DataStatus dataStatus){
        mReferenceCuisines.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsDeleted();
                    }
                });
    }
}
