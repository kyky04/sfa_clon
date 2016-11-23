package com.pertaminalubricants.mysfa.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.pertaminalubricants.mysfa.activity.stockopname.InputStockOpnameActivity;
import com.pertaminalubricants.mysfa.adapter.StockOpnameListAdapter;
import com.pertaminalubricants.mysfa.library.Prefs;
import com.pertaminalubricants.mysfa.library.SessionManager;
import com.pertaminalubricants.mysfa.model.CustomerRealm;
import com.pertaminalubricants.mysfa.model.StockOpnameRealm;
import com.pertaminalubricants.mysfa.realm.RealmController;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by nunu on 10/20/2016.
 */

public class ListViewStockOpnameFragment
        extends com.blunderer.materialdesignlibrary.fragments.ListViewFragment {

    private List<String> mObjects;
    private ArrayAdapter<String> mAdapter;
    private ListAdapter adapter;
    private Realm realm;
    private SessionManager session;
    private StockOpnameRealm selectedCustomer;
    private FloatingActionButton fabNewOrder;

    private ProgressDialog dialog;
    private Context ctx;
    Menu menu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview_stockopname, container, false);
        ctx = this.getContext();
        session = new SessionManager(ctx);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this.getActivity())
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        this.realm = RealmController.with(this.getActivity()).getRealm();

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
                    ListViewStockOpnameFragment.this.onRefresh();
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
                        ListViewStockOpnameFragment.this.onItemClick(adapterView, view, position, id);
                    }

                });
                mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView,
                                                   View view, int position, long id) {
                        return ListViewStockOpnameFragment.this.onItemLongClick(adapterView, view, position, id);
                    }

                });
            }
        }

        //setHasOptionsMenu(true);

        return view;
    }

    @Override
    public ListAdapter getListAdapter() {
        RealmController.with(this).clearStockOpname();
        StockOpnameRealm c = new StockOpnameRealm();
        c.setId(2);
        c.setCode("CUST001");
        c.setName("PT. WIJAYA");
        c.setAddress1("Jl. Pahlawan No 43");
        c.setPhone("0218634123");
        c.setDueDate("24-11-2016");
        realm.beginTransaction();
        realm.copyToRealm(c);
        realm.commitTransaction();
        c.setId(3);
        c.setCode("CUST002");
        c.setName("PT. BERLIAN MOTOR");
        c.setAddress1("Jl. Sulaksana No 223");
        c.setPhone("02177165423");
        c.setDueDate("25-11-2016");
        realm.beginTransaction();
        realm.copyToRealm(c);
        realm.commitTransaction();

        Prefs.with(ctx).setPreLoad(true);
        RealmResults<StockOpnameRealm> listCustomer = RealmController.with(this).getAllScheduleStockOpname();
        return new StockOpnameListAdapter(this.getActivity(), listCustomer);

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
        return new int[]{R.color.mdl_color_primary};
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
//                CustomerService apiService = ServiceGenerator.createService(CustomerService.class);
//                Call<List<CustomerResponse>> call = apiService.getAllCustomer(session.getToken());
////
//                call.enqueue(new Callback<List<CustomerResponse>>() {
//                    @Override
//                    public void onResponse(Call<List<CustomerResponse>> call, Response<List<CustomerResponse>> response) {
//                        if(response.code() == 200) {
//                            session.updateLoadCustomer(1);
//                            RealmController.with(getActivity()).clearCustomer();
//                            List<CustomerResponse> cusRes = response.body();
//                            for(CustomerResponse cust : cusRes){
//                                CustomerRealm c = new CustomerRealm();
//                                c.setId(cust.getId());
//                                c.setCode(cust.getCode());
//                                c.setName(cust.getName());
//                                c.setAddress1(cust.getAddress1());
//                                c.setPhone(cust.getPhone());
//                                c.setFax(cust.getFax());
//                                c.setLatitude(cust.getLatitude());
//                                c.setLongitude(cust.getLongitude());
//                                c.setActive(cust.getActive());
//                                c.setIsDeleted(cust.getIsDeleted());
//                                c.setCreatedAt(cust.getCreatedAt());
//                                c.setUpdatedAt(cust.getUpdatedAt());
//                                c.setIdRegion(cust.getIdRegion());
//                                c.setIdAccount(cust.getIdAccount());
//                                realm.beginTransaction();
//                                realm.copyToRealm(c);
//                                realm.commitTransaction();
//                            }
//
//                            Prefs.with(ctx).setPreLoad(true);
//                        }
//                        ListAdapter adapter = getListAdapter();
//                        mListView.setAdapter(adapter);
//                        setRefreshing(false);
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<CustomerResponse>> call, Throwable t) {
////                        alertDialog = new AlertDialog.Builder(InputCustomerActivity.this).create();
////                        TextView myMsg = new TextView(InputCustomerActivity.this);
////                        myMsg.setText("Terjadi kesalahan pada server. Silahkan ulangi beberapa saat lagi.");
////                        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
////                        myMsg.setTextColor(Color.BLACK);
////                        myMsg.setPadding(15, 15, 15, 15);
////                        alertDialog.setView(myMsg);
////                        alertDialog.show();
//                        setRefreshing(false);
//                    }
//                });
//
            }
//
        }, 200);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        selectedCustomer = (StockOpnameRealm) adapterView.getItemAtPosition(position);
        Intent i;
        i = new Intent(this.getActivity(), InputStockOpnameActivity.class);
        i.putExtra("id", String.valueOf(selectedCustomer.getId()));
        startActivity(i);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

//        if (menu != null){
//            MenuItem menuEdit = menu.findItem(R.id.action_edit);
////            MenuItem menuCancel = menu.findItem(R.id.action_cancel);
//
//            menuEdit.setVisible(!menuEdit.isVisible());
////            menuCancel.setVisible(!menuCancel.isVisible());
//
//        }

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
//
//            Intent i;
//            i = new Intent(this.getActivity(), InputOrderInOutActivity.class);
//            i.putExtra("id", selectedCustomer.getId());
//            startActivity(i);
//            return true;
//        }
//        else if(id == R.id.action_cancel){
//            //Do whatever you want to do
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

}