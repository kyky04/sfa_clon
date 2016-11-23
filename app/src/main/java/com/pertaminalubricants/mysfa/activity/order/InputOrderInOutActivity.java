package com.pertaminalubricants.mysfa.activity.order;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blunderer.materialdesignlibrary.handlers.ActionBarDefaultHandler;
import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.google.gson.Gson;
import com.pertaminalubricants.mysfa.library.CommonUtil;
import com.phlox.datepick.CalendarNumbersView;
import com.phlox.datepick.CalendarPickerView;
import com.pertaminalubricants.mysfa.R;
import com.pertaminalubricants.mysfa.activity.StandarActivity;
import com.pertaminalubricants.mysfa.adapter.CustomerAutoCompleteAdapter;
import com.pertaminalubricants.mysfa.adapter.OrderItemListAdapter;
import com.pertaminalubricants.mysfa.library.SessionManager;
import com.pertaminalubricants.mysfa.model.CustomerRealm;
import com.pertaminalubricants.mysfa.model.OrderDetailTmp;
import com.pertaminalubricants.mysfa.model.Ordertmp;
import com.pertaminalubricants.mysfa.model.SalesInOutResponse;
import com.pertaminalubricants.mysfa.model.SalesItemResponse;
import com.pertaminalubricants.mysfa.realm.RealmController;
import com.pertaminalubricants.mysfa.rest.SalesService;
import com.pertaminalubricants.mysfa.rest.ServiceGenerator;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nunu on 10/31/2016.
 */

public class InputOrderInOutActivity extends StandarActivity {

    private ListView list_view;
    private ListAdapter orderAdapter;
    private Ordertmp order;
    private Realm realm;
    private List<OrderDetailTmp> listOrderDetails = new ArrayList<OrderDetailTmp>();
    private FloatingActionButton fabNewOrder;
    private int idOrder = 0;
    private EditText etCustomer,etCustomerId, etDate, etCode;
    private TextView tvTotal;
    private AutoCompleteTextView acCustomer;
    private Context ctx;
    private PopupWindow calendarPopup;
    private View mView;
    private double total;
    private SessionManager session;
    private AlertDialog alertDialog;
    private ProgressDialog dialog;

    public InputOrderInOutActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, com.blunderer.materialdesignlibrary.R.layout.mdl_activity);

        ViewStub stub = (ViewStub) findViewById(com.blunderer.materialdesignlibrary.R.id.view_stub);
        stub.setLayoutResource(getContentView());
        mView = stub.inflate();
        ctx = getBaseContext();
        session = new SessionManager(ctx);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        this.realm = RealmController.with(this).getRealm();

        Bundle extras = getIntent().getExtras();

//        if (!Prefs.with(this).getPreLoad()) {
//            setOrderData();
//        }

        // refresh the realm instance

        RealmController.with(this).refresh();
        // get all persisted objects
        // create the helper adapter and notify data set changes
        // changes will be reflected automatically
        etCode = (EditText) findViewById(R.id.et_sp_cd);
        etCustomerId = (EditText) findViewById(R.id.et_customer_id);
//        etCustomer = (EditText) findViewById(R.id.et_customer);

//        etCustomer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent();
//                i = new Intent(ctx, ChooseCustomerActivity.class);
//                i.putExtra("id", "depart");
//                startActivityForResult(i, 200);
//            }
//        });

//        List<CustomerRealm> result = RealmController.with(this).getAllCustomer();
        RealmResults<CustomerRealm> result2 = RealmController.with(this).getAllCustomer();
        acCustomer = (AutoCompleteTextView)findViewById(R.id.autoComplete);
        acCustomer.setAdapter(new CustomerAutoCompleteAdapter(getBaseContext(), result2));
        if (acCustomer != null) {
            if (acCustomer.getAdapter() != null) {
//                acCustomer.setAdapter(adapter);
                acCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView,
                                            View view, int position, long id) {
                        CustomerRealm tmp = (CustomerRealm) adapterView.getItemAtPosition(position);
                        etCustomerId.setText(String.valueOf(tmp.getId()));
                        acCustomer.setText(tmp.getCode()+" - "+tmp.getName());
                    }

                });
            }
        }

        etDate = (EditText) findViewById(R.id.et_date);
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        etDate.setText(date);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendarPopup == null) {
                    calendarPopup = new PopupWindow(InputOrderInOutActivity.this);
                    CalendarPickerView calendarView = new CalendarPickerView(InputOrderInOutActivity.this);
                    calendarView.setListener(dateSelectionListener);
                    calendarPopup.setContentView(calendarView);
                    calendarPopup.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    calendarPopup.setOutsideTouchable(true);
                }
                calendarPopup.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0, 50);
            }
        });

        tvTotal = (TextView) findViewById(R.id.tv_total);
        tvTotal.setText("Rp. 0");


        list_view = (ListView) findViewById(R.id.list_view_item);
        if (list_view != null) {
            orderAdapter = getListAdapter();
            list_view.setAdapter(orderAdapter);
        }

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                OrderDetailTmp detail = (OrderDetailTmp) adapterView.getItemAtPosition(position);
                Intent i = new Intent();
                i = new Intent(ctx, InputOrderItemActivity.class);
                i.putExtra("id", detail.getId());
                startActivityForResult(i, 200);
            }
        });

        fabNewOrder = (FloatingActionButton) findViewById(R.id.button_add_item);
        fabNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i = new Intent(ctx, InputOrderItemActivity.class);
                startActivityForResult(i, 200);
            }
        });

        setupActionBar();

        if (extras != null) {
            idOrder = Integer.parseInt(extras.getString("id"));
            initData();
        }else{
            idOrder =0;
            RealmController.with(this).clearAll();
        }
    }

    public ListAdapter getListAdapter() {
        order = RealmController.with(this).getOrderTmp();
        RealmResults<OrderDetailTmp> listCustomer = RealmController.with(this).getOrderDetailTmps();
        return new OrderItemListAdapter(this, listCustomer);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_input_order;
    }

    @Override
    public View findViewById(int id) {
        if (mView != null) return mView.findViewById(id);
        return super.findViewById(id);
    }

    @SuppressLint("InflateParams")
    private void setupActionBar() {

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("Entry Sales Order");
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_ab_arrow_back);

        LayoutInflater mInflater = LayoutInflater.from(this);

    }

    public void saveOrder(){
        openDialog();
        String params = generateOrderInOutParamString();

        SalesService apiService = ServiceGenerator.createService(SalesService.class);
        Call<SalesInOutResponse> call = apiService.addOrderJson(params,session.getToken());

        call.enqueue(new Callback<SalesInOutResponse>() {
            @Override
            public void onResponse(Call<SalesInOutResponse> call, Response<SalesInOutResponse> response) {
                if(response.code() == 200) {
                    closeDialog();
                    saveOrderItem(response.body().getId());
                }else{
                    closeDialog();
                    alertDialog = new AlertDialog.Builder(InputOrderInOutActivity.this).create();
                    TextView myMsg = new TextView(InputOrderInOutActivity.this);
                    myMsg.setText("Entry data gagal : "+response.errorBody());
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextColor(Color.BLACK);
                    myMsg.setPadding(15, 15, 15, 15);
                    alertDialog.setView(myMsg);
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<SalesInOutResponse> call, Throwable t) {
                closeDialog();
                alertDialog = new AlertDialog.Builder(InputOrderInOutActivity.this).create();
                TextView myMsg = new TextView(InputOrderInOutActivity.this);
                myMsg.setText("Terjadi kesalahan pada server. Silahkan ulangi beberapa saat lagi.");
                myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                myMsg.setTextColor(Color.BLACK);
                myMsg.setPadding(15, 15, 15, 15);
                alertDialog.setView(myMsg);
                alertDialog.show();
            }
        });
    }

    public void saveOrderItem(String id){
        RealmResults<OrderDetailTmp> listOrderItems = RealmController.with(this).getOrderDetailTmps();
        for(OrderDetailTmp detail : listOrderItems){
            SalesItemResponse si = new SalesItemResponse();
            si.setIdSalesTrx(Integer.parseInt(id));
            si.setName(detail.getProductName().toString());
            si.setCode(detail.getProductCode().toString());
            si.setQty(detail.getQty());
            si.setUnitPrice(detail.getPrice());
            si.setUom(detail.getProductGrossWeightUom());
            si.setSupplyBySecondary(detail.getProductSupplyBySecondary());
            si.setIdMaterial(detail.getProductMaterialId());
            Gson gson = new Gson();
            String param = gson.toJson(si);
            sendOrderItem(id, param);
        }
        finish();
    }

    public void sendOrderItem(String id, String params){

        SalesService apiService = ServiceGenerator.createService(SalesService.class);
        Call<SalesItemResponse> call = apiService.addOrderItem(id, params,session.getToken());

        call.enqueue(new Callback<SalesItemResponse>() {
            @Override
            public void onResponse(Call<SalesItemResponse> call, Response<SalesItemResponse> response) {
                if(response.code() == 200) {

                }else{

                }
            }

            @Override
            public void onFailure(Call<SalesItemResponse> call, Throwable t) {

            }
        });
    }

    public void finalizeOrder(){
        openDialog();
        SalesInOutResponse so = new SalesInOutResponse();
        so.setId(String.valueOf(idOrder));
        so.setIsFinal(1);
        so.setUpdatedAt(CommonUtil.convertDate(etDate.getText().toString()));
        Gson gson = new Gson();
        String param = gson.toJson(so);

        SalesService apiService = ServiceGenerator.createService(SalesService.class);
        Call<SalesInOutResponse> call = apiService.setOrderToFinal(String.valueOf(idOrder), param,session.getToken());

        call.enqueue(new Callback<SalesInOutResponse>() {
            @Override
            public void onResponse(Call<SalesInOutResponse> call, Response<SalesInOutResponse> response) {
                if(response.code() == 200) {
                    closeDialog();
                    finish();
                }else{
                    closeDialog();
                    alertDialog = new AlertDialog.Builder(InputOrderInOutActivity.this).create();
                    TextView myMsg = new TextView(InputOrderInOutActivity.this);
                    myMsg.setText("Finalisasi order gagal. ");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextColor(Color.BLACK);
                    myMsg.setPadding(15, 15, 15, 15);
                    alertDialog.setView(myMsg);
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<SalesInOutResponse> call, Throwable t) {
                closeDialog();
                alertDialog = new AlertDialog.Builder(InputOrderInOutActivity.this).create();
                TextView myMsg = new TextView(InputOrderInOutActivity.this);
                myMsg.setText("Terjadi kesalahan pada server. Silahkan ulangi beberapa saat lagi.");
                myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                myMsg.setTextColor(Color.BLACK);
                myMsg.setPadding(15, 15, 15, 15);
                alertDialog.setView(myMsg);
                alertDialog.show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null && data.getExtras().containsKey("value")) {
            etCustomer.setText(data.getStringExtra("value"));
            etCustomerId.setText(data.getStringExtra("id"));
        }else if(data != null && data.getExtras().containsKey("save")) {
            RealmResults<OrderDetailTmp> listOrderItems = RealmController.with(this).getOrderDetailTmps();
            orderAdapter =  new OrderItemListAdapter(this, listOrderItems);
            list_view.setAdapter(orderAdapter);
            list_view.refreshDrawableState();

            total = 0.0;
            for(OrderDetailTmp detail : listOrderItems){
                total = total + detail.getTotal();
            }
            DecimalFormat df = new DecimalFormat("###,###.##");
            tvTotal.setText("Rp. "+df.format(total).toString().replace(",", "."));
        }
    }

    private CalendarNumbersView.DateSelectionListener dateSelectionListener = new CalendarNumbersView.DateSelectionListener() {
        @Override
        public void onDateSelected(Calendar selectedDate) {
            if (calendarPopup.isShowing()) {
                calendarPopup.getContentView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        calendarPopup.dismiss();
                    }
                }, 500);//For clarity, we close the popup not immediately.,
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            etDate.setText(formatter.format(selectedDate.getTime()));
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (getIntent().getExtras() == null) {
            getMenuInflater().inflate(R.menu.menu_input_sales_order, menu);
        }else getMenuInflater().inflate(R.menu.menu_input_sales_order2, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }else if (item.getItemId() == R.id.save) {
            saveOrder();
        }else if (item.getItemId() == R.id.senttoinvoice) {
            finalizeOrder();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected ActionBarHandler getActionBarHandler() {
        return new ActionBarDefaultHandler(this);
    }

    @Override
    protected boolean enableActionBarShadow(){
        return true;
    }

    private String generateOrderInOutParamString(){
        int totalVolume = 0;
        long totalSales = 0;
        RealmResults<OrderDetailTmp> listOrderItems = RealmController.with(this).getOrderDetailTmps();
        for(OrderDetailTmp detail : listOrderItems){
            int totVol = 0;
            long totSal = 0;
            try {
                totVol = detail.getQty() * Integer.parseInt(detail.getProductGrossWeightUom());
            }catch (Exception e){

            }

            try {
                totSal = detail.getQty() * detail.getPrice();
            }catch (Exception e){

            }
            totalVolume = totalVolume + totVol;
            totalSales = totalSales + totSal;

        }

        SalesInOutResponse so = new SalesInOutResponse();
        so.setCode(etCode.getText().toString());
        so.setInvoiceNo(so.getCode());
        so.setSalesStatus("NEW");
        so.setIdParent(idOrder);
        so.setIdSalesman(Integer.parseInt(session.getID()));
        so.setTotVolume(totalVolume);
        so.setTotSales(totalSales);
        so.setIdDistributor(Integer.parseInt(session.getDistributor()));
        so.setIdCustomer(Integer.parseInt(etCustomerId.getText().toString()));
        so.setCreatedAt(CommonUtil.convertDate(etDate.getText().toString()));
        Gson gson = new Gson();
        String param = gson.toJson(so);
        return param;
    }

    private void initData(){
        Ordertmp order = RealmController.with(this).getOrderTmp();
        etCode.setText(order.getCode());
        DecimalFormat df = new DecimalFormat("###,###.##");
        tvTotal.setText("Rp. "+df.format(order.getTotalOrder()).toString().replace(",", "."));
        CustomerRealm customer = RealmController.with(this).getCustomer(Integer.parseInt(order.getCustId()));
        acCustomer.setText(customer.getCode()+"-"+customer.getName());
        etCustomerId.setText(order.getCustId().toString());
    }

    private void closeDialog(){
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void openDialog(){
        dialog = ProgressDialog.show(this,"Processing","Please wait...",false,false);
    }
}
