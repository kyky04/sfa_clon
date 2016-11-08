package com.pertaminalubricants.mysfa.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
//        mToolbar.setT(getResources().getColor(android.R.color.white));
        setSupportActionBar(mToolbar);

//        mActionBarHandler = getActionBarHandler();
//        if (mActionBarHandler == null) mCustomToolbar = new ToolbarDefault(this);
//        else mCustomToolbar = mActionBarHandler.build();

//        ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content))
//                .getChildAt(0);
//
//        rootView.addView(mCustomToolbar);

//        mCustomToolbar.getToolbar()
//                .setTitleTextColor(getResources().getColor(android.R.color.white));
//        setSupportActionBar(mCustomToolbar.getToolbar());
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//
//        // Custom Search Button
//        if (mActionBarHandler instanceof ActionBarSearchHandler) {
//            int customSearchButtonId = ((ActionBarSearchHandler) mActionBarHandler)
//                    .getCustomSearchButtonId();
//            if (customSearchButtonId != 0) mCustomSearchButton = findViewById(customSearchButtonId);
//            else mCustomSearchButton = ((ActionBarSearchHandler) mActionBarHandler)
//                    .getCustomSearchButton();
//            if (mCustomSearchButton != null) {
//                mCustomSearchButton.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        showActionBarSearch();
//                        hideActionBarShadow();
//                    }
//
//                });
//            }
//        }
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        if (mCustomToolbar instanceof ToolbarSearch && mCustomSearchButton == null) {
//            MenuItem searchItem = menu
//                    .add(0, R.id.mdl_toolbar_search_menu_item, Menu.NONE, "Search")
//                    .setIcon(R.drawable.ic_action_search);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//            }
//        }
//
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int itemId = item.getItemId();
//        if (itemId == R.id.mdl_toolbar_search_menu_item) {
//            showActionBarSearch();
//            hideActionBarShadow();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

//    @Override
//    public void onBackPressed() {
//        if (mCustomToolbar instanceof ToolbarSearch) {
//            ToolbarSearch toolbarSearch = ((ToolbarSearch) mCustomToolbar);
//            if (toolbarSearch.isSearchBarShown()) {
//                toolbarSearch.hideSearchBar();
//                showActionBarShadow();
//                return;
//            }
//        }
//        super.onBackPressed();
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == ToolbarSearch.SEARCH_REQUEST_CODE) {
//            ((ToolbarSearch) mCustomToolbar).onActivityResult(requestCode, resultCode, data);
//        }
//    }

//    public android.support.v7.widget.Toolbar getMaterialDesignActionBar() {
//        return mCustomToolbar.getToolbar();
//    }

    public View getActionBarShadowView() {
        return mShadowView;
    }


//    public void showActionBarSearch() {
//        if (mCustomToolbar instanceof ToolbarSearch) {
//            ((ToolbarSearch) mCustomToolbar).showSearchBar();
//        }
//    }

//    public void hideActionBarSearch() {
//        if (mCustomToolbar instanceof ToolbarSearch) {
//            ((ToolbarSearch) mCustomToolbar).hideSearchBar();
//        }
//    }

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
