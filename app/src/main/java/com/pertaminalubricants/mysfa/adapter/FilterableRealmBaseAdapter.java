package com.pertaminalubricants.mysfa.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.pertaminalubricants.mysfa.library.SessionManager;
import com.pertaminalubricants.mysfa.model.MaterialRealm;
import com.pertaminalubricants.mysfa.model.StockRealm;
import com.pertaminalubricants.mysfa.model.StockResponse;
import com.pertaminalubricants.mysfa.rest.SalesService;
import com.pertaminalubricants.mysfa.rest.ServiceGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.RealmObject;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nunu on 11/6/2016.
 */

public abstract class FilterableRealmBaseAdapter <T extends RealmObject> extends ArrayAdapter<T> implements Filterable {

    private final RealmResults<T> mRealmObjectList;
    private List<T> mResults;
    private final List<StockRealm> listStock;

    public FilterableRealmBaseAdapter(Context context, @LayoutRes int layout, RealmResults<T> realmObjectList) {
        super(context, layout);
        mRealmObjectList = realmObjectList;
        listStock = null;
    }

    @Override
    public int getCount() {
        return mResults == null ? 0 : mResults.size();
    }

    @Override
    public T getItem(int position) {
        return mResults == null ? null : mResults.get(position);
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            private boolean mHasResults = false;

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults results = new FilterResults();
                results.count = mHasResults ? 1 : 0; // AutoCompleteTextView already hides dropdown here if count is 0, so correct it.
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (constraint != null) {
                    mResults = performRealmFiltering(constraint, mRealmObjectList);
                    mHasResults = mResults.size() > 0;
                    notifyDataSetChanged();
                }
            }
        };
    }

    public List<T> performRealmFiltering(@NonNull CharSequence constraint, RealmResults<T> results){
        if (constraint != null) {
                mResults = mRealmObjectList.where().contains("name", String.valueOf(constraint), Case.INSENSITIVE).findAll();
        }
        return mResults;
    }
}