// Michael Voshell
// MDF3 - 1606
// apiService.java

package com.example.mich.allergenrecipe;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.preference.PreferenceManager;
import android.widget.Toast;

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

        String cityName = SP.getString("city_name", null);
        String forecastType = intent.getStringExtra("forecastType");
        ArrayList<RecipeData> data = null;
        if (isOnline()){


            data = GetApiData.getRecipeData();

        } else {
           StorageClass storageClass = new StorageClass();
            data = storageClass.readFromStorage(getBaseContext(),forecastType );

        }

        ResultReceiver receiver = intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);


        Bundle dataBundle =  new Bundle();

        dataBundle.putSerializable(MainActivity.ARG_API_INFO, data);

        receiver.send(0, dataBundle);



    }

    protected boolean isOnline() {

        ConnectivityManager mgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            return true;

        } else{

            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();

            return false;
        }
    }

}
