package com.nkt.foodster;

import java.util.Map;

public class Recipe {

    private String title;
    private String ingredients;
    private String calories;
    private String imageURL;
    private String instructions;


    public Recipe() {

    }


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getCalories() {
        return calories;
    }



    public void setCalories(String calories) {
        this.calories = calories;
    }


    public Recipe(String title, String ingredients, String calories,String imageURL, String instructions) {
        this.title = title;
        this.ingredients = ingredients;
        this.calories = calories;
        this.imageURL=imageURL;
        this.instructions=instructions;
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
