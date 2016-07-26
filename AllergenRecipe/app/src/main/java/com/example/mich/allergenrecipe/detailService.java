package com.example.mich.allergenrecipe;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.widget.Toast;

import org.json.JSONException;

/**
 * Created by Mich on 7/25/16.
 */
public class detailService extends IntentService {
    SelectedRecepieData data;
    public static final String EXTRA_RESULT_RECEIVER = "com.fullsail.android.EXTRA_RESULT_RECEIVER";

    public detailService(){
        super("detailService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {

        if(isOnline()){
            String recipieId = intent.getStringExtra("String");

            try {
                data = GetSelectedItemsData.GetSelectedItemsData(recipieId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String test = "";

        }
        ResultReceiver receiver = intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);

        Bundle dataBundle =  new Bundle();

        dataBundle.putSerializable(DetailActivity.ARG_API_INFO, data);

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
