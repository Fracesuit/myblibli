package com.dashi.fracesuit.usermodule.modules.trouble;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dashi.fracesuit.usermodule.R;
import com.dashi.fracesuit.usermodule.modules.baselist.BaseListActivity;


public class TroubleActivity extends BaseListActivity<Trouble, TroublePresenter> {
    @Override
    protected BaseQuickAdapter getAdapter() {
        return new TrouibleAdapter(R.layout.item_trouble);
    }

    @Override
    protected int getActivityTitle() {
        return R.string.title_trouble;
    }
}
