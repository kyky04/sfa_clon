package com.pertaminalubricants.mysfa.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.pertaminalubricants.mysfa.R;
import com.pertaminalubricants.mysfa.activity.order.InputOrderInOutActivity;
import com.pertaminalubricants.mysfa.adapter.OrderInOutListAdapter;
import com.pertaminalubricants.mysfa.library.SessionManager;
import com.pertaminalubricants.mysfa.model.OrderInOut;
import com.pertaminalubricants.mysfa.model.SalesInOutResponse;
import com.pertaminalubricants.mysfa.rest.SalesService;
import com.pertaminalubricants.mysfa.rest.ServiceGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nunu on 10/29/2016.
 */

public class OrderFragment extends com.blunderer.materialdesignlibrary.fragments.ListViewFragment {

    private List<String> mObjects;
    private ArrayAdapter<String> mAdapter;
    private ListAdapter adapter;
    private SessionManager session;
    private OrderInOut selectedOrderInOut;
    private FloatingActionButton fabNewOrder;

    private ProgressDialog dialog;
    private Context ctx;
    Menu menu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview_customer, container, false);
        ctx = this.getContext();
        session = new SessionManager(ctx);

        ViewStub stub = (ViewStub) view.findViewById(com.blunderer.materialdesignlibrary.R.id.fragment_listview_view);
        try {
            stub.setLayoutResource(com.blunderer.materialdesignlibrary.R.layout.mdl_listview);
            View inflatedView = stub.inflate();

            if (inflatedView instanceof ListView)
                mListView = (ListView) inflatedView;
            else mListView = (ListView) inflatedView.findViewById(android.R.id.list);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "CustomContentView must have a valid layoutResource");
        }

        mSwipeRefreshLayout = (SwipeRefreshLayout)
                view.findViewById(com.blunderer.materialdesignlibrary.R.id.fragment_listview_refresh);
        if (pullToRefreshEnabled()) {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                @Override
                public void onRefresh() {
                    OrderFragment.this.onRefresh();
                }

            });
            mSwipeRefreshLayout.setColorSchemeResources(getPullToRefreshColorResources());
        } else mSwipeRefreshLayout.setEnabled(false);

        if (mListView != null) {
            adapter = getListAdapter();
            if (adapter != null) {
                View headerView = getListViewHeaderView();
                View footerView = getListViewFooterView();
                if (headerView != null) mListView.addHeaderView(headerView);
                if (footerView != null) mListView.addFooterView(footerView);
                mListView.setAdapter(adapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView,
                                            View view, int position, long id) {
                        OrderFragment.this.onItemClick(adapterView, view, position, id);
                    }

                });
                mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView,
                                                   View view, int position, long id) {
                        return OrderFragment.this.onItemLongClick(adapterView, view, position, id);
                    }

                });
            }
        }

        fabNewOrder = (FloatingActionButton) view.findViewById(R.id.button_new_order);
        fabNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(OrderFragment.this.getActivity(), InputOrderInOutActivity.class);
                startActivity(i);
            }
        });

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public ListAdapter getListAdapter() {
        SalesService apiService = ServiceGenerator.createService(SalesService.class);

//                            // Fetch and print a list of the contributors to this library.
        Call<List<SalesInOutResponse>> call = apiService.getAllOrder(session.getToken(), "SALES_ORDER", "NEW", session.getIntID());
//      http://103.43.46.75:8000/api/SalesOrders?filter=%7B%22where%22%3A%20%7B%22trx_type%22%3A%22SALES_ORDER%22%2C%22id_salesman%22%3A%229%22%7D%7D%20&access_token=vAZABskVHZYt7WDgBDdhsfcOBDECDFd2UKvdHlnJ8BEBg4G1jQzHm4BGVIZ1Tojr

        call.enqueue(new Callback<List<SalesInOutResponse>>() {
            @Override
            public void onResponse(Call<List<SalesInOutResponse>> call, Response<List<SalesInOutResponse>> response) {
                List<OrderInOut> listItems = new ArrayList<OrderInOut>();
                try {
                    List<SalesInOutResponse> sales = response.body();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    for(SalesInOutResponse sale: sales){
                        OrderInOut order = new OrderInOut();
                        order.setId(sale.getId());
                        order.setCustId(String.valueOf(sale.getIdCustomer()));
                        order.setDate(format.parse(sale.getCreatedAt()));
//                        order.setTotalOrder(sale.getTotSales());
//                        order.setTotalVolume(sale.getTotVolume());
                        order.setTotalOrder(100);
                        order.setTotalVolume(23000000);
                        order.setCode(sale.getCode());
                        order.setStatus(sale.getSalesStatus());
                        listItems.add(order);
                    }
                    adapter = new OrderInOutListAdapter(OrderFragment.this.getActivity(), listItems);
                    mListView.setAdapter(adapter);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<SalesInOutResponse>> call, Throwable t) {
                // Log error here since request failed
                Log.e("sales", t.toString());
            }
        });

        return new OrderInOutListAdapter(this.getActivity(), new ArrayList<OrderInOut>());
    }

    @Override
    public boolean useCustomContentView() {
        return false;
    }

    @Override
    public int getCustomContentView() {
        return 0;
    }

    @Override
    public boolean pullToRefreshEnabled() {
        return true;
    }

    @Override
    public int[] getPullToRefreshColorResources() {
        return new int[]{R.color.color_primary};
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
//                mObjects.add("Follow up transaction " + (mObjects.size() + 1));
//                mAdapter.notifyDataSetChanged();
                setRefreshing(false);
            }

        }, 200);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        selectedOrderInOut = (OrderInOut) adapterView.getItemAtPosition(position);
        if (menu != null){
            MenuItem menuEdit = menu.findItem(R.id.action_edit);
            menuEdit.setVisible(!menuEdit.isVisible());
        }
//        Log.d("salesapp", "selesai masuk onItemClick");
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

        if (menu != null){
            MenuItem menuEdit = menu.findItem(R.id.action_edit);
//            MenuItem menuCancel = menu.findItem(R.id.action_cancel);

            menuEdit.setVisible(!menuEdit.isVisible());
//            menuCancel.setVisible(!menuCancel.isVisible());

        }

        return false;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
        inflater.inflate(R.menu.menu_sales_process, menu);
        MenuItem menuEdit = this.menu.findItem(R.id.action_edit);
        menuEdit.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_edit){

            Intent i;
            i = new Intent(this.getActivity(), InputOrderInOutActivity.class);
            i.putExtra("id", selectedOrderInOut.getId());
            startActivity(i);
            return true;
        }
//        else if(id == R.id.action_cancel){
//            //Do whatever you want to do
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

}