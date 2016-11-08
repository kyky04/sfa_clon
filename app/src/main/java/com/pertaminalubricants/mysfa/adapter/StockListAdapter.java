package com.pertaminalubricants.mysfa.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pertaminalubricants.mysfa.R;
import com.pertaminalubricants.mysfa.model.StockResponse;

import java.util.List;

//import com.squareup.picasso.Picasso;

/**
 * Created by nunu on 12/24/2015.
 */
public class StockListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<StockResponse> listStock;
    public StockListAdapter(Activity activity, List<StockResponse> listStock) {
        this.activity = activity;
        this.listStock = listStock;
    }

    @Override
    public int getCount() {
        return listStock.size();
    }

    @Override
    public Object getItem(int location) {
        return listStock.get(location);
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
            convertView = inflater.inflate(R.layout.listview_row_stock, null);

        TextView code = (TextView) convertView.findViewById(R.id.stock_code);
        TextView name = (TextView) convertView.findViewById(R.id.stock_name);
        TextView qty = (TextView) convertView.findViewById(R.id.stock_qty);

        // getting Route data for the row
        StockResponse m = listStock.get(position);
        name.setText(m.getName());
        qty.setText("Stock " + m.getQty());
        code.setText(m.getCode());

        return convertView;
    }

}
