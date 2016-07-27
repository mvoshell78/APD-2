package com.example.mich.allergenrecipe.CustomClasses;

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
    private String mCatagory;
    private String mCuisine;


    //creates the object
    public RecipeData(String _recipeName, String _smallImageUrl, String _id, int _rating, ArrayList _ingredientsList, String _catagory, String _cuisine) {

        mRecpieName = _recipeName;
        mSmallImageUrls = _smallImageUrl;
        mId = _id;
        mIngredientsList = _ingredientsList;
        mRating = _rating;
        mCatagory = _catagory;
        mCuisine = _cuisine;

    }


    // Getter methods.
    public String getRecipeName() { return mRecpieName;}
    public String getSmallImageUrl() { return mSmallImageUrls;}
    public String getId() { return mId;}
    public int getmRating() { return mRating;}
    public ArrayList getIngredientsList() { return mIngredientsList;}
    public String getmCatagory() { return mCatagory;}
    public String getmCuisine() { return mCuisine;}

    // Setter Methods
    public void setRecipeName(String _recipeName) { mRecpieName = _recipeName; }
    public void setSmallImageUrl(String _smallImageUrl) { mSmallImageUrls = _smallImageUrl; }
    public void setId(String _id) { mId = _id; }
    public void setmRating(int _rating) { mRating = _rating; }
    public void setIngredientsList(ArrayList _ingredientsList) { mIngredientsList = _ingredientsList; }
    public void setmCatagory(String _catagory) { mCatagory = _catagory; }
    public void setmCuisine(String _cuisine) { mCuisine = _cuisine; }
}
