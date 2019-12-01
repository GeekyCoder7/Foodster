package com.nkt.foodster;

import java.util.List;
import java.util.Map;

public class Cuisine {
    private String title;
    private String imageURL;
    private List<Recipe> recipes;


    public Cuisine() {

    }

    public Cuisine(String title, List<Recipe> recipes) {
        this.title = title;
        this.recipes = recipes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes (List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
