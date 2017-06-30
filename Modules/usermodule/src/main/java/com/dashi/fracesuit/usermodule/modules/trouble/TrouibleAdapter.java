package com.dashi.fracesuit.usermodule.modules.trouble;

import android.support.annotation.LayoutRes;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dashi.fracesuit.usermodule.R;

/**
 * Created by zhiren.zhang on 2017/6/30.
 */

public class TrouibleAdapter extends BaseQuickAdapter<Trouble, BaseViewHolder> {

    public TrouibleAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Trouble item) {
        TextView view = helper.getView(R.id.textview);
        view.setText(item.getRWN()+"");
    }
}
