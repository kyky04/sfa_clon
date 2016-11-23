package com.pertaminalubricants.mysfa.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pertaminalubricants.mysfa.R;
import com.pertaminalubricants.mysfa.activity.LoginActivity;
import com.pertaminalubricants.mysfa.activity.order.InputOrderInOutActivity;
import com.pertaminalubricants.mysfa.adapter.OrderInOutListAdapter;
import com.pertaminalubricants.mysfa.library.SessionManager;
import com.pertaminalubricants.mysfa.model.OrderDetailTmp;
import com.pertaminalubricants.mysfa.model.OrderInOut;
import com.pertaminalubricants.mysfa.model.Ordertmp;
import com.pertaminalubricants.mysfa.model.SalesInOutResponse;
import com.pertaminalubricants.mysfa.model.SalesItemResponse;
import com.pertaminalubricants.mysfa.realm.RealmController;
import com.pertaminalubricants.mysfa.rest.SalesService;
import com.pertaminalubricants.mysfa.rest.ServiceGenerator;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesProcessFragment extends com.blunderer.materialdesignlibrary.fragments.ListViewFragment {

    private ListAdapter adapter;
    private SessionManager session;
    private OrderInOut selectedOrderInOut;
    private FloatingActionButton fabNewOrder;
    private Realm realm;

    private ProgressDialog dialog;
    private AlertDialog alertDialog;
    private Context ctx;
    Menu menu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview_customer, container, false);
        ctx = this.getContext();
        session = new SessionManager(ctx);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(ctx)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        this.realm = RealmController.with(this).getRealm();

        // refresh the realm instance
        RealmController.with(this).refresh();

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
                    SalesProcessFragment.this.onRefresh();
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
                        Log.d("msfa","item click");
                        SalesProcessFragment.this.onItemClick(adapterView, view, position, id);
                    }

                });
                mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView,
                                                   View view, int position, long id) {
                        Log.d("msfa","item long click");
                        return SalesProcessFragment.this.onItemLongClick(adapterView, view, position, id);
                    }

                });
            }
        }

        fabNewOrder = (FloatingActionButton) view.findViewById(R.id.button_new_order);
        fabNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(SalesProcessFragment.this.getActivity(), InputOrderInOutActivity.class);
                startActivity(i);
            }
        });

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public ListAdapter getListAdapter() {
        SalesService apiService = ServiceGenerator.createService(SalesService.class);
        Call<List<SalesInOutResponse>> call = null;

        JSONObject filter = new JSONObject();
        JSONObject where = new JSONObject();

        try {
            where.put("trx_type","SALES_ORDER");
            where.put("id_salesman",session.getID());
            filter.put("where",where);
            call = apiService.getAllOrder(session.getToken(), URLEncoder.encode(filter.toString(), "UTF-8"));
        } catch (Exception e){

        }

        call.enqueue(new Callback<List<SalesInOutResponse>>() {
            @Override
            public void onResponse(Call<List<SalesInOutResponse>> call, Response<List<SalesInOutResponse>> response) {
                List<OrderInOut> listItems = new ArrayList<OrderInOut>();
                if(response.code() == 200) {
                    try {
                        List<SalesInOutResponse> sales = response.body();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        for (SalesInOutResponse sale : sales) {
                            OrderInOut order = new OrderInOut();
                            order.setId(sale.getId());
                            order.setCustId(String.valueOf(sale.getIdCustomer()));
                            order.setDate((sale.getCreatedAt() == null) ? null : format.parse(sale.getCreatedAt()));
                            order.setTotalOrder(sale.getTotSales());
                            order.setTotalVolume(sale.getTotVolume());
                            order.setCode(sale.getCode());
                            order.setStatus(sale.getSalesStatus());
                            listItems.add(order);
                        }
                        adapter = new OrderInOutListAdapter(SalesProcessFragment.this.getActivity(), listItems);
                        mListView.setAdapter(adapter);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else{
                    alertDialog = new AlertDialog.Builder(getActivity()).create();
                    TextView myMsg = new TextView(getActivity());
                    myMsg.setText("Terjadi kesalahan pada server. Silahkan ulangi beberapa saat lagi.");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextColor(Color.BLACK);
                    myMsg.setPadding(15, 15, 15, 15);
                    alertDialog.setView(myMsg);
                    alertDialog.show();
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
        Log.d("msfa","masuk on item click");
        selectedOrderInOut = (OrderInOut) adapterView.getItemAtPosition(position);
        openEditOrderActivity();
//        if (menu != null){
//            MenuItem menuEdit = menu.findItem(R.id.action_edit);
//            menuEdit.setVisible(!menuEdit.isVisible());
//        }
//        Log.d("salesapp", "selesai masuk onItemClick");
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        alertDialog = new AlertDialog.Builder(getActivity()).create();
        TextView myMsg = new TextView(getActivity());
        myMsg.setText("Username dan password salah.");
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        myMsg.setTextColor(Color.BLACK);
        myMsg.setPadding(15, 15, 15, 15);
        alertDialog.setView(myMsg);
        alertDialog.show();

        return false;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
        inflater.inflate(R.menu.menu_empty, menu);
//        MenuItem menuEdit = this.menu.findItem(R.id.action_edit);
//        menuEdit.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if(id == R.id.action_edit){
////            openEditOrderActivity();
//            return true;
//        }
//        else if(id == R.id.action_cancel){
//            //Do whatever you want to do
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void openEditOrderActivity() {
        openDialog();
        SalesService apiService = ServiceGenerator.createService(SalesService.class);
        Call<List<SalesItemResponse>> call = null;

        call = apiService.getSalesItemsByOrder(selectedOrderInOut.getId(), session.getToken());
        call.enqueue(new Callback<List<SalesItemResponse>>() {
            @Override
            public void onResponse(Call<List<SalesItemResponse>> call, Response<List<SalesItemResponse>> response) {
                closeDialog();
                RealmController.with(getActivity()).clearOrderTmp();
                Ordertmp orderTmp = new Ordertmp();
                orderTmp.setId(selectedOrderInOut.getId());
                orderTmp.setCustId(String.valueOf(selectedOrderInOut.getCustId()));
                orderTmp.setTotalOrder(selectedOrderInOut.getTotalOrder());
                orderTmp.setCode(selectedOrderInOut.getCode());
                orderTmp.setDate(selectedOrderInOut.getDate());
                orderTmp.setIdSalesman(selectedOrderInOut.getIdSalesman());
                orderTmp.setStatus(selectedOrderInOut.getStatus());
                orderTmp.setTotalVolume(selectedOrderInOut.getTotalVolume());
                realm.beginTransaction();
                realm.copyToRealm(orderTmp);
                realm.commitTransaction();

                List<OrderInOut> listItems = new ArrayList<OrderInOut>();
                    List<SalesItemResponse> sales = response.body();
                    for(SalesItemResponse item: sales){
                        OrderDetailTmp tmp = new OrderDetailTmp();
                        tmp.setId(String.valueOf(item.getId()));
                        tmp.setQty(item.getQty());
                        tmp.setProductName(item.getName());
                        tmp.setProductId(String.valueOf(item.getId()));
                        tmp.setProductMaterial(String.valueOf(item.getIdMaterial()));
                        tmp.setProductCode(item.getCode());
                        tmp.setProductGrossWeightUom(item.getUom());
                        tmp.setProductSupplyBySecondary(item.getSupplyBySecondary());
                        tmp.setPrice(item.getUnitPrice());
                        tmp.setTotal(item.getUnitPrice()*item.getQty());
                        realm.beginTransaction();
                        realm.copyToRealm(tmp);
                        realm.commitTransaction();
                    }

                Intent i;
                i = new Intent(getActivity(), InputOrderInOutActivity.class);
                i.putExtra("id", selectedOrderInOut.getId());
                startActivity(i);
            }

            @Override
            public void onFailure(Call<List<SalesItemResponse>> call, Throwable t) {
                // Log error here since request failed
                Log.e("sales", t.toString());
                closeDialog();
            }
        });
    }

    private void closeDialog(){
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void openDialog(){
        dialog = ProgressDialog.show(getActivity(),"Processing","Please wait...",false,false);
    }

}