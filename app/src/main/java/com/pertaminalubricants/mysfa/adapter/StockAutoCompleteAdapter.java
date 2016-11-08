package com.pertaminalubricants.mysfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pertaminalubricants.mysfa.model.StockRealm;

import java.util.List;

import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by nunu on 11/5/2016.
 */

public class StockAutoCompleteAdapter<T extends RealmObject> extends FilterableRealmBaseAdapter  {

    List<T> mResults;
    private final RealmResults<T> mRealmObjectList;
    private LayoutInflater mInflater;

    public StockAutoCompleteAdapter(Context context, RealmResults<T> realmObjectList) {
        super(context, android.R.layout.simple_list_item_1, realmObjectList);
        mInflater = LayoutInflater.from(context);
        mRealmObjectList = realmObjectList;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final TextView tv;
        if (convertView != null) {
            tv = (TextView) convertView;
        } else {
            tv = (TextView) mInflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        }
        getItem(position);
        tv.setText(createFormattedStock((StockRealm) getItem(position)));
        return tv;
    }

    private String createFormattedStock(final StockRealm stock) {
//        mSb.setLength(0);
//        final int addressLineSize = address.getMaxAddressLineIndex();
//        for (int i = 0; i < addressLineSize; i++) {
//            mSb.append(address.getAddressLine(i));
//            if (i != addressLineSize - 1) {
//                mSb.append(", ");
//            }
//        }
//        return mSb.toString();
        return stock.getCode()+" - "+stock.getName();
    }

}