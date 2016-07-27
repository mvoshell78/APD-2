package com.example.mich.allergenrecipe.Helpers;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.mich.allergenrecipe.Activities.MainActivity;
import com.example.mich.allergenrecipe.CustomClasses.AllergenListData;
import com.example.mich.allergenrecipe.CustomClasses.RecipeData;
import com.example.mich.allergenrecipe.Storage.StorePreferencesClass;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mich on 6/14/16.
 */
public class GetApiData {


    public static ArrayList<RecipeData> getRecipeData(String string, int startNumber) {
        ArrayList<RecipeData> recpieDataArrayList = null;
        HttpURLConnection connection;


        StorePreferencesClass storePreferencesClass = new StorePreferencesClass();
        ArrayList<AllergenListData> allergenListDatas = storePreferencesClass.readFromStorage(MainActivity.context,"allergenList");
        ArrayList<AllergenListData> yourAllergenListDatas = storePreferencesClass.readFromStorage(MainActivity.context,"yourAllergenListArray");
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(MainActivity.context);

        String resultNumber = SP.getString("results","10");

        String allergenExclusion = "";
        String exclusionText =   "&excludedIngredient[]=";
        for (int i =0; i< allergenListDatas.size(); i++){
            if (allergenListDatas.get(i).getBool() == true){

                allergenExclusion = allergenExclusion + exclusionText + allergenListDatas.get(i).getAllergenKey();

            }
        }
        for (int i =0; i< yourAllergenListDatas.size(); i++){
            allergenExclusion = allergenExclusion + exclusionText + yourAllergenListDatas.get(i).getAllergenKey();
        }

        // if the user has spaces in their added allergens spaces crash the api
        // this removes the spaces between words to prevent a crash
        if (!allergenExclusion.equals("")){
                allergenExclusion = allergenExclusion.replaceAll(" ","");
            }


        String urlString = "http://api.yummly.com/v1/api/recipes?_app_id=f17f1694&_app_key=4c21ee62419a9c4984c9d5d0efe35c42&q=" + string + "&requirePictures=true" + allergenExclusion +"&maxResult=" + resultNumber +"&start=" + startNumber;

       //
        String resourceData = "No Data";

        try {

            URL url = new URL(urlString);

            connection = (HttpURLConnection) url.openConnection();
            // Setting connection properties.
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000); // 10 seconds
            connection.setReadTimeout(10000); // 10 seconds
            // Refreshing the connection.
            connection.connect();

            InputStream stream = connection.getInputStream();

            resourceData = IOUtils.toString(stream);
            stream.close();
            connection.disconnect();


        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject apiData;

        try {
            apiData = new JSONObject(resourceData);


        } catch (Exception e) {
            System.out.println("Cannot convert API resource to JSON");
            apiData = null;
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (apiData != null) {

                try {
                    //steps into levels of the JSON data
                    JSONArray levelOne = apiData.getJSONArray("matches");
                    recpieDataArrayList = new ArrayList<RecipeData>();

                    for (int ii = 0; ii < levelOne.length(); ii++){
                        JSONObject getArrayAtIndex = levelOne.getJSONObject(ii);
                        String getRecipeName = getArrayAtIndex.getString("recipeName");
                        JSONObject getimageUrl = getArrayAtIndex.getJSONObject("imageUrlsBySize");
                        String getRating = getArrayAtIndex.getString("rating");
                        int convertedRating = Integer.parseInt(getRating);

                        String getImageURL =  getimageUrl.getString("90");
                        //getImageURL = getImageURL.replace("", "");
                        char char4 = getImageURL.charAt(4);
                        if (Character.toString(char4).matches("s")){

                        } else {

                            StringBuilder str = new StringBuilder(getImageURL);
                            str.insert(4, 's');
                            getImageURL = String.valueOf(str);

                        }


                        String getRecipeID = getArrayAtIndex.getString("id");
                        JSONArray getIngredientsList = getArrayAtIndex.getJSONArray("ingredients");
                        ArrayList ingredientsList = null;
                        ingredientsList =  new ArrayList();

                        for (int i=0; i < getIngredientsList.length(); i++){
                            String ingredient = getIngredientsList.getString(i);


                            ingredientsList.add(ingredient);
                        }
                        RecipeData recipeData;
                        recipeData = new RecipeData(getRecipeName,getImageURL,getRecipeID,convertedRating,ingredientsList);
                        recpieDataArrayList.add(recipeData);
                    }

                    return recpieDataArrayList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

    }
}

