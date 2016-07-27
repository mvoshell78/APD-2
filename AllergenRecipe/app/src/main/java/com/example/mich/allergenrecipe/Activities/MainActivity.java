package com.example.mich.allergenrecipe.Activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mich.allergenrecipe.interfaces.FragmentActivityInterface;
import com.example.mich.allergenrecipe.Fragments.ListFragment;
import com.example.mich.allergenrecipe.R;
import com.example.mich.allergenrecipe.CustomClasses.RecipeData;
import com.example.mich.allergenrecipe.Fragments.SearchFragment;
import com.example.mich.allergenrecipe.Storage.StorageClass;
import com.example.mich.allergenrecipe.Services.apiService;
import com.example.mich.allergenrecipe.interfaces.searchTextInterface;
import com.example.mich.allergenrecipe.Fragments.settingsFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ActionBar.OnNavigationListener, searchTextInterface, FragmentActivityInterface {



    static final android.os.Handler mHandler = new android.os.Handler();
    public static final String ARG_API_INFO = "apiInfo";
    static ArrayList<RecipeData> recipeData;
    settingsFragment fragment;
    SearchFragment searchFrag;
    boolean selectedSettings = false;
    boolean selectedSearch = false;
    int startNumber;
    String getItemSelected = "";
    RelativeLayout relativeLayout;
    String spinnerArray[];
    FloatingActionButton fab;
    ProgressBar progressBar;

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        relativeLayout = (RelativeLayout) findViewById(R.id.transparentOverlay);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        createFrag();
        startNumber = 0;

        context=this;

        final Spinner spinner = (Spinner) findViewById(R.id.spinner_nav);

        spinnerArray = new String[]{"Favorites","American", "Chineese", "Italian", "Mexican"};

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

                getItemSelected = spinnerArray[i];

                if (i == 0){

                    progressBar.setVisibility(View.VISIBLE);
                    StorageClass storageClass = new StorageClass();
                    recipeData = storageClass.readFromStorage(context,"favorites");
                    if(recipeData.size() > 0 ) {
                        relativeLayout.setVisibility(2);
                    } else {
                        relativeLayout.setVisibility(View.INVISIBLE);
                    }

                    ListFragment fragment = (ListFragment) getFragmentManager().findFragmentByTag(ListFragment.TAG);
                    fragment.setUpList(recipeData);




                    fab.hide();
                }
                if (i > 0 ){

                progressBar.setVisibility(View.VISIBLE);
                startNumber = 0;
                startIntentService(getItemSelected, startNumber);

                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(MainActivity.context);

                    String number = SP.getString("results","10");
                    int num = startNumber;
                    int resultNumber = Integer.parseInt(number);

                    startNumber = startNumber + resultNumber;

                    startIntentService(getItemSelected, startNumber);
                    }



            });
        fab.hide();

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


            if (selectedSearch == true){
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

                if (searchFrag.isHidden()) {
                    fragmentTransaction.show(searchFrag);

                } else {
                    fragmentTransaction.hide(searchFrag);
                    View view = this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }

                fragmentTransaction.commit();
            } else {
                createSearchFragment();
            }

            return true;
        }
        if (id == R.id.action_settings){


           if (selectedSettings == true){
               FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
               fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);


               if (fragment.isHidden()) {
                   fragmentTransaction.show(fragment);
                   fab.hide();


               } else {
                   fragmentTransaction.hide(fragment);
                   if(recipeData.size() > 0 ){
                       fab.show();
                   }


               }

               fragmentTransaction.commit();
           } else {
               fab.hide();
               createSettingsFragment();
           }

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
        startNumber = 0;
        getItemSelected = searchTxt;
        startIntentService(searchTxt, startNumber);
        createSearchFragment();

    }

    @Override
    public void passItemFragActivity(int item) {

        RecipeData selectedRecipie =  recipeData.get(item);

        Intent detailIntent =  new Intent(this, DetailActivity.class);
        detailIntent.putExtra("recipieData", selectedRecipie);
        detailIntent.putExtra("itemSelected", getItemSelected);
        detailIntent.putExtra("item",item);

        startActivity(detailIntent);

    }


    public class apiResultReceiver extends ResultReceiver {

        public apiResultReceiver() {
            super(mHandler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            progressBar.setVisibility(View.GONE);



            fab.show();
            recipeData = (ArrayList<RecipeData>) resultData.getSerializable(ARG_API_INFO);

            ListFragment fragment = (ListFragment) getFragmentManager().findFragmentByTag(ListFragment.TAG);

            fragment.setUpList(recipeData);
            relativeLayout.setVisibility(2);

        }
    }



    public void startIntentService (String searchString, int startNumber ) {

        if (isOnline()) {

            Intent serviceIntent = new Intent(this, apiService.class);
            serviceIntent.putExtra(apiService.EXTRA_RESULT_RECEIVER, new apiResultReceiver());
            serviceIntent.putExtra("String", searchString);
            serviceIntent.putExtra("startNumber", startNumber);

            startService(serviceIntent);
        } else {

            recipeData = null;
            Toast.makeText(context, "Not connected to the internet", Toast.LENGTH_LONG).show();
            StorageClass storageClass = new StorageClass();
            recipeData = storageClass.readFromStorage(getBaseContext(), searchString);
            if (recipeData.size() ==  0) {
                Toast.makeText(context, "there are no saved items for " + getItemSelected, Toast.LENGTH_SHORT).show();
            } else {
                ListFragment fragment = (ListFragment) getFragmentManager().findFragmentByTag(ListFragment.TAG);

                fragment.setUpList(recipeData);
                relativeLayout.setVisibility(2);
                Toast.makeText(context, "Some items may not be available", Toast.LENGTH_SHORT).show();
            }


        }
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

        searchFrag = (SearchFragment)getFragmentManager().findFragmentByTag(SearchFragment.TAG);


            // checking for null
            if (searchFrag == null) {
                searchFrag = new SearchFragment();

            }

        getFragmentManager().beginTransaction().addToBackStack(SearchFragment.TAG).replace(R.id.searchContainer, searchFrag, SearchFragment.TAG).commit();
        selectedSearch = true;

        }

    public void createSettingsFragment(){
       fragment = (settingsFragment) getFragmentManager().findFragmentByTag(settingsFragment.TAG);

        if (fragment == null){
            fragment = new settingsFragment();
        }
        getFragmentManager().beginTransaction().addToBackStack(settingsFragment.TAG).replace(R.id.settingsContainer, fragment, settingsFragment.TAG).commit();
            selectedSettings = true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(getItemSelected.equals("Favorites")){
            StorageClass storageClass = new StorageClass();
            recipeData = storageClass.readFromStorage(context,"favorites");

            ListFragment fragment = (ListFragment) getFragmentManager().findFragmentByTag(ListFragment.TAG);
            fragment.setUpList(recipeData);
            if(recipeData.size() > 0){
                relativeLayout.setVisibility(2);
            } else {
                relativeLayout.setVisibility(View.INVISIBLE);
            }


        }
    }

    protected boolean isOnline() {

        ConnectivityManager mgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            return true;

        } else{


            return false;
        }
    }
}




