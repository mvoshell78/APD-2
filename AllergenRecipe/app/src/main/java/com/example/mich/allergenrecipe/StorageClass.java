package com.example.mich.allergenrecipe;

import android.content.Context;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Mich on 6/17/16.
 */
public class StorageClass {

    private ArrayList<RecipeData> mWeather;

    static String imageString;


    public void saveData(ArrayList<RecipeData> PeopleList, Context context, String storageType)  {

        // creates a var from the item selected to write the data to
        String FILENAME = storageType + ".txt";


        try {
            // writeing the data
            FileOutputStream fileOutputStream = context.openFileOutput(FILENAME, context.MODE_PRIVATE);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(PeopleList);
            objectOutputStream.close();

            for (int i = 0; i < PeopleList.size(); i++) {
            String imageURL = PeopleList.get(i).getSmallImageUrl();
            imageString = imageURL.toLowerCase().replaceAll("\\s+","").replace("/", "").replace(":", "").replace(".", "").trim();
            //Download the Image
            try {
                URLConnection connection = new URL(imageURL).openConnection();
                FileOutputStream file = context.openFileOutput(imageString, Context.MODE_PRIVATE);
                IOUtils.copy(connection.getInputStream(), file);
                IOUtils.closeQuietly(file);

                } catch (IOException e) {

                e.printStackTrace();
                }
            }

            // user feedback to let them know the save was successful
             //Toast.makeText(context, "Saved Succesful", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error Item Not Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error Item Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    // read the data from storage
    public ArrayList<RecipeData> readFromStorage(Context context, String forcastType){

        // creates a var from the item selected to read the data from
        String FILENAME = forcastType + ".txt";

        //Toast.makeText(this, FILENAME, Toast.LENGTH_SHORT).show();

        // creates a place to write the data to
        mWeather = new ArrayList<RecipeData>();
        try{
            // read that data
            FileInputStream fileInputStream = context.openFileInput(FILENAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            mWeather = (ArrayList<RecipeData>) objectInputStream.readObject();
            // Toast.makeText(context,"Read Succesfull", Toast.LENGTH_SHORT).show();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }


        return mWeather;
    }

    public static String readImageFromStorage(Context context, String urlString){

        String[] filenames = context.fileList();
        for (String filename : filenames) {
            if(filename.contains(urlString.toLowerCase().replaceAll("\\s+","").replace("/", "").replace(":", "").replace(".", "").trim()))
            imageString = filename;
            }
        return imageString;
    }

}





