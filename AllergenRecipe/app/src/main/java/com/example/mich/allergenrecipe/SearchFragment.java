package com.example.mich.allergenrecipe;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Mich on 7/14/16.
 */
public class SearchFragment extends Fragment {
    public static final String TAG = "SearchFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment, container, false);
    }
}
