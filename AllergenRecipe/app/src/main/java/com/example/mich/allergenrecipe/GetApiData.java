package com.example.mich.allergenrecipe;

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


    public static ArrayList<RecipeData> getRecipeData() {
        ArrayList<RecipeData> weatherDataArrayList = null;
        HttpURLConnection connection;
        String forecastType = "";
        String state = "DE";
        String city = "Dover";



       // String urlString = "http://api.wunderground.com/api/d17c1da828371c3e/" + forecastType + "/q/"+ state +"/"+ city+ ".json";
        String urlString = "http://api.yummly.com/v1/api/recipes?_app_id=f17f1694&_app_key=4c21ee62419a9c4984c9d5d0efe35c42&q=onion+soup";
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
                    String condition = "";

//                JSONObject levelTwo = levelOne.getJSONObject("simpleforecast");
//                JSONArray levelThree = levelTwo.getJSONArray("forecastday");
//                JSONObject getDate = levelThree.getJSONObject(1);
//                JSONObject getDataArray = getDate.getJSONObject("date");
                    // used for a random number smaller than the size of the number of articles
//                int arraylength = levelThree.length();
//                Random rand = new Random();
//                int randomArticleNumber = rand.nextInt(arraylength);

                    // selects one article and returns it for use
//                JSONObject levelThree = levelTwo.getJSONObject(randomArticleNumber);
//                JSONObject articleInfoJson = levelThree.getJSONObject("data");

                    String temp;
                    String dateTime;
                    String iconUrl;
                    RecipeData weatherData;
//                    condition = levelOne.getString("weather");
//                    temp = levelOne.getString("temp_f");
//                    dateTime = levelOne.getString("observation_time");
//                    iconUrl = levelOne.getString("icon_url");

//                    weatherData = new RecipeData(condition,temp,dateTime, iconUrl);
//
//                    weatherDataArrayList = new ArrayList<RecipeData>();
//                    weatherDataArrayList.add(weatherData);

                    return weatherDataArrayList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

    }
}

