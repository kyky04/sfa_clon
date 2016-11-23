package com.pertaminalubricants.mysfa.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pertaminalubricants.mysfa.R;
import com.pertaminalubricants.mysfa.library.Prefs;
import com.pertaminalubricants.mysfa.library.SessionManager;
import com.pertaminalubricants.mysfa.listener.OnSwipeTouchListener;
import com.pertaminalubricants.mysfa.model.ContractResponse;
import com.pertaminalubricants.mysfa.model.CustomerProspectRealm;
import com.pertaminalubricants.mysfa.model.CustomerRealm;
import com.pertaminalubricants.mysfa.model.CustomerResponse;
import com.pertaminalubricants.mysfa.model.LoginInfo;
import com.pertaminalubricants.mysfa.model.MaterialRealm;
import com.pertaminalubricants.mysfa.model.StockRealm;
import com.pertaminalubricants.mysfa.model.StockResponse;
import com.pertaminalubricants.mysfa.realm.RealmController;
import com.pertaminalubricants.mysfa.rest.CustomerService;
import com.pertaminalubricants.mysfa.rest.LoginService;
import com.pertaminalubricants.mysfa.rest.SalesService;
import com.pertaminalubricants.mysfa.rest.ServiceGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nunu on 10/19/2016.
 */

public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private EditText username;
    private EditText password;
    private LinearLayout formLogin;
    private AlertDialog alertDialog;
    private SessionManager session;

    private ProgressDialog dialog;
    private Context ctx;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ctx = getBaseContext();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        this.realm = RealmController.with(this).getRealm();

        // refresh the realm instance
        RealmController.with(this).refresh();


        formLogin = (LinearLayout) findViewById(R.id.login_form);
        formLogin.setBackgroundColor(getColorWithAlpha(Color.BLACK, 0.2f));

        username = (EditText) findViewById(R.id.login_code);
        password = (EditText) findViewById(R.id.login_password);

        setupButton();
        session = new SessionManager(ctx);

    }

    public void setupButton() {
        btn_login 		= (Button) findViewById(R.id.btn_login);

        btn_login.setOnTouchListener(new OnSwipeTouchListener(this) {
            public boolean onTouch(View v, MotionEvent event) {
                super.onTouch(v, event);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        if (!username.getText().toString().equalsIgnoreCase("") && !password.getText().toString().equalsIgnoreCase("")) {
                            openDialog();
                            LoginService apiService = ServiceGenerator.createService(LoginService.class);

//                            // Fetch and print a list of the contributors to this library.
                            Call<LoginInfo> call = apiService.basicLogin(username.getText().toString(),password.getText().toString());
//
                            call.enqueue(new Callback<LoginInfo>() {
                                @Override
                                public void onResponse(Call<LoginInfo> call, Response<LoginInfo> response) {
                                    closeDialog();
                                    if(response.code() == 200) {
                                        session.createLoginSession(username.getText().toString(), "", response.body().getId(), response.body().getUserId());
                                        loadInitData();
                                        Intent intent = new Intent(LoginActivity.this, SplashScreenActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                                        TextView myMsg = new TextView(LoginActivity.this);
                                        myMsg.setText("Username dan password salah.");
                                        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                                        myMsg.setTextColor(Color.BLACK);
                                        myMsg.setPadding(15, 15, 15, 15);
                                        alertDialog.setView(myMsg);
                                        alertDialog.show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<LoginInfo> call, Throwable t) {
                                    // Log error here since request failed
                                    Log.e("sales", t.toString());
                                    closeDialog();
                                    alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                                    TextView myMsg = new TextView(LoginActivity.this);
                                    myMsg.setText("Terjadi kesalahan pada server. Silahkan ulangi beberapa saat lagi.");
                                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                                    myMsg.setTextColor(Color.BLACK);
                                    myMsg.setPadding(15, 15, 15, 15);
                                    alertDialog.setView(myMsg);
                                    alertDialog.show();
                                }
                            });
                        } else {
                            Toast.makeText(LoginActivity.this, "Please input code and password", Toast.LENGTH_LONG).show();
                        }
                        break;
                }
                return true;
            }
        });

    }

    private void loadInitData(){
//        if (session.getLoadCustomer() != 1) {

            JSONObject filterContract = new JSONObject();
            JSONObject where = new JSONObject();
            CustomerService apiService = ServiceGenerator.createService(CustomerService.class);
            try {
//                {"where":{"id_distributor":51, "id_salesman":14}, "include":{"relation":"customer", "where":{"name": {"like":"%SAMPLE%"}}}}
                where.put("id_distributor",session.getDistributor());
                Log.d("mysfa", "session : "+session.getDistributor());
                where.put("id_salesman",session.getID());
                filterContract.put("where",where);
                filterContract.put("include","customer");
                Log.d("mysfa", "filterContract : "+filterContract.toString());
                Call<List<ContractResponse>> call = apiService.getAllContract(session.getToken(), URLEncoder.encode(filterContract.toString(), "UTF-8"));
                call.enqueue(new Callback<List<ContractResponse>>() {
                    @Override
                    public void onResponse(Call<List<ContractResponse>> call, Response<List<ContractResponse>> response) {
                        if(response.code() == 200) {
                            session.updateLoadCustomer(1);
                            RealmController.with(LoginActivity.this).clearCustomer();
                            List<ContractResponse> cusRes = response.body();
                            for(ContractResponse contract : cusRes){
                                CustomerResponse cust = contract.getCustomer();
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
                    }

                    @Override
                    public void onFailure(Call<List<ContractResponse>> call, Throwable t) {
                        Log.e("sales", t.toString());
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        filterContract = new JSONObject();
        where = new JSONObject();
        CustomerService apiService2 = ServiceGenerator.createService(CustomerService.class);
        try {
            where.put("id_salesman",session.getID());
            where.put("status","PROSPECT");
            filterContract.put("where",where);
            Log.d("sfa", filterContract.toString());
            Call<List<CustomerResponse>> call = apiService.getAllCustomerProspect(session.getToken(), URLEncoder.encode(filterContract.toString(), "UTF-8"));
            call.enqueue(new Callback<List<CustomerResponse>>() {
                @Override
                public void onResponse(Call<List<CustomerResponse>> call, Response<List<CustomerResponse>> response) {
                    if(response.code() == 200) {
                        session.updateLoadCustomer(1);
                        RealmController.with(LoginActivity.this).clearCustomerProspect();
                        List<CustomerResponse> cusRes = response.body();
                        for(CustomerResponse cust : cusRes){
                            CustomerProspectRealm c = new CustomerProspectRealm();
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
                }

                @Override
                public void onFailure(Call<List<CustomerResponse>> call, Throwable t) {
                    Log.e("sales", t.toString());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        JSONObject filter = new JSONObject();
//        JSONObject where = new JSONObject();
//        try {
////            where.put("id_distributor",session.getDistributor());
////            filter.put("where",where);
//            filter.put("include","material");
//            SalesService apiService2 = ServiceGenerator.createService(SalesService.class);
//            Call<List<StockResponse>> call2 = apiService2.getAllStock(session.getToken(), URLEncoder.encode(filter.toString(), "UTF-8"));
//            call2.enqueue(new Callback<List<StockResponse>>() {
//                @Override
//                public void onResponse(Call<List<StockResponse>> call, Response<List<StockResponse>> response) {
//                    if(response.code() == 200) {
//    //                    session.updateLoadSto(1);
//                        RealmController.with(LoginActivity.this).clearStock();
//                        List<StockResponse> stockRes = response.body();
//                        for(StockResponse stock : stockRes){
//                            StockRealm c = new StockRealm();
//                            c.setId(stock.getId());
//                            c.setCode(stock.getMaterial().getCode());
//                            c.setName(stock.getMaterial().getName());
//                            c.setCreatedAt(stock.getCreatedAt());
//                            c.setUpdatedAt(stock.getUpdatedAt());
//                            c.setCreatedBy(stock.getCreatedBy());
//                            c.setModifiedBy(stock.getModifiedBy());
//                            c.setQty(stock.getQty());
//                            c.setStatus(stock.getStatus());
//                            c.setIdDistributor(stock.getIdDistributor());
//                            c.setIdMaterial(stock.getIdMaterial());
//                            Log.d("sfa","id_material : "+stock.getIdMaterial());
//                            c.setIdCustomer(stock.getIdCustomer());
//                            c.setIdSite(stock.getIdSite());
//
//                            c.setMaterial(stock.getMaterial().getMaterial());
//                            c.setMaterialDesc(stock.getMaterial().getMaterialDesc());
//                            c.setUom(stock.getMaterial().getUom());
//                            c.setGrossWeight(stock.getMaterial().getGrossWeight());
//                            c.setGrossWeightUom(stock.getMaterial().getGrossWeightUom());
//                            c.setIntensifYear(stock.getMaterial().getIntensifYear());
//                            c.setPackaging(stock.getMaterial().getPackaging());
//
////                            MaterialRealm d = new MaterialRealm();
////                            d.setId(stock.getMaterial().getId());
////                            d.setCode(stock.getMaterial().getCode());
////                            d.setName(stock.getMaterial().getName());
////                            d.setMaterial(stock.getMaterial().getMaterial());
////                            d.setMaterialDesc(stock.getMaterial().getMaterialDesc());
////                            d.setUom(stock.getMaterial().getUom());
////                            d.setGrossWeight(stock.getMaterial().getGrossWeight());
////                            d.setGrossWeightUom(stock.getMaterial().getGrossWeightUom());
////                            d.setIntensifYear(stock.getMaterial().getIntensifYear());
////                            d.setPackaging(stock.getMaterial().getPackaging());
////                            d.setCreatedAt(stock.getMaterial().getCreatedAt());
////                            d.setUpdatedAt(stock.getMaterial().getUpdatedAt());
////                            c.setMaterial(d);
//
//                            realm.beginTransaction();
//                            realm.copyToRealm(c);
//                            realm.commitTransaction();
//                        }
//
//                        Prefs.with(ctx).setPreLoad(true);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<StockResponse>> call, Throwable t) {
//                    Log.e("sales", t.toString());
//                }
//            });
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
////        }

    }
    public static int getColorWithAlpha(int color, float ratio) {
        int newColor = 0;
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        newColor = Color.argb(alpha, r, g, b);
        return newColor;
    }

    private void closeDialog(){
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void openDialog(){
        dialog = ProgressDialog.show(this,"Processing","Please wait...",false,false);
    }

    protected void onRestart() {
        super.onRestart();
        setupButton();
    }
}

