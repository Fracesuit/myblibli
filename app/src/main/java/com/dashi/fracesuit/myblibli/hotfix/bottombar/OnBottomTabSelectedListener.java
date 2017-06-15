package com.dashi.fracesuit.myblibli.hotfix.bottombar;

/**
 * Created by Fracesuit on 2017/6/9.
 */

public interface OnBottomTabSelectedListener {
    void onTabSelected(int position, int prePosition);

    void onTabUnselected(int position);

    void onTabReselected(int position);
}
