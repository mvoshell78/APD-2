package com.example.mich.allergenrecipe.Helpers;

import com.example.mich.allergenrecipe.CustomClasses.SelectedRecepieData;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mich on 7/25/16.
 */
public class GetSelectedItemsData {
    static SelectedRecepieData selectedRecepieData = null;

    public static SelectedRecepieData GetSelectedItemsData(String recipieId) throws JSONException {
        HttpURLConnection connection;
        String urlString = "http://api.yummly.com/v1/api/recipe/"+ recipieId +"?_app_id=f17f1694&_app_key=4c21ee62419a9c4984c9d5d0efe35c42";
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
        String yield = "";
        String totalTime = "";
        String source = "";
        String url = "";
        String numberServes = "";
        String cookTime = "";
        String imageUrl = "";


        if (apiData != null) {
            try {
                 yield = apiData.getString("yield");

            } catch (Exception e){
            yield = "Not Available";
            }
            try {
                totalTime = apiData.getString("totalTime");
            } catch (Exception e){
                totalTime  = "Not Available";
            }
            try {
                JSONObject sourceObject = apiData.getJSONObject("source");
                source = sourceObject.getString("sourceDisplayName");
            } catch (Exception e){
                source  = "Not Available";
            }
            try {
                JSONObject urlObject = apiData.getJSONObject("attribution");
                url = urlObject.getString("url");
            } catch (Exception e){
                url  = "Not Available";
            }
            try {
                numberServes = apiData.getString("numberOfServings");
            } catch (Exception e){
                numberServes  = "Not Available";
            }
            try {
                cookTime = apiData.getString("totalTimeInSeconds");
            } catch (Exception e){
                cookTime = "Not Available";
            }

            try {
                JSONArray imageArray = apiData.getJSONArray("images");
                JSONObject image = (JSONObject) imageArray.get(0);
                imageUrl = image.getString("hostedLargeUrl");
            } catch (Exception e){

            }


            selectedRecepieData = new SelectedRecepieData(yield,totalTime,source,url,numberServes,cookTime,imageUrl);

        }

        return selectedRecepieData;
    }
}
