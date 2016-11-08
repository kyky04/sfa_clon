package com.pertaminalubricants.mysfa.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.blunderer.materialdesignlibrary.handlers.ActionBarDefaultHandler;
import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.blunderer.materialdesignlibrary.handlers.NavigationDrawerAccountsHandler;
import com.blunderer.materialdesignlibrary.handlers.NavigationDrawerAccountsMenuHandler;
import com.blunderer.materialdesignlibrary.handlers.NavigationDrawerBottomHandler;
import com.blunderer.materialdesignlibrary.handlers.NavigationDrawerStyleHandler;
import com.blunderer.materialdesignlibrary.handlers.NavigationDrawerTopHandler;
import com.blunderer.materialdesignlibrary.models.Account;
import com.pertaminalubricants.mysfa.R;
import com.pertaminalubricants.mysfa.activity.LoginActivity;
import com.pertaminalubricants.mysfa.fragment.BlankFragment;
import com.pertaminalubricants.mysfa.fragment.HomeFragment;
import com.pertaminalubricants.mysfa.fragment.ListViewCustomerFragment;
import com.pertaminalubricants.mysfa.fragment.ListViewTransactionFragment;
import com.pertaminalubricants.mysfa.fragment.ProfileFragment;
import com.pertaminalubricants.mysfa.fragment.SalesProcessFragment;
import com.pertaminalubricants.mysfa.library.SessionManager;
//import com.pertaminalubricants.mysfa.viewpagers.ViewPagerActivity;

import java.util.ArrayList;
import java.util.List;

//public class MainActivity extends com.blunderer.materialdesignlibrary.activities.ListViewActivity {


public class MainActivity extends com.blunderer.materialdesignlibrary.activities.NavigationDrawerActivity{

    private ProgressDialog dialog;
    private Context ctx;
    private SessionManager session;

    @Override
    public NavigationDrawerAccountsHandler getNavigationDrawerAccountsHandler() {
//        return new NavigationDrawerAccountsHandler(this)
//                .addAccount("Blunderer", "blundererandroid@gmail.com",
//                        R.drawable.profile1, R.drawable.profile1_background)
//                .addAccount("Blunderer's cat", "cat@gmail.com",
//                        R.drawable.profile2, R.drawable.profile2_background)
//                .addAccount("Blunderer's dog", "dog@gmail.com",
//                        R.drawable.profile3, R.color.cyan)
//                .addAccount("Blunderer's monkey", "monkey@gmail.com",
//                        R.drawable.profile4, R.color.gray);
        session = new SessionManager(getBaseContext());

        return new NavigationDrawerAccountsHandler(this)
                .addAccount(session.getUserName(), session.getEmail(),
                        R.drawable.profile5, R.drawable.profile1_background);
    }

    @Override
    public NavigationDrawerAccountsMenuHandler getNavigationDrawerAccountsMenuHandler() {
//        return new NavigationDrawerAccountsMenuHandler(this)
//                .addAddAccount(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                    }
//
//                })
//                .addManageAccounts(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                    }
//
//                });
        return new NavigationDrawerAccountsMenuHandler(this);
    }

    @Override
    public void onNavigationDrawerAccountChange(Account account) {
    }

    @Override
    public NavigationDrawerTopHandler getNavigationDrawerTopHandler() {
//        return new NavigationDrawerTopHandler(this)
//                .addSection(R.string.fragment)
//                .addItem(R.string.fragment_listview, new ListViewFragment())
//                .addItem(R.string.fragment_scrollview, new ScrollViewFragment())
//                .addItem(R.string.fragment_viewpager, new ViewPagerFragment())
//                .addItem(R.string.fragment_viewpager_with_tabs, new ViewPagerWithTabsFragment())
//                .addSection(R.string.activity)
//                .addItem(R.string.start_activity,
//                        new Intent(getApplicationContext(), ViewPagerActivity.class));
//        return new NavigationDrawerTopHandler(this)
//                .addItem("Dashboard", R.drawable.ic_menu_dashboard, new ListViewFragment())
//                .addItem("Input Data Penjualan", R.drawable.ic_menu_booking, new ScrollViewFragment())
//                .addItem("Input Pelanggan Baru", R.drawable.ic_menu_affiliate, new ViewPagerFragment())
//                .addItem("Visit", R.drawable.ic_menu_booking, new ViewPagerFragment());


        return new NavigationDrawerTopHandler(this)
                .addItem("Home", new HomeFragment())
                .addItem("Dashboard", new BlankFragment())
                .addItem("Sales Process", new SalesProcessFragment())
                .addItem("Customer", new ListViewCustomerFragment())
                .addItem("Follow Up", new ListViewTransactionFragment())
                .addItem("Profile", new ProfileFragment())
                ;
    }

    @Override
    public NavigationDrawerBottomHandler getNavigationDrawerBottomHandler() {
//        return new NavigationDrawerBottomHandler(this)
//                .addSettings(null)
//                .addHelpAndFeedback(null);
        return new NavigationDrawerBottomHandler(this)
                .addItem("Logout", R.drawable.ic_menu_signout, new MyClickListener("Signout"))
                ;
    }

    @Override
    public boolean overlayActionBar() {
        return true;
    }

    @Override
    public boolean replaceActionBarTitleByNavigationDrawerItemTitle() {
        return true;
    }

    @Override
    public int defaultNavigationDrawerItemSelectedPosition() {
        return 0;
    }

    @Override
    protected ActionBarHandler getActionBarHandler() {
        return new ActionBarDefaultHandler(this);
    }

    @Override
    protected boolean enableActionBarShadow(){
        return true;
    }

    @Override
    public NavigationDrawerStyleHandler getNavigationDrawerStyleHandler(){
        return new NavigationDrawerStyleHandler();
    }

    class MyClickListener implements View.OnClickListener {

        String message = "";

        public MyClickListener(String msg) {
            message = msg;
        }
        @Override
        public void onClick(View v) {
            Intent i;
            switch (message) {
//                case "Setting":
//                    i = new Intent(ctx, SettingActivity.class);
//                    startActivity(i);
//                    ctx.toggle();
//                    break;
                case "Signout":
                    i = new Intent(MainActivity.this, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    SessionManager session = new SessionManager(MainActivity.this);
                    session.logoutUser();
//                    MainActivity.this.toggle();
                    MainActivity.this.finish();
                    break;
                default:
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }


    private void closeDialog(){
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void openDialog(){
        dialog = new ProgressDialog(ctx);
    }

//    @Override
//    protected boolean enableActionBarShadow(){
//        return true;
//    }
}
