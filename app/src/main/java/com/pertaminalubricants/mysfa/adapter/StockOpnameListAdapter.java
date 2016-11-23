package com.pertaminalubricants.mysfa.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pertaminalubricants.mysfa.R;
import com.pertaminalubricants.mysfa.model.CustomerRealm;
import com.pertaminalubricants.mysfa.model.StockOpnameRealm;

import java.util.List;

//import com.squareup.picasso.Picasso;

/**
 * Created by nunu on 12/24/2015.
 */
public class StockOpnameListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<StockOpnameRealm> CustomerItems;

    public StockOpnameListAdapter(Activity activity, List<StockOpnameRealm> CustomerItems) {
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
            convertView = inflater.inflate(R.layout.listview_row_scheduleopname, null);

        TextView name = (TextView) convertView.findViewById(R.id.cust_name);
        TextView address = (TextView) convertView.findViewById(R.id.cust_location);
        TextView dueDate = (TextView) convertView.findViewById(R.id.cust_due_date);

        // getting Route data for the row
        StockOpnameRealm m = CustomerItems.get(position);
        name.setText(m.getName());
        address.setText(m.getAddress1()+ ", Phone " +m.getPhone());
        dueDate.setText("Schedule to visit "+m.getDueDate());

        return convertView;
    }

}
