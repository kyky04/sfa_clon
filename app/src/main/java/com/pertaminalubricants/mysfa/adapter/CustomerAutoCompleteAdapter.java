package com.pertaminalubricants.mysfa.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pertaminalubricants.mysfa.model.CustomerRealm;

import java.util.List;

import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by nunu on 11/5/2016.
 */

public class CustomerAutoCompleteAdapter <T extends RealmObject> extends FilterableRealmBaseAdapter  {

    List<T> mResults;
    private final RealmResults<T> mRealmObjectList;
    private LayoutInflater mInflater;

    public CustomerAutoCompleteAdapter(Context context, RealmResults<T> realmObjectList) {
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
        tv.setText(createFormattedCustomer((CustomerRealm) getItem(position)));
        return tv;
    }

    private String createFormattedCustomer(final CustomerRealm customer) {
//        mSb.setLength(0);
//        final int addressLineSize = address.getMaxAddressLineIndex();
//        for (int i = 0; i < addressLineSize; i++) {
//            mSb.append(address.getAddressLine(i));
//            if (i != addressLineSize - 1) {
//                mSb.append(", ");
//            }
//        }
//        return mSb.toString();
        return customer.getCode()+" - "+customer.getName();
    }

//    public List<T> performRealmFiltering(@NonNull CharSequence constraint, RealmResults<T> results){
////    public void performRealmFiltering(@NonNull CharSequence constraint, RealmResults<T> results){
//
//        if (constraint != null) {
//            mResults = mRealmObjectList.where().contains("name", String.valueOf(constraint)).findAll();
//        }
//        return mResults;
//    }


//    @Override
//    protected List<T> performRealmFiltering(@NonNull CharSequence constraint, RealmResults<T> results){
//        if (constraint != null) {
//            mResults = mRealmObjectList.where().contains("name", String.valueOf(constraint)).findAll();
//        }
//        return mResults;
//    }
}