package com.pertaminalubricants.mysfa.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.pertaminalubricants.mysfa.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nunu on 10/20/2016.
 */

public class ListViewTransactionFragment
        extends com.blunderer.materialdesignlibrary.fragments.ListViewFragment {

    private List<String> mObjects;
    private ArrayAdapter<String> mAdapter;

    @Override
    public ListAdapter getListAdapter() {
        mObjects = new ArrayList<>(Arrays.asList(
                "Follow up transaction 1",
                "Follow up transaction 2",
                "Follow up transaction 3"
        ));
        return (mAdapter = new ArrayAdapter<>(getActivity(), R.layout.listview_row, mObjects));
    }

    @Override
    public boolean useCustomContentView() {
        return false;
    }

    @Override
    public int getCustomContentView() {
        return 0;
    }

    @Override
    public boolean pullToRefreshEnabled() {
        return true;
    }

    @Override
    public int[] getPullToRefreshColorResources() {
        return new int[]{R.color.mdl_color_primary_dark};
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                mObjects.add("Follow up transaction " + (mObjects.size() + 1));
                mAdapter.notifyDataSetChanged();
                setRefreshing(false);
            }

        }, 2000);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        return false;
    }

}
