package com.pertaminalubricants.mysfa.activity.customer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blunderer.materialdesignlibrary.handlers.ActionBarDefaultHandler;
import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.pertaminalubricants.mysfa.R;
import com.pertaminalubricants.mysfa.activity.StandarActivity;
import com.pertaminalubricants.mysfa.fragment.ListViewCustomerFragment;
import com.pertaminalubricants.mysfa.library.CommonUtil;
import com.pertaminalubricants.mysfa.library.SessionManager;
import com.pertaminalubricants.mysfa.model.CustomerResponse;
import com.pertaminalubricants.mysfa.realm.RealmController;
import com.pertaminalubricants.mysfa.rest.CustomerService;
import com.pertaminalubricants.mysfa.rest.ServiceGenerator;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nunu on 10/31/2016.
 */

public class InputCustomerActivity extends StandarActivity {

    private Realm realm;
    private FloatingActionButton fabNewOrder;
    private EditText etCode,etName, etAddress, etPhone, etFax, etLat, etLong;
    private Context ctx;
    private View mView;
    private Button btCancel, btSave;
    private SessionManager session;
    private ProgressDialog dialog;
    private AlertDialog alertDialog;

    public InputCustomerActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, com.blunderer.materialdesignlibrary.R.layout.mdl_activity);

        ViewStub stub = (ViewStub) findViewById(com.blunderer.materialdesignlibrary.R.id.view_stub);
        stub.setLayoutResource(getContentView());
        mView = stub.inflate();

        ctx = getBaseContext();
        session = new SessionManager(ctx);
        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            idOrder = Integer.parseInt(extras.getString("id"));
//        }

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        this.realm = RealmController.with(this).getRealm();

//        if (!Prefs.with(this).getPreLoad()) {
//            setData();
//        }

        // refresh the realm instance
        RealmController.with(this).refresh();
        // get all persisted objects
        // create the helper adapter and notify data set changes
        // changes will be reflected automatically

        etCode = (EditText) findViewById(R.id.et_code);
        etName = (EditText) findViewById(R.id.et_name);
        etAddress = (EditText) findViewById(R.id.et_address);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etFax = (EditText) findViewById(R.id.et_fax);
        etLat = (EditText) findViewById(R.id.et_latitude);
        etLong = (EditText) findViewById(R.id.et_longitude);

        btCancel = (Button) findViewById(R.id.bt_cancel);
        btSave = (Button) findViewById(R.id.bt_save);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(InputCustomerActivity.this, InputOrderInOutActivity.class);
                finish();
            }
        });

        setupActionBar();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_input_customer;
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

        View mCustomView = mInflater.inflate(R.layout.head_no_action, null);
        TextView mTitle = (TextView) mCustomView.findViewById(R.id.title);
        mTitle.setText("Entry Customer");

        ImageView mIcon = (ImageView) mCustomView.findViewById(R.id.home_icon);
        mIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public boolean validation(){
        if (etCode.getText().toString().length() == 0){
            Snackbar.make(mView,"Code is required.", Snackbar.LENGTH_SHORT).show();
            return false;
        }else if (etName.getText().toString().length() == 0){
            Snackbar.make(mView,"Name is required", Snackbar.LENGTH_SHORT).show();
            return false;
        }else if (etAddress.getText().toString().length() == 0){
            Snackbar.make(mView,"Address is required", Snackbar.LENGTH_SHORT).show();
            return false;
        }else if (etPhone.getText().toString().length() == 0){
            Snackbar.make(mView,"Phone is required", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void save(){
        if(validation()) {
            openDialog();
            Map<String, String> params = new HashMap<String, String>();
            params.put("code",etCode.getText().toString());
            params.put("name",etName.getText().toString());
            params.put("address_1",etAddress.getText().toString());
            params.put("phone",etPhone.getText().toString());
            params.put("fax",etFax.getText().toString());
            params.put("lat",etLat.getText().toString());
            params.put("long",etLong.getText().toString());
            params.put("active","1");
            params.put("is_deleted","0");
            params.put("created_at", CommonUtil.getCurrentDate());
            params.put("id_region","1");
            Intent i = new Intent();

            CustomerService apiService = ServiceGenerator.createService(CustomerService.class);
            Call<CustomerResponse> call = apiService.addCustomer(params,session.getToken());
//
            call.enqueue(new Callback<CustomerResponse>() {
                @Override
                public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                    closeDialog();
                    if(response.code() == 200) {
//                        alertDialog = new AlertDialog.Builder(InputCustomerActivity.this).create();
//                        TextView myMsg = new TextView(InputCustomerActivity.this);
//                        myMsg.setText("Entry data sukses");
//                        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
//                        myMsg.setTextColor(Color.BLACK);
//                        myMsg.setPadding(15, 15, 15, 15);
//                        alertDialog.setView(myMsg);
//                        alertDialog.show();

                        finish();
                    }else{
                        alertDialog = new AlertDialog.Builder(InputCustomerActivity.this).create();
                        TextView myMsg = new TextView(InputCustomerActivity.this);
                        myMsg.setText("Entry data gagal : "+response.errorBody());
                        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                        myMsg.setTextColor(Color.BLACK);
                        myMsg.setPadding(15, 15, 15, 15);
                        alertDialog.setView(myMsg);
                        alertDialog.show();
                    }
                }

                @Override
                public void onFailure(Call<CustomerResponse> call, Throwable t) {
                    closeDialog();
                    alertDialog = new AlertDialog.Builder(InputCustomerActivity.this).create();
                    TextView myMsg = new TextView(InputCustomerActivity.this);
                    myMsg.setText("Terjadi kesalahan pada server. Silahkan ulangi beberapa saat lagi.");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextColor(Color.BLACK);
                    myMsg.setPadding(15, 15, 15, 15);
                    alertDialog.setView(myMsg);
                    alertDialog.show();
                }
            });

        }
    }

    private void closeDialog(){
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void openDialog(){
        dialog = ProgressDialog.show(this,"Process saving customer","Please wait...",false,false);
    }

    @Override
    protected ActionBarHandler getActionBarHandler() {
        return new ActionBarDefaultHandler(this);
    }

    @Override
    protected boolean enableActionBarShadow(){
        return true;
    }

}
