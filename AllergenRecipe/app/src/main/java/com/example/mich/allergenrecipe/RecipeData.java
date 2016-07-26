package com.example.mich.allergenrecipe;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mich on 6/14/16.
 */
public class RecipeData implements Serializable {


    private String mRecpieName;
    private String mSmallImageUrls;
    private String mId;
    private int mRating;
    private ArrayList mIngredientsList;


    //creates the object
    public RecipeData(String _recipeName, String _smallImageUrl, String _id,int _rating, ArrayList _ingredientsList) {

        mRecpieName = _recipeName;
        mSmallImageUrls = _smallImageUrl;
        mId = _id;
        mIngredientsList = _ingredientsList;
        mRating = _rating;

    }


    // Getter methods.
    public String getRecipeName() { return mRecpieName;}
    public String getSmallImageUrl() { return mSmallImageUrls;}
    public String getId() { return mId;}
    public int getmRating() { return mRating;}
    public ArrayList getIngredientsList() { return mIngredientsList;}

    // Setter Methods
    public void setRecipeName(String _recipeName) { mRecpieName = _recipeName; }
    public void setSmallImageUrl(String _smallImageUrl) { mSmallImageUrls = _smallImageUrl; }
    public void setId(String _id) { mId = _id; }
    public void setmRating(int _rating) { mRating = _rating; }
    public void setIngredientsList(ArrayList _ingredientsList) { mIngredientsList = _ingredientsList; }
}
