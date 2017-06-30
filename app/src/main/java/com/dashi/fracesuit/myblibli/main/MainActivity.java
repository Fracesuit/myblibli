package com.dashi.fracesuit.myblibli.main;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dashi.fracesuit.commonlibs.base.CommonActivity;
import com.dashi.fracesuit.myblibli.R;
import com.dashi.fracesuit.myblibli.hotfix.bottombar.BaseAdapter;
import com.dashi.fracesuit.myblibli.hotfix.bottombar.BottomBar;
import com.dashi.fracesuit.myblibli.hotfix.bottombar.BottomBarTab;
import com.dashi.fracesuit.myblibli.hotfix.bottombar.OnBottomTabSelectedListener;
import com.dashi.fracesuit.permissions.PermissionsHelp;
import com.dashi.fracesuit.usermodule.modules.user.UserActivity;

import butterknife.BindView;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainActivity extends CommonActivity<MainContract.MainView, MainPresenter> implements MainContract.MainView {
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
    protected void init() {

    }

    @Override
    protected void requestPermissions() {
        PermissionsHelp.with(this).requestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE);
        /*PermissionsHelp.with(this).requestPermissions(
                new OnRequestPermissionsListener() {
                    @Override
                    public void grant(Permission permission, boolean granted) {
                        if (granted) {
                            LogUtils.d("granted");
                        } else {
                            LogUtils.d("refuse");
                        }
                    }

                    @Override
                    public void completed() {
                        LogUtils.d("completed");
                    }

                    @Override
                    public void error(Throwable e) {
                        LogUtils.d("error");
                    }
                }
                , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE
               *//* , new PermissionModel(Manifest.permission.WRITE_EXTERNAL_STORAGE, "提示信息,存储权限", true)
                , new PermissionModel(Manifest.permission.READ_PHONE_STATE, "提示信息，电话权限", true)
                , new PermissionModel(Manifest.permission.ACCESS_FINE_LOCATION, "提示信息，位置权限", false)*//*
        );*/

    }


    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initToolBar() {
        toolbar.supportBack()
                .titleCenter("dd")
                .inflateMenu(R.menu.menu);
    }

    @Override
    protected void initListener() {
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);*/
                int i = 1 / 0;
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
