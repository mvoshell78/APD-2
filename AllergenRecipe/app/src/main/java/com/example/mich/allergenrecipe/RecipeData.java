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
    private ArrayList mIngredientsList;


    //creates the object
    public RecipeData(String _recipeName, String _smallImageUrl, String _id, ArrayList _ingredientsList) {

        mRecpieName = _recipeName;
        mSmallImageUrls = _smallImageUrl;
        mId = _id;
        mIngredientsList = _ingredientsList;

    }


    // Getter methods.
    public String getRecipeName() { return mRecpieName;}
    public String getSmallImageUrl() { return mSmallImageUrls;}
    public String getId() { return mId;}
    public ArrayList getIngredientsList() { return mIngredientsList;}

    // Setter Methods
    public void setRecipeName(String _recipeName) { mRecpieName = _recipeName; }
    public void setSmallImageUrl(String _smallImageUrl) { mSmallImageUrls = _smallImageUrl; }
    public void setId(String _id) { mId = _id; }
    public void setIngredientsList(ArrayList _ingredientsList) { mIngredientsList = _ingredientsList; }
}
