package com.pertaminalubricants.mysfa.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pertaminalubricants.mysfa.R;
import com.pertaminalubricants.mysfa.adapter.OrderItemListAdapter;
import com.pertaminalubricants.mysfa.adapter.StockAutoCompleteAdapter;
import com.pertaminalubricants.mysfa.library.Prefs;
import com.pertaminalubricants.mysfa.library.SessionManager;
import com.pertaminalubricants.mysfa.model.CustomerRealm;
import com.pertaminalubricants.mysfa.model.OrderDetailTmp;
import com.pertaminalubricants.mysfa.model.Ordertmp;
import com.pertaminalubricants.mysfa.model.StockRealm;
import com.pertaminalubricants.mysfa.model.StockResponse;
import com.pertaminalubricants.mysfa.realm.RealmController;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by nunu on 10/31/2016.
 */

public class InputOrderItemActivity extends AppCompatActivity {

    private Realm realm;
    private String idDetail;
    private OrderDetailTmp detail;
    private EditText etItem,etItemId, etItemCd, etItemNm, etItemMaterial, etItemGWU, etItemSecondary, etPrice, etQty, etQtyAvailable;
    private AutoCompleteTextView acStock;
    private TextView tvSubTotal;
    private Context ctx;
    private View mView;
    private SessionManager session;
    private Button btCancel, btSave;
    private DecimalFormat df = new DecimalFormat("###,###.##");

    public InputOrderItemActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_order_item);

        ctx = getBaseContext();
        session = new SessionManager(ctx);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        this.realm = RealmController.with(this).getRealm();

        if (!Prefs.with(this).getPreLoad()) {

        }

        RealmController.with(this).refresh();

        etItemId = (EditText) findViewById(R.id.et_order_item_id);
        etItemCd = (EditText) findViewById(R.id.et_order_item_cd);
        etItemNm = (EditText) findViewById(R.id.et_order_item_nm);
        etItemMaterial = (EditText) findViewById(R.id.et_order_item_material);
        etItemGWU = (EditText) findViewById(R.id.et_order_item_gwu);
        etItemSecondary = (EditText) findViewById(R.id.et_order_item_secondary);
//        etItem = (EditText) findViewById(R.id.et_order_item);
//        etItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent();
//                i = new Intent(ctx, ChooseStockActivity.class);
//                startActivityForResult(i, 200);
//            }
//        });

        etPrice = (EditText) findViewById(R.id.et_order_item_price);
        etQty = (EditText) findViewById(R.id.et_order_item_qty);
        etQtyAvailable = (EditText) findViewById(R.id.et_order_item_stock);

        tvSubTotal = (TextView) findViewById(R.id.tv_sub_total);
        tvSubTotal.setText("Rp. 0");

//        RealmResults<StockRealm> result2 = RealmController.with(this).getAllStock();
        acStock = (AutoCompleteTextView)findViewById(R.id.autoComplete);
        acStock.setThreshold(1);

//        acStock.setAdapter(new StockAutoCompleteAdapter(getBaseContext(), session));
        StockAutoCompleteAdapter adapter = new StockAutoCompleteAdapter(this, R.layout.activity_input_order_item, android.R.id.text1, new ArrayList<StockResponse>(), session);
        acStock.setAdapter(adapter);

        if (acStock != null) {
            if (acStock.getAdapter() != null) {
                acStock.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView,
                                            View view, int position, long id) {
                        StockResponse tmp = (StockResponse) adapterView.getItemAtPosition(position);
                        etItemId.setText(String.valueOf(tmp.getMaterial().getId()));
                        etItemCd.setText(tmp.getMaterial().getCode());
                        etItemNm.setText(tmp.getMaterial().getName());
                        etItemMaterial.setText(String.valueOf(tmp.getIdMaterial()));
                        etItemGWU.setText(String.valueOf(tmp.getMaterial().getGrossWeightUom()));
                        etItemSecondary.setText("0"); //di hardcode dulu
                        etQtyAvailable.setText(String.valueOf(tmp.getQty()));
                        acStock.setText(tmp.getMaterial().getCode()+" - "+tmp.getMaterial().getName());
                    }
                });
            }
        }

        etPrice.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence cs, int s, int b, int c) {

            }
            public void afterTextChanged(Editable editable) {
                try {
                    Double price = Double.parseDouble(etPrice.getText().toString());
                    int qty = Integer.parseInt(etQty.getText().toString());

                    if (price != 0 && qty != 0)
                        tvSubTotal.setText("Rp. "+df.format(price * qty).toString().replace(",", "."));
                } catch (NumberFormatException e){
                    tvSubTotal.setText("Rp. "+df.format(0).toString().replace(",", "."));
                }
            }

            public void beforeTextChanged(CharSequence cs, int i, int j, int k) { }
        });

        etQty.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence cs, int s, int b, int c) {

            }
            public void afterTextChanged(Editable editable) {
                try {
                    Double price = Double.parseDouble(etPrice.getText().toString());
                    int qty = Integer.parseInt(etQty.getText().toString());

                    if(price != 0 && qty != 0) tvSubTotal.setText("Rp. "+df.format(price*qty).toString().replace(",", "."));
                } catch (NumberFormatException e){
                    tvSubTotal.setText("Rp. "+df.format(0).toString().replace(",", "."));
                }
            }

            public void beforeTextChanged(CharSequence cs, int i, int j, int k) { }
        });

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
                Intent i = new Intent(InputOrderItemActivity.this, InputOrderInOutActivity.class);
                finish();
            }
        });

        initData();
    }

    public void initData(){

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idDetail = extras.getString("id");
            detail = RealmController.with(this).getOrderDetailTmp(idDetail);
            if(detail != null){
                etPrice.setText(df.format(detail.getPrice()).toString().replace(",", ""));
                etQty.setText(String.valueOf(detail.getQty()));
                tvSubTotal.setText("Rp. "+df.format(detail.getTotal()).toString().replace(",", "."));

                etItemId.setText(detail.getProductId());
                etItemCd.setText(detail.getProductCode());
                etItemNm.setText(detail.getProductName());
                etItemMaterial.setText(detail.getProductMaterial());
                etItemGWU.setText(detail.getProductGrossWeightUom());
                etItemSecondary.setText(String.valueOf(detail.getProductSupplyBySecondary()));
                acStock.setText(detail.getProductCode()+"-"+detail.getProductName());
            }
        }
    }



    public boolean validation(){
        if (acStock.getText().toString().length() == 0){
            Snackbar.make(mView,"Item is required", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (etItemId.getText().toString().length() == 0){
            Snackbar.make(mView,"Item is required. Please Select Item", Snackbar.LENGTH_SHORT).show();
            return false;
        }else if (etQty.getText().toString().length() == 0){
            Snackbar.make(mView,"Quantity is required", Snackbar.LENGTH_SHORT).show();
            return false;
        }else if (etPrice.getText().toString().length() == 0){
            Snackbar.make(mView,"Price is required", Snackbar.LENGTH_SHORT).show();
            return false;
        }else if (Integer.parseInt(etQty.getText().toString()) > Integer.parseInt(etQtyAvailable.getText().toString())){
            Snackbar.make(mView,"Max allowed quantity is : " + etQtyAvailable.getText().toString(), Snackbar.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void save(){
        if(validation()) {

            if(detail == null){
                detail = new OrderDetailTmp();
                detail.setId(UUID.randomUUID().toString());
            }

            if(!realm.isInTransaction()){
                realm.beginTransaction();
            }
            detail.setQty(Integer.parseInt(etQty.getText().toString()));
            detail.setProductName(etItemNm.getText().toString());
            detail.setProductId(etItemId.getText().toString());
            detail.setProductMaterial(etItemMaterial.getText().toString());
            detail.setProductCode(etItemCd.getText().toString());
            detail.setProductGrossWeightUom(etItemGWU.getText().toString());
            detail.setProductSupplyBySecondary(Integer.parseInt(etItemSecondary.getText().toString()));
            detail.setPrice(Long.parseLong(etPrice.getText().toString()));
            detail.setTotal(detail.getPrice() * detail.getQty());
            realm.copyToRealmOrUpdate(detail);
            realm.commitTransaction();
            Intent i = new Intent();
            i = new Intent(InputOrderItemActivity.this, InputOrderInOutActivity.class);
            setResult(RESULT_OK, i);
            i.putExtra("save", true);
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null && data.getExtras().containsKey("value")) {
            etItem.setText(data.getStringExtra("code")+"-"+data.getStringExtra("value"));
            etItemId.setText(data.getStringExtra("id"));
            etItemCd.setText(data.getStringExtra("code"));
            etItemNm.setText(data.getStringExtra("value"));
            etItemMaterial.setText(data.getStringExtra("id_material"));
            etQtyAvailable.setText(data.getStringExtra("stock"));
        }
    }

}
