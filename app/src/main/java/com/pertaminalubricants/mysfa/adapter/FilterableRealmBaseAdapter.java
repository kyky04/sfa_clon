package com.pertaminalubricants.mysfa.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.List;

import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by nunu on 11/6/2016.
 */

public abstract class FilterableRealmBaseAdapter <T extends RealmObject> extends ArrayAdapter<T> implements Filterable {

    private final RealmResults<T> mRealmObjectList;
    private List<T> mResults;

    public FilterableRealmBaseAdapter(Context context, @LayoutRes int layout, RealmResults<T> realmObjectList) {
        super(context, layout);
        mRealmObjectList = realmObjectList;
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
                // do nothing here because it's executed in another thread and Realm really
                // doesn't like treating data from another thread.
                final FilterResults results = new FilterResults();
                results.count = mHasResults ? 1 : 0; // AutoCompleteTextView already hides dropdown here if count is 0, so correct it.
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // back on the main thread, we can do the query and notify
                if (constraint != null) {
                    mResults = performRealmFiltering(constraint, mRealmObjectList);
                    mHasResults = mResults.size() > 0;
                    notifyDataSetChanged();
                }
            }
        };
    }

//    protected abstract List<T> performRealmFiltering(@NonNull CharSequence constraint, RealmResults<T> results);
    public List<T> performRealmFiltering(@NonNull CharSequence constraint, RealmResults<T> results){
        if (constraint != null) {
            mResults = mRealmObjectList.where().contains("name", String.valueOf(constraint)).findAll();
        }
        return mResults;
    }
}