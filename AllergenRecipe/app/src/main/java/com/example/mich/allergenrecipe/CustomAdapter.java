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

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Mich on 7/13/16.
 */
public class CustomAdapter extends BaseAdapter {
    ArrayList<String> result;
    ArrayList<String> imageId;
    Context context;

    private static LayoutInflater inflater = null;
    public CustomAdapter(MainActivity mainActivity, ArrayList<String> recipieNameList, ArrayList<String> recipieImageUrlString) {

        // TODO Auto-generated constructor stub
        result = recipieNameList;
        context= mainActivity;

        imageId = recipieImageUrlString;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }



    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = inflater.inflate(R.layout.recipie_list, null);

        TextView textView =(TextView) rowView.findViewById(R.id.textView1);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);
        String urlString = imageId.get(position);
        String filename = StorageClass.readImageFromStorage(context, urlString);
        textView.setText(result.get(position));
        try {
            imageView.setImageBitmap(copyBitmap(BitmapFactory.decodeStream(context.openFileInput(filename))));
        } catch (FileNotFoundException e) {
            //System.out.println(TAG + "Problem setting the image.");
            e.printStackTrace();
        }




        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + result.get(position), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }


    public static Bitmap copyBitmap(Bitmap bmp) {
        Bitmap copy = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas copyCanvas = new Canvas(copy);
        copyCanvas.drawBitmap(bmp, new Matrix(), null);
        return copy;
    }


}
