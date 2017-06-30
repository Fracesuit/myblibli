package com.dashi.fracesuit.toolbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

public class AwesomeToolbar extends Toolbar {
    private AppCompatActivity appCompatActivity;
    Context context;

    public AwesomeToolbar(Context context) {
        super(context);
        init(context);
    }

    public AwesomeToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AwesomeToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        this.context = context;
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public AwesomeToolbar with(@NonNull AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
        //不用支持actionbar
        ActionBar supportActionBar = appCompatActivity.getSupportActionBar();
        if (supportActionBar != null) {
            appCompatActivity.setSupportActionBar(null);
        }
        return this;
    }

    public AwesomeToolbar supportBack() {
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(this.getContext(), null, R.styleable.ActionBar, R.attr.actionBarStyle, 0);
        Drawable mDefaultNavigationIcon = a.getDrawable(R.styleable.ActionBar_homeAsUpIndicator);
        setNavigationIcon(mDefaultNavigationIcon);
        setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                appCompatActivity.finish();
            }
        });
        return this;
    }

    public AwesomeToolbar supportNavigation(@NonNull DrawerLayout drawerLayout) {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(appCompatActivity,
                drawerLayout,
                this,
                android.R.string.ok,
                android.R.string.cancel) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        drawerLayout.addDrawerListener(mDrawerToggle);
        return this;
    }


    public AwesomeToolbar titleLeft(@StringRes int titleResid) {
        setTitle(context.getString(titleResid));
        //这个因为如果有导航按钮的时候,会出现title和naviation的距离太大
      /* Drawable navigationIcon = getNavigationIcon();
        if (navigationIcon != null) {

        }*/
        setContentInsetStartWithNavigation(0);
        setContentInsetsRelative(0, 0);
        setContentInsetsAbsolute(0, 0);
        return this;
    }

    public AwesomeToolbar titleCenter(@StringRes int titleResid) {
        titleLeft(titleResid);
        final AppCompatTextView textView = getTitleView();
        textView.setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
        layoutParams.width = LayoutParams.MATCH_PARENT;
        layoutParams.height = LayoutParams.MATCH_PARENT;
        textView.setLayoutParams(layoutParams);
        return this;
    }

    @Nullable
    private AppCompatTextView getTitleView() {
        int childCount = this.getChildCount();
        AppCompatTextView textView = null;
        for (int i = 0; i < childCount; i++) {
            View view = this.getChildAt(i);
            if (view instanceof AppCompatTextView) {
                textView = (AppCompatTextView) view;
                break;
            }
        }
        return textView;
    }

}
