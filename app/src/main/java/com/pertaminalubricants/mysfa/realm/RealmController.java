package com.pertaminalubricants.mysfa.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.pertaminalubricants.mysfa.model.CustomerRealm;
import com.pertaminalubricants.mysfa.model.OrderDetailTmp;
import com.pertaminalubricants.mysfa.model.Ordertmp;
import com.pertaminalubricants.mysfa.model.StockRealm;

import io.realm.Realm;
import io.realm.RealmResults;


public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from Ordertmp.class
    public void clearAll() {

        realm.beginTransaction();
        realm.clear(Ordertmp.class);
        realm.clear(OrderDetailTmp.class);
        realm.commitTransaction();
    }

    public void clearCustomer() {

        realm.beginTransaction();
        realm.clear(CustomerRealm.class);
        realm.commitTransaction();
    }

    public void clearStock() {

        realm.beginTransaction();
        realm.clear(StockRealm.class);
        realm.commitTransaction();
    }

    //find all objects in the OrderDetailTmp.class
    public RealmResults<OrderDetailTmp> getOrderDetailTmps() {

        return realm.where(OrderDetailTmp.class).findAll();
    }

    //query a single item with the given id
    public OrderDetailTmp getOrderDetailTmp(String id) {

        return realm.where(OrderDetailTmp.class).equalTo("id", id).findFirst();
    }

    public Ordertmp getOrderTmp() {

        return realm.where(Ordertmp.class).findFirst();
    }

    //check if OrderDetailTmp.class is empty
    public boolean hasOrderDetailTmp() {

        return !realm.allObjects(OrderDetailTmp.class).isEmpty();
    }

    //query example
    public RealmResults<OrderDetailTmp> queryedOrderDetailTmp() {

        return realm.where(OrderDetailTmp.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }


    public RealmResults<CustomerRealm> getAllCustomer() {

        return realm.where(CustomerRealm.class).findAll();
    }


    public RealmResults<StockRealm> getAllStock() {

        return realm.where(StockRealm.class).findAll();
    }
}