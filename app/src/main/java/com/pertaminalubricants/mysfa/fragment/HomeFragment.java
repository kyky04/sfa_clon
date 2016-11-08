package com.pertaminalubricants.mysfa.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pertaminalubricants.mysfa.R;

/**
 * Created by nunu on 10/20/2016.
 */

public class HomeFragment
        extends Fragment {

//    @Override
//    protected int getContentView() {
//        return R.layout.activity_cardview_with_top_image;
//    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        return rootView;
    }

}
