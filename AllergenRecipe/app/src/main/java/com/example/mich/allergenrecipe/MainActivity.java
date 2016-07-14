package com.example.mich.allergenrecipe;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ActionBar.OnNavigationListener{


    static final android.os.Handler mHandler = new android.os.Handler();
    public static final String ARG_API_INFO = "apiInfo";
    static ArrayList<RecipeData> recipeData;
    Button searchButton;
    EditText searchText;
    String spinnerArray[];
    static ListView lv;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ArrayList prgmName;
        lv=(ListView) findViewById(R.id.listView);

        searchButton = (Button) findViewById(R.id.searchButton);
        searchText = (EditText) findViewById(R.id.searchText);
        context=this;




        final Spinner spinner = (Spinner) findViewById(R.id.spinner_nav);

        spinnerArray = new String[]{"Favorites","American", "Chineese", "Italian", "Mexican"};

//        Spinner spinner = new Spinner(this);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {


                if (i > 0 ){
                String getItemSelected = spinnerArray[i];
                startIntentService(getItemSelected);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
public void searchMethod(View v ){

    String searchedText = String.valueOf(searchText.getText());

    searchedText = searchedText.replace(' ','+');

    startIntentService(searchedText);

}

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return false;
    }


    public static class apiResultReceiver extends ResultReceiver {

        public apiResultReceiver() {
            super(mHandler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {


           recipeData = (ArrayList<RecipeData>) resultData.getSerializable(ARG_API_INFO);
            setUpList(recipeData);
        }
    }

    public void startIntentService (String string){

        Intent serviceIntent = new Intent(this, apiService.class);
        serviceIntent.putExtra(apiService.EXTRA_RESULT_RECEIVER, new apiResultReceiver());
        serviceIntent.putExtra("String", string);

        startService(serviceIntent);

    }

    public static void setUpList(ArrayList<RecipeData> recipeData){

       ArrayList<String> recipieNames = null;
        ArrayList<String> recipieImageUrl = null;
        recipieNames = new ArrayList<>();
        recipieImageUrl = new ArrayList<>();

        for (int i=0; i< recipeData.size(); i++){

            String name = recipeData.get(i).getRecipeName();
            String imageUrl = recipeData.get(i).getSmallImageUrl();
            recipieNames.add(name);
            recipieImageUrl.add(imageUrl);
        }

        lv.setAdapter(new CustomAdapter((MainActivity) context, recipieNames,recipieImageUrl));
    }
}
