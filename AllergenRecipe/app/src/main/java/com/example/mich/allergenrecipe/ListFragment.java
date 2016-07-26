package com.example.mich.allergenrecipe;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Mich on 7/14/16.
 */
public class ListFragment extends Fragment implements listClickInterface{
    public static final String TAG = "ListFragment";
    private static String ARG = "arg";
    static ListView lv;
    FragmentActivityInterface listener;


   // getListPosition listener;
    public ListFragment(){

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof searchTextInterface){

            listener = (FragmentActivityInterface) activity;

        } else {

            throw new IllegalArgumentException("not connected");

        }
    }
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        if (activity instanceof getListPosition){
//            listener = (getListPosition) activity;
//        } else {
//            throw new IllegalArgumentException("not connected");
//        }
//    }

    public static ListFragment newInstanceOf(ArrayList<RecipeData> fav ){
        ListFragment fragment = new ListFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG, fav);
        fragment.setArguments(args);
        return  fragment;
    }

    private ArrayList<RecipeData> mFavorites;

    private ArrayList<String> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_fragement, container, false);
        lv = (ListView) v.findViewById(R.id.listView);


        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        data = new ArrayList<String>();

        Bundle args = getArguments();
        if (args != null) {
            setUpList((ArrayList<RecipeData>) args.getSerializable(ARG));

        }
    }



    public void setUpList(ArrayList<RecipeData> recipeData){

        ArrayList<String> recipieNames = null;
        ArrayList<String> recipieImageUrl = null;
        ArrayList<Integer> ratingCount = null;
        recipieNames = new ArrayList<>();
        recipieImageUrl = new ArrayList<>();
        ratingCount = new ArrayList<>();
            if (recipeData!= null){
                for (int i=0; i< recipeData.size(); i++){

                    String name = recipeData.get(i).getRecipeName();
                    String imageUrl = recipeData.get(i).getSmallImageUrl();
                    int rating = recipeData.get(i).getmRating();
                    recipieNames.add(name);
                    recipieImageUrl.add(imageUrl);
                    ratingCount.add(rating);
                }
            }


        lv.setAdapter(new CustomAdapter((MainActivity) MainActivity.context, recipieNames, recipieImageUrl, ratingCount, this));
    }

    @Override
    public void itemclicked(int item) {


        listener.passItemFragActivity(item);
    }
}
