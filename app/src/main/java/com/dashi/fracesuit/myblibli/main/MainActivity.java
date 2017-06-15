package com.dashi.fracesuit.myblibli.main;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dashi.fracesuit.commonlibs.base.mvp.RxMvpBaseActivity;
import com.dashi.fracesuit.myblibli.R;
import com.dashi.fracesuit.myblibli.hotfix.bottombar.BaseAdapter;
import com.dashi.fracesuit.myblibli.hotfix.bottombar.BottomBar;
import com.dashi.fracesuit.myblibli.hotfix.bottombar.BottomBarTab;
import com.dashi.fracesuit.myblibli.hotfix.bottombar.OnBottomTabSelectedListener;
import com.dashi.fracesuit.usermodule.modules.user.UserActivity;

import butterknife.BindView;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainActivity extends RxMvpBaseActivity<MainContract.MainView, MainPresenter> implements MainContract.MainView {
    @BindView(R.id.go)
    TextView go;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    private BaseAdapter baseAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public String[] getPermissions() {
        return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);*/
                int i=1/0;
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });
        bottomBar.setOnTabSelectedListener(new OnBottomTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                toast("position111==" + position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

    }

    @Override
    protected void initData() {
        bottomBar
                .addItem(new BottomBarTab(this, R.mipmap.ic_launcher, "工作台"))
                .addItem(new BottomBarTab(this, R.mipmap.ic_launcher, "应用"))
                .addItem(new BottomBarTab(this, R.mipmap.ic_launcher, "系统设置"));
    }

}
