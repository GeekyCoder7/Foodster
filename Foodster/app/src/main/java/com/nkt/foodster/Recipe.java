package com.nkt.foodster;

import java.util.Map;

public class Recipe {
    private String title;
    private String ingredients;
    private String calories;
    private String imageURL;


    public Recipe() {

    }

    public String getCalories() {
        return calories;
    }



    public void setCalories(String calories) {
        this.calories = calories;
    }


    public Recipe(String title, String ingredients, String calories) {
        this.title = title;
        this.ingredients = ingredients;
        this.calories = calories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
