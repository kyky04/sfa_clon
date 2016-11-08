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
import android.view.Gravity;
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
import android.widget.TextView;

import com.pertaminalubricants.mysfa.R;
import com.pertaminalubricants.mysfa.activity.LoginActivity;
import com.pertaminalubricants.mysfa.activity.customer.InputCustomerActivity;
import com.pertaminalubricants.mysfa.activity.order.InputOrderInOutActivity;
import com.pertaminalubricants.mysfa.adapter.CustomerListAdapter;
import com.pertaminalubricants.mysfa.library.Prefs;
import com.pertaminalubricants.mysfa.library.SessionManager;
import com.pertaminalubricants.mysfa.model.CustomerRealm;
import com.pertaminalubricants.mysfa.model.CustomerResponse;
import com.pertaminalubricants.mysfa.realm.RealmController;
import com.pertaminalubricants.mysfa.rest.CustomerService;
import com.pertaminalubricants.mysfa.rest.ServiceGenerator;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nunu on 10/20/2016.
 */

public class ListViewCustomerFragment
        extends com.blunderer.materialdesignlibrary.fragments.ListViewFragment {

    private List<String> mObjects;
    private ArrayAdapter<String> mAdapter;
    private ListAdapter adapter;
    private Realm realm;
    private SessionManager session;
    private CustomerRealm selectedCustomer;
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
                    ListViewCustomerFragment.this.onRefresh();
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
                        ListViewCustomerFragment.this.onItemClick(adapterView, view, position, id);
                    }

                });
                mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView,
                                                   View view, int position, long id) {
                        return ListViewCustomerFragment.this.onItemLongClick(adapterView, view, position, id);
                    }

                });
            }
        }

        fabNewOrder = (FloatingActionButton) view.findViewById(R.id.button_new_order);
        fabNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(ListViewCustomerFragment.this.getActivity(), InputCustomerActivity.class);
                startActivity(i);
            }
        });

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public ListAdapter getListAdapter() {
        RealmResults<CustomerRealm> listCustomer = RealmController.with(this).getAllCustomer();
        return new CustomerListAdapter(this.getActivity(), listCustomer);

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
                CustomerService apiService = ServiceGenerator.createService(CustomerService.class);
                Call<List<CustomerResponse>> call = apiService.getAllCustomer(session.getToken());
//
                call.enqueue(new Callback<List<CustomerResponse>>() {
                    @Override
                    public void onResponse(Call<List<CustomerResponse>> call, Response<List<CustomerResponse>> response) {
                        if(response.code() == 200) {
                            session.updateLoadCustomer(1);
                            RealmController.with(getActivity()).clearCustomer();
                            List<CustomerResponse> cusRes = response.body();
                            for(CustomerResponse cust : cusRes){
                                CustomerRealm c = new CustomerRealm();
                                c.setId(cust.getId());
                                c.setCode(cust.getCode());
                                c.setName(cust.getName());
                                c.setAddress1(cust.getAddress1());
                                c.setPhone(cust.getPhone());
                                c.setFax(cust.getFax());
                                c.setLatitude(cust.getLatitude());
                                c.setLongitude(cust.getLongitude());
                                c.setActive(cust.getActive());
                                c.setIsDeleted(cust.getIsDeleted());
                                c.setCreatedAt(cust.getCreatedAt());
                                c.setUpdatedAt(cust.getUpdatedAt());
                                c.setIdRegion(cust.getIdRegion());
                                c.setIdAccount(cust.getIdAccount());
                                realm.beginTransaction();
                                realm.copyToRealm(c);
                                realm.commitTransaction();
                            }

                            Prefs.with(ctx).setPreLoad(true);
                        }
                        ListAdapter adapter = getListAdapter();
                        mListView.setAdapter(adapter);
                        setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<List<CustomerResponse>> call, Throwable t) {
//                        alertDialog = new AlertDialog.Builder(InputCustomerActivity.this).create();
//                        TextView myMsg = new TextView(InputCustomerActivity.this);
//                        myMsg.setText("Terjadi kesalahan pada server. Silahkan ulangi beberapa saat lagi.");
//                        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
//                        myMsg.setTextColor(Color.BLACK);
//                        myMsg.setPadding(15, 15, 15, 15);
//                        alertDialog.setView(myMsg);
//                        alertDialog.show();
                        setRefreshing(false);
                    }
                });

            }

        }, 200);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        selectedCustomer = (CustomerRealm) adapterView.getItemAtPosition(position);
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
            i.putExtra("id", selectedCustomer.getId());
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