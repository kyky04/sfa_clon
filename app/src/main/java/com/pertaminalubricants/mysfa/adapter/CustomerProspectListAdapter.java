package com.pertaminalubricants.mysfa.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pertaminalubricants.mysfa.R;
import com.pertaminalubricants.mysfa.model.CustomerProspectRealm;

import java.util.List;

//import com.squareup.picasso.Picasso;

/**
 * Created by nunu on 12/24/2015.
 */
public class CustomerProspectListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<CustomerProspectRealm> CustomerItems;

    public CustomerProspectListAdapter(Activity activity, List<CustomerProspectRealm> CustomerItems) {
        this.activity = activity;
        this.CustomerItems = CustomerItems;
    }

    @Override
    public int getCount() {
        return CustomerItems.size();
    }

    @Override
    public Object getItem(int location) {
        return CustomerItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.listview_row_customer, null);

        TextView name = (TextView) convertView.findViewById(R.id.cust_name);
        TextView phone = (TextView) convertView.findViewById(R.id.cust_phone);
        TextView address = (TextView) convertView.findViewById(R.id.cust_location);

        // getting Route data for the row
//        CustomerResponse m = CustomerItems.get(position);
        CustomerProspectRealm m = CustomerItems.get(position);
        name.setText(m.getName());
        phone.setText("Phone " + m.getPhone());
        address.setText(m.getAddress1());

//        ImageView img = (ImageView) convertView.findViewById(R.id.cust_img);
//        img.setImageResource(Integer.parseInt(m.getImage()));
//        Picasso.with(this.activity).load("http:"+m.getImage())
//                .placeholder(R.drawable.ic_hotel).error(R.drawable.ic_hotel).into(img);

        return convertView;
    }

}
