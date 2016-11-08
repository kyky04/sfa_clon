package com.pertaminalubricants.mysfa.activity.order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.pertaminalubricants.mysfa.R;
import com.pertaminalubricants.mysfa.adapter.StockListAdapter;
import com.pertaminalubricants.mysfa.library.SessionManager;
import com.pertaminalubricants.mysfa.model.StockResponse;
import com.pertaminalubricants.mysfa.realm.RealmController;
import com.pertaminalubricants.mysfa.rest.SalesService;
import com.pertaminalubricants.mysfa.rest.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nunu on 11/1/2016.
 */

public class ChooseStockActivity extends com.blunderer.materialdesignlibrary.activities.ListViewActivity {
    private SessionManager session;
    private ListView list_view;
    private Context ctx;
    private Realm realm;
    private ListAdapter adapter;
    private StockResponse selectedStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_customer);
        ctx = getBaseContext();
        session = new SessionManager(getBaseContext());

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        this.realm = RealmController.with(this).getRealm();

        // refresh the realm instance
        RealmController.with(this).refresh();

        ViewStub stub = (ViewStub) findViewById(com.blunderer.materialdesignlibrary.R.id.fragment_listview_view);
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
                findViewById(com.blunderer.materialdesignlibrary.R.id.fragment_listview_refresh);
        if (pullToRefreshEnabled()) {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                @Override
                public void onRefresh() {
                    ChooseStockActivity.this.onRefresh();
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
                        ChooseStockActivity.this.onItemClick(adapterView, view, position, id);
                    }

                });
                mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView,
                                                   View view, int position, long id) {
                        return ChooseStockActivity.this.onItemLongClick(adapterView, view, position, id);
                    }

                });
            }
        }

        setupActionBar();
    }

    @Override
    public ListAdapter getListAdapter() {
        session = new SessionManager(getBaseContext());
        SalesService apiService = ServiceGenerator.createService(SalesService.class);
        Call<List<StockResponse>> call = apiService.getAllStock(session.getToken());
//
        call.enqueue(new Callback<List<StockResponse>>() {
            @Override
            public void onResponse(Call<List<StockResponse>> call, Response<List<StockResponse>> response) {
                    List<StockResponse> listStock = response.body();
//                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                    for(SalesInOutResponse sale: sales){
//                        OrderInOut order = new OrderInOut();
//                        order.setId(sale.getId());
//                        order.setCustId(String.valueOf(sale.getIdCustomer()));
//                        order.setDate(format.parse(sale.getCreatedAt()));
////                        order.setTotalOrder(sale.getTotSales());
////                        order.setTotalVolume(sale.getTotVolume());
//                        order.setTotalOrder(100);
//                        order.setTotalVolume(23000000);
//                        order.setCode(sale.getCode());
//                        order.setStatus(sale.getSalesStatus());
//                        listItems.add(order);
//                    }
                    adapter = new StockListAdapter(ChooseStockActivity.this, listStock);
                    mListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<StockResponse>> call, Throwable t) {
                // Log error here since request failed
                Log.e("sales", t.toString());
            }
        });

        return new StockListAdapter(ChooseStockActivity.this, new ArrayList<StockResponse>());
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        selectedStock = (StockResponse) adapterView.getItemAtPosition(position);
        Intent i = getIntent();
        i = new Intent(ChooseStockActivity.this, InputOrderItemActivity.class);
        i.putExtra("value", selectedStock.getName());
        i.putExtra("id", String.valueOf(selectedStock.getId()));
        i.putExtra("code", selectedStock.getCode());
        i.putExtra("stock", String.valueOf(selectedStock.getQty()));
        i.putExtra("id_material", String.valueOf(selectedStock.getIdMaterial()));
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        selectedStock = (StockResponse) adapterView.getItemAtPosition(position);
        Intent i = getIntent();
        i.putExtra("value", selectedStock.getCode() +" - "+ selectedStock.getName());
        i.putExtra("id", selectedStock.getId());
        i.putExtra("qty", selectedStock.getQty());
        setResult(RESULT_OK, i);
        finish();

        return false;
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
        return new int[]{R.color.mdl_color_primary_dark};
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
    public ActionBarHandler getActionBarHandler(){
        ActionBarHandler mActionBarHandler = null;
        return mActionBarHandler;
    }

    @Override
    public boolean enableActionBarShadow(){
        return true;
    }

    @SuppressLint("InflateParams")
    private void setupActionBar() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.head_no_action, null);
        TextView mTitle = (TextView) mCustomView.findViewById(R.id.title);
        mTitle.setText("Entry Sales Order");

        ImageView mIcon = (ImageView) mCustomView.findViewById(R.id.home_icon);
        mIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Intent i = new Intent();
//                i = new Intent(InputOrderInOutActivity.this, MainActivity.class);
//                startActivity(i);
                finish();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }
}
