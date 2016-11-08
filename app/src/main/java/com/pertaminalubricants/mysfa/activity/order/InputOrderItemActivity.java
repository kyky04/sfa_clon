package com.pertaminalubricants.mysfa.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.pertaminalubricants.mysfa.model.CustomerRealm;
import com.pertaminalubricants.mysfa.model.OrderDetailTmp;
import com.pertaminalubricants.mysfa.model.Ordertmp;
import com.pertaminalubricants.mysfa.model.StockRealm;
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

    private ListView list_view;
    private ListAdapter orderAdapter;
    private Ordertmp order;
    private Realm realm;
    private List<OrderDetailTmp> listOrderDetails = new ArrayList<OrderDetailTmp>();
    private FloatingActionButton fabNewOrder;
    private int idOrder = 0;
    private EditText etItem,etItemId, etItemCd, etItemNm, etItemMaterial, etPrice, etQty, etQtyAvailable;
    private AutoCompleteTextView acStock;
    private TextView tvSubTotal;
    private Context ctx;
    private View mView;
    private View forsnack;
    private Button btCancel, btSave;

    public InputOrderItemActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_order_item);

        ctx = getBaseContext();
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

        if (!Prefs.with(this).getPreLoad()) {
            setData();
        }

        // refresh the realm instance
        RealmController.with(this).refresh();
        // get all persisted objects
        // create the helper adapter and notify data set changes
        // changes will be reflected automatically

        etItemId = (EditText) findViewById(R.id.et_order_item_id);
        etItemCd = (EditText) findViewById(R.id.et_order_item_cd);
        etItemNm = (EditText) findViewById(R.id.et_order_item_nm);
        etItemMaterial = (EditText) findViewById(R.id.et_order_item_material);
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

        RealmResults<StockRealm> result2 = RealmController.with(this).getAllStock();
        acStock = (AutoCompleteTextView)findViewById(R.id.autoComplete);
        acStock.setAdapter(new StockAutoCompleteAdapter(getBaseContext(), result2));
        if (acStock != null) {
            if (acStock.getAdapter() != null) {
//                acCustomer.setAdapter(adapter);
                acStock.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView,
                                            View view, int position, long id) {
                        StockRealm tmp = (StockRealm) adapterView.getItemAtPosition(position);
                        etItemId.setText(String.valueOf(tmp.getId()));
                        etItemCd.setText(tmp.getCode());
                        etItemNm.setText(tmp.getName());
                        acStock.setText(tmp.getCode()+" - "+tmp.getName());
                    }

                });
            }
        }

        etPrice.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence cs, int s, int b, int c) {

            }
            public void afterTextChanged(Editable editable) {
                DecimalFormat df = new DecimalFormat("###,###.##");
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
                DecimalFormat df = new DecimalFormat("###,###.##");
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

//                if (cekValid()){
//                    doChangePassword();
//                }
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InputOrderItemActivity.this, InputOrderInOutActivity.class);
                finish();
            }
        });
//        fabNewOrder = (FloatingActionButton) findViewById(R.id.button_save_item);
//        fabNewOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                save();
//
////                if (cekValid()){
////                    doChangePassword();
////                }
//            }
//        });

//        forsnack = getContentView();

    }

    public void setData(){

//        Ordertmp orderTmp = new Ordertmp();
//        orderTmp.setId(UUID.randomUUID().toString());
//        orderTmp.setCustId("1");
//        orderTmp.setTax("4");
//        orderTmp.setDate(new Date());
//
//        ArrayList<OrderDetailTmp> listOrderDetail = new ArrayList<>();
//
//        OrderDetailTmp detail = new OrderDetailTmp();
//        detail.setId(UUID.randomUUID().toString());
//        detail.setOrderId(orderTmp.getId());
//        detail.setProduct("Aqua");
//        detail.setPrice(2000.00);
//        detail.setQty(2);
//        detail.setTotal(4000.00);
//        listOrderDetail.add(detail);
//
//        detail = new OrderDetailTmp();
//        detail.setId(UUID.randomUUID().toString());
//        detail.setOrderId(orderTmp.getId());
//        detail.setProduct("Sari Roti");
//        detail.setPrice(5000.00);
//        detail.setQty(5);
//        detail.setTotal(25000.00);
//        listOrderDetail.add(detail);
//
//        for (OrderDetailTmp b : listOrderDetail) {
//            // Persist your data easily
//            realm.beginTransaction();
//            realm.copyToRealm(b);
//            realm.commitTransaction();
//        }
//
//        Prefs.with(this).setPreLoad(true);
    }



    public boolean validation(){
//        if (etItem.getText().toString().length() == 0){
//            Snackbar.make(mView,"Item is required", Snackbar.LENGTH_SHORT).show();
//            return false;
//        }
        if (etItemId.getText().toString().length() == 0){
            Snackbar.make(mView,"Item is required. Please Select Item", Snackbar.LENGTH_SHORT).show();
            return false;
        }else if (etQty.getText().toString().length() == 0){
            Snackbar.make(mView,"Quantity is required", Snackbar.LENGTH_SHORT).show();
            return false;
        }else if (etPrice.getText().toString().length() == 0){
            Snackbar.make(mView,"Price is required", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void save(){
        if(validation()) {
            OrderDetailTmp tmp = new OrderDetailTmp();
            tmp.setId(UUID.randomUUID().toString());
            tmp.setQty(Integer.parseInt(etQty.getText().toString()));
            tmp.setProductName(etItemNm.getText().toString());
            tmp.setProductId(etItemId.getText().toString());
            tmp.setProductMaterial(etItemMaterial.getText().toString());
            tmp.setProductCode(etItemCd.getText().toString());
            tmp.setPrice(Double.parseDouble(etPrice.getText().toString()));
            tmp.setTotal(tmp.getPrice() * tmp.getQty());
            realm.beginTransaction();
            realm.copyToRealm(tmp);
            realm.commitTransaction();
            Intent i = new Intent();
            i = new Intent(InputOrderItemActivity.this, InputOrderInOutActivity.class);
            setResult(RESULT_OK, i);
            i.putExtra("save", true);
            finish();
        }
    }

    public ListAdapter getListAdapter() {
        order = RealmController.with(this).getOrderTmp();
        RealmResults<OrderDetailTmp> listCustomer = RealmController.with(this).getOrderDetailTmps();
        return new OrderItemListAdapter(this, listCustomer);

//        return new OrderInOutListAdapter(this.getActivity(), new ArrayList<OrderInOut>());
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
