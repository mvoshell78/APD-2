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


    public static ArrayList<RecipeData> getRecipeData(String forecastType, String cityPref) {
        ArrayList<RecipeData> weatherDataArrayList = null;
        HttpURLConnection connection;
        String urlType = forecastType;
        String state = "DE";
        String city = "Dover";
        if (city.equals("Dover")){
             state = "DE";
            city = cityPref;
        }
        if (city.equals("Columbus")){
            state = "GA";
            city = cityPref;
        }
        if (city.equals("Anchorage")){
            state = "AK";
            city = cityPref;
        }


        String urlString = "http://api.wunderground.com/api/d17c1da828371c3e/" + forecastType + "/q/"+ state +"/"+ city+ ".json";
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
            if (forecastType.equals("conditions")){
                try {
                    //steps into levels of the JSON data
                    JSONObject levelOne = apiData.getJSONObject("current_observation");
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
                    String condition;
                    String temp;
                    String dateTime;
                    String iconUrl;
                    RecipeData weatherData;
                    condition = levelOne.getString("weather");
                    temp = levelOne.getString("temp_f");
                    dateTime = levelOne.getString("observation_time");
                    iconUrl = levelOne.getString("icon_url");

                    weatherData = new RecipeData(condition,temp,dateTime, iconUrl);

                    weatherDataArrayList = new ArrayList<RecipeData>();
                    weatherDataArrayList.add(weatherData);

                    return weatherDataArrayList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } if (forecastType.equals("forecast")){
                try {
                    JSONObject levelOne = apiData.getJSONObject("forecast");
                    JSONObject levelTwo = levelOne.getJSONObject("simpleforecast");
                    JSONArray levelThree = levelTwo.getJSONArray("forecastday");

                    weatherDataArrayList = new ArrayList<RecipeData>();

                    for (int i = 0; i < levelThree.length(); i++){
                        JSONObject getDay = levelThree.getJSONObject(i);
                        JSONObject high = getDay.getJSONObject("high");
                        String highFahrenheit = high.getString("fahrenheit");
                        JSONObject low = getDay.getJSONObject("low");
                        String lowFahrenheit = low.getString("fahrenheit");
                        String iconUrl = getDay.getString("icon_url");


                        String condition = getDay.getString("conditions");

                        RecipeData weatherData;
                        weatherData = new RecipeData(condition,highFahrenheit,lowFahrenheit, iconUrl);

                        weatherDataArrayList.add(weatherData);
                    }


                    return weatherDataArrayList;


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }




            return null;
        } else {
            return null;
        }

    }
}

