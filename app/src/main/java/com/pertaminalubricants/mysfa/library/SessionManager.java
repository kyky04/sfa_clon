package com.pertaminalubricants.mysfa.library;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.pertaminalubricants.mysfa.activity.MainActivity;

import java.util.HashMap;

/**
 * Created by Nugraha on 11/12/2015.
 */

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "mySFAPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User ID (make variable public to access from outside)
    public static final String KEY_ID = "id";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // Token
    public static final String KEY_TOKEN = "token";

    // Token
//    public static final String KEY_CODE = "code";
//
    public static final String KEY_LOAD_SALES = "load_sales";
    public static final String KEY_LOAD_SALES_ITEM = "load_sales_item";
    public static final String KEY_LOAD_CUSTOMER = "load_customer";
    public static final String KEY_LOAD_DISTRIBUTOR = "load_distributor";
//    public static final String KEY_PHONE = "phone";
//    public static final String KEY_BALANCE = "balance";
//    public static final String KEY_PHOTO = "photo";
//    public static final String KEY_LOAD_ORDER_TICKET = "load_order_ticket";
//    public static final String KEY_LOAD_ORDER_HOTEL = "load_order_hotel";
//    public static final String KEY_LOAD_ORDER_PPOB = "load_order_ppob";

    public static final String KEY_LEVEL = "level";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_STATUS = "status";
    public static final String KEY_REGION = "region";
    public static final String KEY_DISTRIBUTOR = "distributor";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String userName, String email, String token, String id){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_USERNAME, userName);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // Storing email in pref
        editor.putString(KEY_TOKEN, token);

        editor.putString(KEY_ID, id);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        user.put(KEY_LEVEL, pref.getString(KEY_LEVEL, null));
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        user.put(KEY_STATUS, pref.getString(KEY_STATUS, null));
        user.put(KEY_REGION, pref.getString(KEY_REGION, null));
        user.put(KEY_DISTRIBUTOR, pref.getString(KEY_DISTRIBUTOR, null));
        user.put(KEY_ID, pref.getString(KEY_ID, null));

        return user;
    }

    public String getFullname() {
        return pref.getString(KEY_NAME, null);
    }

    public void setFullName(String v) {
        editor.putString(KEY_NAME, v);
        editor.commit();
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, null);
    }

    public void setEmail(String v) {
        editor.putString(KEY_EMAIL, v);
        editor.commit();
    }

    public String getToken() {
        return pref.getString(KEY_TOKEN, "");
    }

    public String getLevel() {
        return pref.getString(KEY_LEVEL, "");
    }

    public void setLevel(String v) {
        editor.putString(KEY_LEVEL, v);
        editor.commit();
    }

    public String getStatus() {
        return pref.getString(KEY_STATUS, "");
    }

    public void setStatus(String v) {
        editor.putString(KEY_STATUS, v);
        editor.commit();
    }

    public String getRegion() {
        return pref.getString(KEY_REGION, "");
    }

    public void setRegion(String v) {
        editor.putString(KEY_REGION, v);
        editor.commit();
    }

    public String getDistributor() {
        return pref.getString(KEY_DISTRIBUTOR, "");
    }

    public void setDistributor(String v) {
        editor.putString(KEY_DISTRIBUTOR, v);
        editor.commit();
    }

    public String getUserName() {
        return pref.getString(KEY_USERNAME, null);
    }

    public String getID() {
        return pref.getString(KEY_ID, "0");
    }

    public int getIntID() {
        return Integer.parseInt(pref.getString(KEY_ID, "0"));
    }
    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnected();
    }


    /**
     * Clear session details
     * */
    public void logoutUser(){
        editor.clear();
        editor.commit();
    }

    public void updateLoadCustomer(int value) {
        editor.putInt(KEY_LOAD_CUSTOMER, value);
        editor.commit();
    }

    public int getLoadCustomer() {
        return pref.getInt(KEY_LOAD_CUSTOMER, 1);
    }

    public void updateLoadDistributor(int value) {
        editor.putInt(KEY_LOAD_DISTRIBUTOR, value);
        editor.commit();
    }

    public int getLoadDistributor() {
        return pref.getInt(KEY_LOAD_DISTRIBUTOR, 1);
    }
}

