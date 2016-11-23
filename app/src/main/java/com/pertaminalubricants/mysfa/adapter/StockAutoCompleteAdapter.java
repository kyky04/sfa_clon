package com.pertaminalubricants.mysfa.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.pertaminalubricants.mysfa.R;
import com.pertaminalubricants.mysfa.library.SessionManager;
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

import io.realm.RealmObject;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nunu on 11/5/2016.
 */

public class StockAutoCompleteAdapter extends ArrayAdapter<StockResponse> {

    Context context;
    int resource, textViewResourceId;
    List<StockResponse> items, tempItems, suggestions;
    private LayoutInflater mInflater;
    private SessionManager session;

    public StockAutoCompleteAdapter(Context context, int resource, int textViewResourceId, List<StockResponse> items, SessionManager sess) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = items; // this makes the difference.
        suggestions = new ArrayList<StockResponse>();
        mInflater = LayoutInflater.from(context);
        session = sess;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.listview_row_stock, parent, false);

        TextView code = (TextView) convertView.findViewById(R.id.stock_code);
        TextView qty = (TextView) convertView.findViewById(R.id.stock_qty);
        TextView name = (TextView) convertView.findViewById(R.id.stock_name);
        getItem(position);
        StockResponse s = (StockResponse) getItem(position);
        if(s.getMaterial() != null){
            code.setText(s.getMaterial().getCode());
            name.setText(s.getMaterial().getName());
        }
        qty.setText(String.valueOf(s.getQty()));

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((StockResponse) resultValue).getName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null && constraint.length() > 2) {
                FilterResults filterResults = new FilterResults();
                filterResults.values = null;
                filterResults.count = 1;
                JSONObject filter = new JSONObject();
                JSONObject where = new JSONObject();
                JSONObject whereMaterial = new JSONObject();
                JSONObject include = new JSONObject();
                JSONObject like = new JSONObject();
                try {
                    where.put("id_distributor", session.getDistributor());

                    like.put("name", "%" + constraint + "%");
                    whereMaterial.put("name", like);
                    include.put("relation", "material");
                    include.put("where", whereMaterial);
                    filter.put("where", where);
                    filter.put("include", include);
                    SalesService apiService2 = ServiceGenerator.createService(SalesService.class);
                    Call<List<StockResponse>> call2 = apiService2.getAllStock(session.getToken(), URLEncoder.encode(filter.toString(), "UTF-8"));
                    call2.enqueue(new Callback<List<StockResponse>>() {
                        @Override
                        public void onResponse(Call<List<StockResponse>> call, Response<List<StockResponse>> response) {
                            if (response.code() == 200) {
                                clear();
                                addAll(response.body());
                                notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<StockResponse>> call, Throwable t) {

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    };
}