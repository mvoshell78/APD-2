package com.example.mich.allergenrecipe.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.mich.allergenrecipe.R;
import com.example.mich.allergenrecipe.interfaces.searchTextInterface;

/**
 * Created by Mich on 7/14/16.
 */
public class SearchFragment extends Fragment {
    public static final String TAG = "SearchFragment";
    ImageButton searchButton;
    EditText searchText;
    searchTextInterface listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchText = (EditText) getActivity().findViewById(R.id.searchText);
        searchButton = (ImageButton) getActivity().findViewById(R.id.searchButton);
        searchTextListener();

        searchText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    searchMethod();

                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {


        super.onAttach(activity);
        if (activity instanceof searchTextInterface){

            listener = (searchTextInterface) activity;

        } else {

            throw new IllegalArgumentException("not connected");

        }
    }


    public void searchTextListener() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchMethod();

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            }
        });
    }

    public void searchMethod(){

        String searchedText = String.valueOf(searchText.getText());
        searchText.setText("");
        searchedText = searchedText.replace(' ','+');
        listener.searchText(searchedText);

    }

}
