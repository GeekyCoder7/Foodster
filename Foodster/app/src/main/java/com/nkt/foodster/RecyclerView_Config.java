package com.nkt.foodster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private CuisinesAdapter mCuisineAdapter;
    private RecipeAdapter mRecipeAdapter;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;


    public void setConfig(RecyclerView recyclerView, Context context, List<Cuisine> cuisines, List<String> keys){
        mContext = context;
        mCuisineAdapter = new CuisinesAdapter(cuisines,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mCuisineAdapter);
    }

    public void setConfig1(RecyclerView recyclerView, Context context, List<Recipe> recipes, List<String> keys){
        mContext = context;
        mRecipeAdapter = new RecipeAdapter(recipes,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mRecipeAdapter);
    }

    class CuisineItemView extends RecyclerView.ViewHolder {
        private TextView mTitle, mimageURL;
        private ImageView mImage;

        private String key;

        public CuisineItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.cuisine_list_item, parent, false));

            mTitle = (TextView) itemView.findViewById(R.id.title_txtView);
            mimageURL = (TextView) itemView.findViewById(R.id.cuisineImageURLtxtView);

           mImage = (ImageView) itemView.findViewById(R.id.cuisineImgView);

            sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                itemView.setOnClickListener(v->{
                    boolean isAdmin = sharedpreferences.getBoolean("isAdmin",false);
                    if(isAdmin){
                        Intent intent = new Intent(mContext, CuisineDetailsActivity.class);
                        intent.putExtra("key",key);
                        intent.putExtra("cuisine_type",mTitle.getText());
                        intent.putExtra("image_URL",mimageURL.getText());
                        mContext.startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(mContext, RecipesActivity.class);
                        intent.putExtra("key",key);
                        System.out.println("Parev"+key);
                        intent.putExtra("cuisine_type", mTitle.getText().toString());
                        mContext.startActivity(intent);
                    }

                });


            //if admin edit, else open normally


        }



        public void bind(Cuisine cuisine, String key){
            mTitle.setText(cuisine.getTitle());
            mimageURL.setText(cuisine.getImageURL());
            if(cuisine.getImageURL()!=null&&cuisine.getImageURL().length()==0){
                switch (cuisine.getTitle()){
                    case "American":
                        mImage.setImageResource(R.drawable.burger);
                        break;
                    case "Italian":
                        mImage.setImageResource(R.drawable.pizza);
                        break;
                    case "Chinese":
                        mImage.setImageResource(R.drawable.noodles);
                        break;
                    case "Japanese":
                        mImage.setImageResource(R.drawable.sushi);
                        break;
                    case "Mediterranean":
                        mImage.setImageResource(R.drawable.risotto);
                        break;
                    case "Armenian":
                        mImage.setImageResource(R.drawable.kebab);
                        break;
                    case "French":
                        mImage.setImageResource(R.drawable.croissant);
                        break;
                    case "Greek":
                        mImage.setImageResource(R.drawable.salad);
                        break;
                }
            }
            else{
                Picasso.get().load(cuisine.getImageURL()).into(mImage);
            }
            this.key=key;
        }


    }

    class RecipeItemView extends RecyclerView.ViewHolder {
        private TextView mId,mTitle, mCalories, mIngredients,mInstructions,mImageURL;
        private ImageView mImage;

        private String key;

        public RecipeItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.recipe_list_item, parent, false));

            mId = (TextView) itemView.findViewById(R.id.id_txtView);
            mTitle = (TextView) itemView.findViewById(R.id.title_txtView);
            mCalories = (TextView) itemView.findViewById(R.id.caloriesTxtView);
            mIngredients = (TextView) itemView.findViewById(R.id.ingredientsTxtView);
            mInstructions = (TextView) itemView.findViewById(R.id.instructionsTxtView);

            mImageURL = (TextView) itemView.findViewById(R.id.recipeImageURLTxtView);
            mImage = (ImageView) itemView.findViewById(R.id.recipeImgView);
            sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


            itemView.setOnClickListener(v->{
                boolean isAdmin = sharedpreferences.getBoolean("isAdmin",false);
                if(isAdmin){

                    Intent intent = new Intent(mContext, RecipeDetailsActivity.class);
                    intent.putExtra("id",mId.getText());
                    intent.putExtra("key",key);
                    System.out.println("parev"+key);
                    intent.putExtra("title",mTitle.getText());
                    intent.putExtra("ingredients",mIngredients.getText());
                    intent.putExtra("calories",mCalories.getText());
                    intent.putExtra("instructions",mInstructions.getText());
                    intent.putExtra("imageURL",mImageURL.getText());
                    mContext.startActivity(intent);

                }
                else{
                    Intent intent = new Intent(mContext, RecipeActivity.class);
                    intent.putExtra("item_name", mTitle.getText().toString());
                    intent.putExtra("ingredients", mIngredients.getText().toString());
                    intent.putExtra("instructions",mInstructions.getText());
                    intent.putExtra("calories",mCalories.getText());
                    mContext.startActivity(intent);
                }

            });

            //if admin edit, else open normally


        }



        public void bind(Recipe recipe, String key){
            System.out.println(recipe);
            mId.setText(key);
            mTitle.setText(recipe.getTitle());
            mCalories.setText(recipe.getCalories());
            mIngredients.setText(recipe.getIngredients());
            mImageURL.setText(recipe.getImageURL());
            mInstructions.setText(recipe.getInstructions());
//            Log.d("asd","Hel2"+recipe.getId());
            this.key=key;
        }


    }

    class CuisinesAdapter extends RecyclerView.Adapter<CuisineItemView>{
        private List<Cuisine> mCuisineList;
        public List<String> mKeys;

        public CuisinesAdapter(List<Cuisine> mCuisineList, List<String> mKeys) {
            this.mCuisineList = mCuisineList;
            this.mKeys = mKeys;
        }

        public Cuisine getCuisine(int position) {
            return mCuisineList.get(position);
        }

        @NonNull
        @Override
        public CuisineItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CuisineItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CuisineItemView holder, int position) {
            holder.bind(mCuisineList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mCuisineList.size();
        }
    }

    class RecipeAdapter extends RecyclerView.Adapter<RecipeItemView>{
        private List<Recipe> mRecipeList;
        public List<String> mKeys;

        public RecipeAdapter(List<Recipe> mRecipeList, List<String> mKeys) {
            this.mRecipeList = mRecipeList;
            this.mKeys=mKeys;
        }

        public Recipe getRecipe(int position) {
            return mRecipeList.get(position);
        }

        @NonNull
        @Override
        public RecipeItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecipeItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipeItemView holder, int position) {
            holder.bind(mRecipeList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mRecipeList.size();
        }
    }

}
