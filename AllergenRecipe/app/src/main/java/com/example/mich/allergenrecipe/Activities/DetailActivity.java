package com.example.mich.allergenrecipe.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mich.allergenrecipe.R;
import com.example.mich.allergenrecipe.CustomClasses.RecipeData;
import com.example.mich.allergenrecipe.Fragments.SaveFavoritesService;
import com.example.mich.allergenrecipe.CustomClasses.SelectedRecepieData;
import com.example.mich.allergenrecipe.Storage.StorageClass;
import com.example.mich.allergenrecipe.Services.detailService;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{
    RecipeData recipeData;
    ArrayList<RecipeData> favoriteRecepies;
    static final android.os.Handler mHandler = new android.os.Handler();
    public static final String ARG_API_INFO = "apiInfo";
    TextView recipieName;
    TextView gotoWeb;
    String selectedItem;
    TextView totalTime;
    ImageView imageView;
    LinearLayout layout;
    TextView source;
    TextView yield;
    TextView cookTime;
    TextView ingredient;
    SelectedRecepieData selectedRecepieData;
    String favorites = "favorites";
    public static Context context;
    int itemNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        recipeData = (RecipeData) intent.getSerializableExtra("recipieData");
        selectedItem = intent.getStringExtra("itemSelected");
        itemNumber = intent.getIntExtra("item",0);

        context = this;

        recipieName = (TextView) findViewById(R.id.recipieName);
        totalTime = (TextView) findViewById(R.id.totalTime);
        imageView = (ImageView) findViewById(R.id.imageView);
        layout = (LinearLayout) findViewById(R.id.ingredients);
        source = (TextView) findViewById(R.id.source);
        yield = (TextView) findViewById(R.id.yield);
        cookTime = (TextView) findViewById(R.id.cookTime);
        ingredient = (TextView) findViewById(R.id.ing);
        gotoWeb= (TextView) findViewById(R.id.gotoWeb);

        favoriteRecepies = new ArrayList<RecipeData>();


        if (isOnline()){
            Intent serviceIntent = new Intent(this, detailService.class);
            serviceIntent.putExtra(detailService.EXTRA_RESULT_RECEIVER, new apiResultReceiver());
            serviceIntent.putExtra("String", recipeData.getId());
            startService(serviceIntent);
        } else {
            recipieName.setText(recipeData.getRecipeName());
            ArrayList<String> ingredients = recipeData.getIngredientsList();
            ingredient.setText("Ingredients : ");
            String urlString = recipeData.getSmallImageUrl();
            String filename = StorageClass.readImageFromStorage(context, urlString);
            try {

                imageView.setImageBitmap(copyBitmap(BitmapFactory.decodeStream(context.openFileInput(filename))));
            } catch (FileNotFoundException e) {
                Toast.makeText(MainActivity.context, "image not set", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            for (int i = 0; i < ingredients.size(); i++ ){
                final TextView ingredientTextView = new TextView(DetailActivity.this);
                ingredientTextView.setText(ingredients.get(i));

                layout.addView(ingredientTextView);
            }
        }






//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (selectedItem.equals("Favorites")){
            getMenuInflater().inflate(R.menu.menu_delete, menu);
        } else{
            getMenuInflater().inflate(R.menu.menu_detail, menu);
        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {
            StorageClass storageClass = new StorageClass();

            favoriteRecepies = storageClass.readFromStorage(this, favorites);
            favoriteRecepies.add(recipeData);

            Intent saveFavoritesIntent = new Intent(this, SaveFavoritesService.class);
            saveFavoritesIntent.putExtra("recipeData", favoriteRecepies);
            startService(saveFavoritesIntent);

            Toast.makeText(this,"Favorite Saved",Toast.LENGTH_SHORT).show();
            finish();


            return true;
        }
        if (id == R.id.action_share){

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this recepie!");
            sendIntent.putExtra(Intent.EXTRA_TEXT,selectedRecepieData.getmUrl() );
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Share data with..."));

            return true;
        }
        if (id == R.id.action_delete){

            StorageClass storageClass = new StorageClass();
            favoriteRecepies = storageClass.readFromStorage(this, favorites);
            favoriteRecepies.remove(itemNumber);




            Intent saveFavoritesIntent = new Intent(this, SaveFavoritesService.class);
            saveFavoritesIntent.putExtra("recipeData", favoriteRecepies);
            startService(saveFavoritesIntent);

            Toast.makeText(this,"Favorite Deleted",Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(selectedRecepieData.getmUrl()));
        startActivity(browserIntent);
    }


    public class apiResultReceiver extends ResultReceiver {

        public apiResultReceiver() {
            super(mHandler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            selectedRecepieData = (SelectedRecepieData) resultData.getSerializable(ARG_API_INFO);
            recipieName.setText(recipeData.getRecipeName());
            totalTime.setText("Total Time : " + selectedRecepieData.getmTotalTime());
            if (selectedRecepieData.getmImageUrl() == null){
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.noimageavailable));

            } else {

                new DownloadImageTask(imageView).execute(selectedRecepieData.getmImageUrl());
            }

            ArrayList<String> ingredients = recipeData.getIngredientsList();
            source.setText("Recepie provided by " + selectedRecepieData.getmSource());
            yield.setText("Serves : " + selectedRecepieData.getmYield());
            if(selectedRecepieData.getmCookTime() == null){
                if(selectedRecepieData.getmCookTime().equals("null")){
                    cookTime.setText("");
                } else {
                    cookTime.setText("Cook Time : " + (Integer.parseInt(selectedRecepieData.getmCookTime())/ 60) + " Min");
                }


            }

            ingredient.setText("Ingredients : ");
            gotoWeb.setText("View full recepie");

            for (int i = 0; i < ingredients.size(); i++ ){
                final TextView ingredientTextView = new TextView(DetailActivity.this);
                ingredientTextView.setText(ingredients.get(i));

                layout.addView(ingredientTextView);
            }



        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
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

    public static Bitmap copyBitmap(Bitmap bmp) {
        try {
            Bitmap copy = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas copyCanvas = new Canvas(copy);
            copyCanvas.drawBitmap(bmp, new Matrix(), null);
            return copy;
        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;
    }
}
