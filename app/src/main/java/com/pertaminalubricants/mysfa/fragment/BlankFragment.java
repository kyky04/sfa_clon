package com.pertaminalubricants.mysfa.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pertaminalubricants.mysfa.R;
import com.pertaminalubricants.mysfa.library.ApplicationContstant;
import com.pertaminalubricants.mysfa.model.Contributor;
import com.pertaminalubricants.mysfa.rest.ApiInterface;
import com.pertaminalubricants.mysfa.rest.ServiceGenerator;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

/**
 * Created by nunu on 10/20/2016.
 */

public class BlankFragment
        extends Fragment {

    private Context ctx;

    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ApplicationContstant.API_KEY.isEmpty()) {
            Toast.makeText(this.getContext(), "Please obtain your API KEY first ", Toast.LENGTH_LONG).show();
            return;
        }
        ctx = this.getContext();

        Log.e("salesapp", " on create");
        //setupListTransaction();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);

        return rootView;
    }


}
