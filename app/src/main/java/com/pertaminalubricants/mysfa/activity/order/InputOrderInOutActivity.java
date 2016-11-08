package com.pertaminalubricants.mysfa.activity.order;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blunderer.materialdesignlibrary.handlers.ActionBarDefaultHandler;
import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private EditText etCustomer,etCustomerId, etVat, etDate;
    private TextView tvTotal;
    private AutoCompleteTextView acCustomer;
    private Context ctx;
    private PopupWindow calendarPopup;
    private View mView;
    private double total;
    private SessionManager session;
    private AlertDialog alertDialog;
//    private android.widget.AutoCompleteTextView text;
//    private String[] languages={"Android ","java","IOS","SQL","JDBC","Web services"};

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
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idOrder = Integer.parseInt(extras.getString("id"));
        }

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        this.realm = RealmController.with(this).getRealm();

//        if (!Prefs.with(this).getPreLoad()) {
//            setOrderData();
//        }

        // refresh the realm instance
        RealmController.with(this).clearAll();
        RealmController.with(this).refresh();
        // get all persisted objects
        // create the helper adapter and notify data set changes
        // changes will be reflected automatically

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

        fabNewOrder = (FloatingActionButton) findViewById(R.id.button_add_item);
        fabNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("mySFA","masuk on click floating");
                Intent i = new Intent();
                i = new Intent(ctx, InputOrderItemActivity.class);
                startActivityForResult(i, 200);
//                if (cekValid()){
//                    doChangePassword();
//                }
            }
        });
        setupActionBar();
//        setHasOptionsMenu(true);
    }

    public ListAdapter getListAdapter() {
        order = RealmController.with(this).getOrderTmp();
        RealmResults<OrderDetailTmp> listCustomer = RealmController.with(this).getOrderDetailTmps();
        return new OrderItemListAdapter(this, listCustomer);

//        return new OrderInOutListAdapter(this.getActivity(), new ArrayList<OrderInOut>());
    }

//    public void setRealmAdapter() {
//        Ordertmp order = RealmController.with(this).getOrderTmp();
//        RealmResults<OrderDetailTmp> listOrderDetail = RealmController.with(this).getOrderDetailTmps();
//        orderAdapter = new OrderItemListAdapter(this, listOrderDetail);
//        orderAdapter.notifyDataSetChanged();
//
////        RealmBooksAdapter realmAdapter = new RealmBooksAdapter(this.getApplicationContext(), books, true);
////        // Set the data and tell the RecyclerView to draw
////        adapter.setRealmAdapter(realmAdapter);
////        adapter.notifyDataSetChanged();
//
//    }

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
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);


        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.head_action_label, null);
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

        ImageView mAction = (ImageView) mCustomView.findViewById(R.id.action_icon);
        mAction.setImageResource(R.mipmap.ic_send);
        mAction.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveOrder();
//                Intent i = new Intent();
//                i = new Intent(InputOrderInOutActivity.this, MainActivity.class);
//                startActivity(i);
//                finish();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public void saveOrder(){

        Map<String, String> params = generateOrderInOutParam();

        SalesService apiService = ServiceGenerator.createService(SalesService.class);
        Call<SalesInOutResponse> call = apiService.addOrder(params,session.getToken());
//
        call.enqueue(new Callback<SalesInOutResponse>() {
            @Override
            public void onResponse(Call<SalesInOutResponse> call, Response<SalesInOutResponse> response) {
                if(response.code() == 200) {
                    saveOrderItem(response.body().getId());
                    alertDialog = new AlertDialog.Builder(InputOrderInOutActivity.this).create();
                    TextView myMsg = new TextView(InputOrderInOutActivity.this);
                    myMsg.setText("Entry data sukses");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextColor(Color.BLACK);
                    myMsg.setPadding(15, 15, 15, 15);
                    alertDialog.setView(myMsg);
                    alertDialog.show();
//                    Intent intent = new Intent(InputOrderInOutActivity.this, SplashScreenActivity.class);
//                    startActivity(intent);
                    finish();
                }else{
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
                // Log error here since request failed
//                Log.e("sales", t.toString());
//                closeDialog();
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
            Map<String, String> params = new HashMap<String, String>();
            params.put("name",detail.getProductName());
            params.put("code",detail.getProductCode());
            params.put("qty",String.valueOf(detail.getQty()));
            params.put("unit_price",String.valueOf(detail.getPrice()));
            params.put("supply_by_secondary","0");
            params.put("id_sales_trx",id);
            params.put("id_material",detail.getProductMaterial());
            sendOrderItem(id, params);
        }
    }

    public void sendOrderItem(String id, Map<String, String> params){

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
    protected ActionBarHandler getActionBarHandler() {
        return new ActionBarDefaultHandler(this);
    }

    @Override
    protected boolean enableActionBarShadow(){
        return true;
    }

    private Map<String, String> generateOrderInOutParam(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("trx_type","SALES_ORDER");
        params.put("do_at","2016-11-01");
        params.put("pending_do","0");
        params.put("sales_status","NEW");
        params.put("do_status","");
        params.put("is_secondary","0");
        params.put("dist_type","");
        params.put("id_parent","84");
        params.put("id_salesman",session.getID());
        params.put("code","43210");
        params.put("plant","");
        params.put("shipto","");
        params.put("delivnumb","");
        params.put("actualgidate","2016-11-01");
        params.put("invoiceno","");
        params.put("created_at","2016-11-01");
        params.put("invoiceno","2016-11-01");
        params.put("updated_at","2016-11-01");
        params.put("id_distributor",session.getDistributor());
        params.put("id_customer",etCustomerId.getText().toString());
        params.put("id_user",session.getID());
        params.put("id_secondary","0");
        params.put("id_handler","0");
        params.put("id_followup","0");
        return params;
    }
}
