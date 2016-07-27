package com.example.mich.allergenrecipe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mich.allergenrecipe.Activities.MainActivity;
import com.example.mich.allergenrecipe.interfaces.listClickInterface;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Mich on 7/13/16.
 */
public class CustomAdapter extends BaseAdapter {
    ArrayList<String> result;
    ArrayList<String> imageId;
    ArrayList<String> imageName;
    ArrayList<String> cuisineName;
    ArrayList<Integer> ratingC;


    Context context;
    listClickInterface listener;


    private static LayoutInflater inflater = null;

    public CustomAdapter(MainActivity mainActivity, ArrayList<String> recipieNameList, ArrayList<String> recipieImageUrlString, ArrayList<Integer> ratingCount, listClickInterface callback, ArrayList<String> catagory, ArrayList<String> cuisine) {


        result = recipieNameList;
        context = mainActivity;
        ratingC = ratingCount;
        imageName = catagory;
        cuisineName = cuisine;


        imageId = recipieImageUrlString;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listener = callback;




    }
    private CustomAdapter(listClickInterface listClickInterface){
        this.listener = listClickInterface;
    }

    @Override
    public int getCount() {

        return result.size();
    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }


    //    public class Holder
//    {
//        TextView tv;
//        ImageView img;
//    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = inflater.inflate(R.layout.recipie_list, null);

        TextView textView = (TextView) rowView.findViewById(R.id.textView1);

        TextView cuisine = (TextView) rowView.findViewById(R.id.textView2);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);
        ImageView imageView1 = (ImageView) rowView.findViewById(R.id.star1);
        ImageView imageView2 = (ImageView) rowView.findViewById(R.id.star2);
        ImageView imageView3 = (ImageView) rowView.findViewById(R.id.star3);
        ImageView imageView4 = (ImageView) rowView.findViewById(R.id.star4);
        String urlString = imageId.get(position);
        String image = imageName.get(position);
        textView.setText(result.get(position));
        if (cuisineName.get(position) == ""){

        } else {
            cuisine.setText("Cuisne : " + cuisineName.get(position) );
        }

            if (ratingC.get(position) == 1){
                imageView1.setImageResource((R.drawable.starfull));
                imageView2.setImageResource((R.drawable.starempty));
                imageView3.setImageResource((R.drawable.starempty));
                imageView4.setImageResource((R.drawable.starempty));

            }
            if (ratingC.get(position) == 2){
                imageView1.setImageResource((R.drawable.starfull));
                imageView2.setImageResource((R.drawable.starfull));
                imageView3.setImageResource((R.drawable.starempty));
                imageView4.setImageResource((R.drawable.starempty));

            }
            if (ratingC.get(position) == 3){
                imageView1.setImageResource((R.drawable.starfull));
                imageView2.setImageResource((R.drawable.starfull));
                imageView3.setImageResource((R.drawable.starfull));
                imageView4.setImageResource((R.drawable.starempty));

            }
            if (ratingC.get(position) == 4){
                imageView1.setImageResource((R.drawable.starfull));
                imageView2.setImageResource((R.drawable.starfull));
                imageView3.setImageResource((R.drawable.starfull));
                imageView4.setImageResource((R.drawable.starfull));

            }

        try {

            imageView.setImageBitmap(copyBitmap(BitmapFactory.decodeStream(context.openFileInput(image))));
        } catch (FileNotFoundException e) {
            Toast.makeText(MainActivity.context, "image not set", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }



        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                listener.itemclicked(position);
            }
        });


        return rowView;
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
