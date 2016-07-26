package com.example.mich.allergenrecipe;

import android.app.IntentService;
import android.content.Intent;

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
