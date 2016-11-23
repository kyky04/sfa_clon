package com.pertaminalubricants.mysfa.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.pertaminalubricants.mysfa.R;

public abstract class StandarActivity extends AppCompatActivity {

    private final static String TOOLBAR_SEARCH_CONSTRAINT_KEY = "ToolbarSearchConstraint";
    private final static String TOOLBAR_SEARCH_IS_SHOWN = "ToolbarSearchIsShown";

    private Toolbar mToolbar;
    private View mShadowView;
    private View mCustomSearchButton;
    private ActionBarHandler mActionBarHandler;

    public void onCreate(Bundle savedInstanceState, int contentView) {
        super.onCreate(savedInstanceState);

        setContentView(contentView);

        // Toolbar Shadow View
        mShadowView = findViewById(R.id.toolbar_shadow);

        if (enableActionBarShadow()) showActionBarShadow();

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_empty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public View getActionBarShadowView() {
        return mShadowView;
    }

    public void showActionBarShadow() {
        if (mShadowView != null) mShadowView.setVisibility(View.VISIBLE);
    }

    public void hideActionBarShadow() {
        if (mShadowView != null) mShadowView.setVisibility(View.INVISIBLE);
    }

    protected abstract boolean enableActionBarShadow();

    protected abstract ActionBarHandler getActionBarHandler();

    protected abstract int getContentView();
}
