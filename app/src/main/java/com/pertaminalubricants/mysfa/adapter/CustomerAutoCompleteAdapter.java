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
import com.pertaminalubricants.mysfa.R;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.listview_row_customer, parent, false);

        TextView name = (TextView) convertView.findViewById(R.id.cust_name);
        TextView phone = (TextView) convertView.findViewById(R.id.cust_phone);
        TextView address = (TextView) convertView.findViewById(R.id.cust_location);

        // getting Route data for the row
        CustomerRealm m = (CustomerRealm) getItem(position);
        name.setText(m.getName());
        phone.setText("Phone " + m.getPhone());
        address.setText(m.getAddress1());

        return convertView;
    }
}