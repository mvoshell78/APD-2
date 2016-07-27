package com.example.mich.allergenrecipe.Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.preference.PreferenceManager;

import com.example.mich.allergenrecipe.Activities.MainActivity;
import com.example.mich.allergenrecipe.Helpers.GetApiData;
import com.example.mich.allergenrecipe.CustomClasses.RecipeData;
import com.example.mich.allergenrecipe.Storage.StorageClass;

import java.util.ArrayList;

/**
 * Created by Mich on 6/14/16.
 */
public class apiService extends IntentService {
    public static final String EXTRA_RESULT_RECEIVER = "com.fullsail.android.EXTRA_RESULT_RECEIVER";


   public apiService(){
       super("apiService");

   }

    @Override
    protected void onHandleIntent(Intent intent) {


        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());


        String searchString = intent.getStringExtra("String");
        int startNumber = intent.getIntExtra("startNumber",0);

        ArrayList<RecipeData> data = null;



            data = GetApiData.getRecipeData(searchString , startNumber);
            StorageClass storageClass = new StorageClass();
            storageClass.saveData(data, MainActivity.context, searchString);

        ResultReceiver receiver = intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);

        Bundle dataBundle =  new Bundle();

        dataBundle.putSerializable(MainActivity.ARG_API_INFO, data);

        receiver.send(0, dataBundle);



    }





}
