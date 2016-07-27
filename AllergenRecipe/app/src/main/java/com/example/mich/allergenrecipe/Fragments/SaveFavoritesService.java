package com.example.mich.allergenrecipe.Fragments;

import android.app.IntentService;
import android.content.Intent;

import com.example.mich.allergenrecipe.Activities.DetailActivity;
import com.example.mich.allergenrecipe.CustomClasses.RecipeData;
import com.example.mich.allergenrecipe.Storage.StorageClass;

import java.util.ArrayList;

/**
 * Created by Mich on 7/25/16.
 */
public class SaveFavoritesService extends IntentService {
    public SaveFavoritesService(){
        super("SaveFavoritesService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        StorageClass storageClass = new StorageClass();
        ArrayList<RecipeData> recipeData = (ArrayList<RecipeData>) intent.getSerializableExtra("recipeData");
        storageClass.saveData(recipeData, DetailActivity.context, "favorites");

    }
}
