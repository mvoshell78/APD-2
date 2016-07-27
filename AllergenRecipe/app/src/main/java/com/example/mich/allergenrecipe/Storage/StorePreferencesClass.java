package com.example.mich.allergenrecipe.Storage;

import android.content.Context;
import android.widget.Toast;

import com.example.mich.allergenrecipe.CustomClasses.AllergenListData;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Mich on 7/18/16.
 */
public class StorePreferencesClass {
    private ArrayList<AllergenListData> mReturnedData;

    public void saveData(ArrayList<AllergenListData> PeopleList, Context context, String storageType)  {

    // creates a var from the item selectedSettings to write the data to
    String FILENAME = storageType + ".txt";


    try {
        // writeing the data
        FileOutputStream fileOutputStream = context.openFileOutput(FILENAME, context.MODE_PRIVATE);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(PeopleList);
        objectOutputStream.close();


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
    public ArrayList<AllergenListData> readFromStorage(Context context, String storageType){

        // creates a var from the item selectedSettings to read the data from
        String FILENAME = storageType + ".txt";

        //Toast.makeText(this, FILENAME, Toast.LENGTH_SHORT).show();

        // creates a place to write the data to
        mReturnedData = new ArrayList<AllergenListData>();
        try{
            // read that data
            FileInputStream fileInputStream = context.openFileInput(FILENAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            mReturnedData = (ArrayList<AllergenListData>) objectInputStream.readObject();
            // Toast.makeText(context,"Read Succesfull", Toast.LENGTH_SHORT).show();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }


        return mReturnedData;
    }

}
