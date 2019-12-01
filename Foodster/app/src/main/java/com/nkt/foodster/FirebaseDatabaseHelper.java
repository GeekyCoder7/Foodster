package com.nkt.foodster;

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

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
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
                List<String> keys = new ArrayList<>();
                Log.d("myTag", "Hellooo1\n"+mReferenceCuisines.child("recipes"));
                Log.d("myTag", "Hellooo\n"+dataSnapshot.getValue());
                Log.d("myTag", "Hellooo\n"+dataSnapshot.getChildren());

                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
//                    Log.d("myTag", "Hellooo\n"+keyNode.getValue(Cuisine.class));
                    keys.add(keyNode.getKey());
                    Cuisine cuisine = keyNode.getValue(Cuisine.class);
                    Log.d("myTag", "Hellooo2\n"+cuisine.getRecipes().get(0).getIngredients());
                    Recipe recipe = new Recipe(cuisine.getRecipes().get(0).getTitle(),cuisine.getRecipes().get(0).getIngredients(),cuisine.getRecipes().get(0).getCalories());

                    if(cuisine_type.equalsIgnoreCase(cuisine.getTitle())){
                        recipes.add(recipe);
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
                Log.d("myTag", "Hellooo\n"+dataSnapshot.getChildren());

                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
//                    Log.d("myTag", "Hellooo\n"+keyNode.getValue(Cuisine.class));
                    keys.add(keyNode.getKey());
                    Cuisine cuisine = keyNode.getValue(Cuisine.class);
//                    Log.d("myTag", "Hellooo\n"+cuisine.getRecipes().get(0).getIngredients());
//                    Log.d("myTag", "Hellooo\n"+cuisine.getRecipes());
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
        mReferenceCuisines.child(key).child("title").setValue(cuisine)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsInserted();
                    }
                });
    }


    public void updateCuisineTitle(String key, String newTitleCuisine, final DataStatus dataStatus){
        mReferenceCuisines.child(key).child("title").setValue(newTitleCuisine)
                .addOnSuccessListener(new OnSuccessListener<Void>() {



                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsUpdated();
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
