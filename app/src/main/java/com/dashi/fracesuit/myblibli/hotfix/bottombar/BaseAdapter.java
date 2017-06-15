package com.dashi.fracesuit.myblibli.hotfix.bottombar;

/**
 * Created by Fracesuit on 2017/6/10.
 */

public class BaseAdapter {



    public interface OnItemClick {
        void onclick();
    }

    private OnItemClick onItemClick;

    public OnItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void click()
    {
        onItemClick.onclick();;
    }
}
