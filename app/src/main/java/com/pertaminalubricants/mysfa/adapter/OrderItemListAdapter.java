package com.pertaminalubricants.mysfa.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pertaminalubricants.mysfa.R;
import com.pertaminalubricants.mysfa.model.OrderDetailTmp;

import java.text.DecimalFormat;
import java.util.List;

import io.realm.RealmResults;

//import com.squareup.picasso.Picasso;

/**
 * Created by nunu on 12/24/2015.
 */
public class OrderItemListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<OrderDetailTmp> listOrderDetailTmp;

    public OrderItemListAdapter(Activity activity, List<OrderDetailTmp> listOrderDetailTmp) {
        this.activity = activity;
        this.listOrderDetailTmp = listOrderDetailTmp;
    }

    public OrderItemListAdapter(Activity activity, RealmResults<OrderDetailTmp> listOrderDetailTmp) {
        this.activity = activity;
        this.listOrderDetailTmp = listOrderDetailTmp;
    }

    @Override
    public int getCount() {
        return listOrderDetailTmp.size();
    }

    @Override
    public Object getItem(int location) {
        return listOrderDetailTmp.get(location);
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
            convertView = inflater.inflate(R.layout.listview_row_ordertmp, null);

        TextView name = (TextView) convertView.findViewById(R.id.product_name);
        TextView qty = (TextView) convertView.findViewById(R.id.order_qty);
        TextView total = (TextView) convertView.findViewById(R.id.order_total);
        TextView price = (TextView) convertView.findViewById(R.id.order_price);

        DecimalFormat df = new DecimalFormat("Rp###,###.##");
        OrderDetailTmp m = listOrderDetailTmp.get(position);
        name.setText(m.getProductCode()+"-"+m.getProductName());
        qty.setText(m.getQty()+" items");
        price.setText("@"+df.format(m.getPrice()).toString().replace(",","."));
        total.setText(df.format(m.getTotal()).replace(",","."));

        return convertView;
    }

}
