package com.example.mich.allergenrecipe.Services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.example.mich.allergenrecipe.Activities.DetailActivity;
import com.example.mich.allergenrecipe.CustomClasses.SelectedRecepieData;
import com.example.mich.allergenrecipe.Helpers.GetSelectedItemsData;

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


            String recipieId = intent.getStringExtra("String");

            try {
                data = GetSelectedItemsData.GetSelectedItemsData(recipieId);
            } catch (JSONException e) {
                e.printStackTrace();
            }



        ResultReceiver receiver = intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);

        Bundle dataBundle =  new Bundle();

        dataBundle.putSerializable(DetailActivity.ARG_API_INFO, data);

        receiver.send(0, dataBundle);

    }


}
