package com.example.mich.allergenrecipe;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ActionBar.OnNavigationListener, searchTextInterface{


    static final android.os.Handler mHandler = new android.os.Handler();
    public static final String ARG_API_INFO = "apiInfo";
    static ArrayList<RecipeData> recipeData;

    String spinnerArray[];
    ArrayList<AllergenListData> allergenListData;
    ProgressBar progressBar;

    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ArrayList prgmName;
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        createFrag();



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
                progressBar.setVisibility(View.VISIBLE);
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
        if (id == R.id.action_search) {
            createSearchFragment();
            return true;
        }
        if (id == R.id.action_settings){
       createSettingsFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return false;
    }

    @Override
    public void searchText(String searchTxt) {

        startIntentService(searchTxt);
        createSearchFragment();

    }


    public class apiResultReceiver extends ResultReceiver {

        public apiResultReceiver() {
            super(mHandler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            progressBar.setVisibility(View.GONE);


           recipeData = (ArrayList<RecipeData>) resultData.getSerializable(ARG_API_INFO);

            ListFragment fragment = (ListFragment) getFragmentManager().findFragmentByTag(ListFragment.TAG);

            fragment.setUpList(recipeData);
            //setUpList(recipeData);
        }
    }

    public void startIntentService (String string){

        Intent serviceIntent = new Intent(this, apiService.class);
        serviceIntent.putExtra(apiService.EXTRA_RESULT_RECEIVER, new apiResultReceiver());
        serviceIntent.putExtra("String", string);

        startService(serviceIntent);

    }
    public void createFrag(){

        getFragmentManager().beginTransaction().replace(R.id.mainContainer,new ListFragment()).commit();

        ListFragment fragment = (ListFragment) getFragmentManager().findFragmentByTag(ListFragment.TAG);
        if (fragment == null){
            fragment = ListFragment.newInstanceOf(recipeData);
            getFragmentManager().beginTransaction().replace(R.id.mainContainer,fragment,ListFragment.TAG).commit();

        } else {
            fragment.setUpList(recipeData);
        }
    }

    public void createSearchFragment(){

        SearchFragment fragment = (SearchFragment)getFragmentManager().findFragmentByTag(SearchFragment.TAG);


            // checking for null
            if (fragment == null) {
                fragment = new SearchFragment();

                getFragmentManager().beginTransaction().addToBackStack(SearchFragment.TAG).replace(R.id.searchContainer, fragment, SearchFragment.TAG).commit();

            } else {
                // if a frag exists update it with the following txt items
                getFragmentManager().beginTransaction().addToBackStack(SearchFragment.TAG).replace(R.id.searchContainer, fragment, SearchFragment.TAG).commit();
            }

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.setCustomAnimations(android.R.animator.fade_in,
                    android.R.animator.fade_out);

            if (fragment.isHidden()) {
                ft.show(fragment);
                Log.d("hidden","Show");
            } else {
                ft.hide(fragment);
                Log.d("Shown","Hide");
            }

            ft.commit();
        }

    public void createSettingsFragment(){
        settingsFragment fragment = (settingsFragment) getFragmentManager().findFragmentByTag(settingsFragment.TAG);

        if (fragment == null){
            fragment = new settingsFragment();
        }
        getFragmentManager().beginTransaction().addToBackStack(settingsFragment.TAG).replace(R.id.settingsContainer, fragment, settingsFragment.TAG).commit();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out);

        if (fragment.isHidden()) {
            ft.show(fragment);




        } else {
            ft.hide(fragment);
            Log.d("Shown","Hide");
        }

        ft.commit();
    }

//        public void searchTextListener( ){
//        searchButton = (ImageButton) getActivity().findViewById(R.id.searchButton);
//        searchText = (EditText) getActivity().findViewById(R.id.searchText);
//
//        String searchedText = String.valueOf(searchText.getText());
//
//        searchedText = searchedText.replace(' ','+');
//
//        //
//
//    }
    }




