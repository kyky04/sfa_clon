package com.pertaminalubricants.mysfa.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pertaminalubricants.mysfa.R;
import com.pertaminalubricants.mysfa.model.OrderInOut;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import io.realm.RealmResults;

//import com.squareup.picasso.Picasso;

/**
 * Created by nunu on 12/24/2015.
 */
public class OrderInOutListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<OrderInOut> listOrderInOut;

    public OrderInOutListAdapter(Activity activity, List<OrderInOut> listOrderInOut) {
        this.activity = activity;
        this.listOrderInOut = listOrderInOut;
    }

    public OrderInOutListAdapter(Activity activity, RealmResults<OrderInOut> listOrderInOut) {
        this.activity = activity;
        this.listOrderInOut = listOrderInOut;
    }

    @Override
    public int getCount() {
        return listOrderInOut.size();
    }

    @Override
    public Object getItem(int location) {
        return listOrderInOut.get(location);
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
            convertView = inflater.inflate(R.layout.listview_row_orderinout, null);

        TextView tvDate = (TextView) convertView.findViewById(R.id.tv_order_date);
        TextView tvQty = (TextView) convertView.findViewById(R.id.tv_order_total_volume);
        TextView tvTotal = (TextView) convertView.findViewById(R.id.tv_order_total);
        TextView tvCode = (TextView) convertView.findViewById(R.id.tv_code);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.tv_status);

        // getting Route data for the row
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        OrderInOut m = listOrderInOut.get(position);
        tvDate.setText(format.format(m.getDate()));
        tvQty.setText("Total ("+m.getTotalVolume()+") items");
        DecimalFormat df = new DecimalFormat("###,###.##");
        tvTotal.setText("Rp. "+df.format(m.getTotalOrder()).toString().replace(",", "."));
        tvCode.setText(m.getCode());
        tvStatus.setText(m.getStatus());

        return convertView;
    }

}
