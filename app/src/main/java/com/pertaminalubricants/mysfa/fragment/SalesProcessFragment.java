package com.pertaminalubricants.mysfa.fragment;

import com.blunderer.materialdesignlibrary.handlers.ViewPagerHandler;
import com.pertaminalubricants.mysfa.R;

public class SalesProcessFragment extends com.blunderer.materialdesignlibrary.fragments.ViewPagerWithTabsFragment {

    @Override
    public ViewPagerHandler getViewPagerHandler() {
        return new ViewPagerHandler(getActivity())
                .addPage(R.string.title_order,
                        new OrderFragment())
                .addPage(R.string.title_invoice,
                        new InvoiceFragment())
                ;
    }

    @Override
    public boolean expandTabs() {
        return false;
    }

    @Override
    public int defaultViewPagerPageSelectedPosition() {
        return 0;
    }
}
