package com.dashi.fracesuit.usermodule.modules.baselist;


import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dashi.fracesuit.commonlibs.base.BaseActivity;
import com.dashi.fracesuit.logger.LogUtils;
import com.dashi.fracesuit.rxjava1x.interator.BaseView;
import com.dashi.fracesuit.usermodule.R;
import com.dashi.fracesuit.usermodule.R2;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public abstract class BaseListActivity<T, P extends BaseListPresenter<T>> extends BaseActivity<BaseView, P> implements BaseQuickAdapter.RequestLoadMoreListener {
    //请求
    private static final int REQUEST_STATE_REFRESH = 0;//下拉刷新
    private static final int REQUEST_STATE_LOADMORE = 1;//上拉加载更多

    //返回
    private static final int RESPONSE_STATE_START = 10;//开始状态
    private static final int RESPONSE_STATE_ERROR = 11;//错误状态
    private static final int RESPONSE_STATE_CANCEL = 12;//取消状态
    private static final int RESPONSE_STATE_Completed = 13;//完成状态
    private static final int RESPONSE_STATE_PARSEING = 14;//解析数据


    //布局
    @BindView(R2.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R2.id.swiperefreshlayout)
    SwipeRefreshLayout mSwiperefreshlayout;
    View loadingView;
    View notDataView;
    View errorView;

    //引擎
    BaseQuickAdapter adapter;

    //分页
    private int currentPageIndex = 1;
    private int pageSize = 15;


    @Override
    protected int getLayoutId() {
        return R.layout.recycleview;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void requestPermissions() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        loadingView = getLayoutInflater().inflate(R.layout.loading_view, (ViewGroup) mRecyclerview.getParent(), false);
        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mRecyclerview.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestData(REQUEST_STATE_REFRESH);
            }
        });
        errorView = getLayoutInflater().inflate(R.layout.error_view, (ViewGroup) mRecyclerview.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestData(REQUEST_STATE_REFRESH);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        adapter = getAdapter();
        adapter.bindToRecyclerView(mRecyclerview);
        adapter.setOnLoadMoreListener(this, mRecyclerview);
    }

    protected abstract BaseQuickAdapter getAdapter();

    @Override
    protected void initToolBar() {
        if (supportBack()) {
            toolbar.supportBack();
        }
        toolbar.titleLeft(getActivityTitle());
    }

    protected boolean supportBack() {
        return true;
    }

    @StringRes
    protected abstract int getActivityTitle();

    @Override
    protected void initListener() {
        mSwiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData(REQUEST_STATE_REFRESH);
            }
        });
    }


    @Override
    protected void initData() {
        requestData(REQUEST_STATE_REFRESH);
    }

    private void requestData(int requestState) {
        if (REQUEST_STATE_REFRESH == requestState) {
            currentPageIndex = 1;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("username", "yangjinzhou");
        map.put("pageIndex", String.valueOf(currentPageIndex));
        map.put("pageSize", String.valueOf(pageSize));
        mPresenter.requestData(map, requestState);
    }


    @Override
    public void doParseData(int requestCode, Object data) {
        LogUtils.d(TAG + "doParseData");
        refreshUi(requestCode, RESPONSE_STATE_PARSEING);
        adapter.addData((List<T>) data);
    }

    //返回的
    // case RESPONSE_STATE_CANCEL:待研究
    //break;
    private void refreshUi(int requestCode, int state) {
        if (requestCode == REQUEST_STATE_REFRESH) {
            switch (state) {
                case RESPONSE_STATE_START:
                    adapter.setEmptyView(loadingView);
                    mSwiperefreshlayout.setRefreshing(true);//这句话和你手动下拉起一个作用，都是显示一个下拉的按钮，但是手动下拉是会触发事件，这个只是一个UI更新
                    break;
                case RESPONSE_STATE_ERROR:
                    mSwiperefreshlayout.setRefreshing(false);
                    adapter.setEmptyView(errorView);
                    break;
                case RESPONSE_STATE_PARSEING:
                    adapter.getData().clear();
                    break;
                case RESPONSE_STATE_Completed:
                    int size = adapter.getData().size();
                    if (size == 0) {
                        adapter.setEmptyView(notDataView);
                    }
                    currentPageIndex++;
                    mSwiperefreshlayout.setRefreshing(false);
                    break;
            }
        } else if (requestCode == REQUEST_STATE_LOADMORE) {
            switch (state) {
                case RESPONSE_STATE_START:
                    break;
                case RESPONSE_STATE_ERROR:
                    adapter.loadMoreFail();
                    break;
                case RESPONSE_STATE_Completed:
                    currentPageIndex++;
                    adapter.loadMoreComplete();
                    break;
            }

        }

    }

    @Override
    public void doOnError(int requestCode, String msg) {
        refreshUi(requestCode, RESPONSE_STATE_ERROR);
        super.doOnError(requestCode, msg);
    }


    @Override
    public void doOnStart(int requestCode) {
        if (requestCode == REQUEST_STATE_REFRESH || requestCode == REQUEST_STATE_LOADMORE) {
            refreshUi(requestCode, RESPONSE_STATE_START);
        } else {
            super.doOnStart(requestCode);
        }
    }

    @Override
    public void doOnCompleted(int requestCode) {
        refreshUi(requestCode, RESPONSE_STATE_Completed);
        super.doOnCompleted(requestCode);
    }

    //当最后一个可见时,会自动调用
    @Override
    public void onLoadMoreRequested() {
        //从第二页开始，才能加载更多。
        int currentPageSize = adapter.getData().size() - ((currentPageIndex - 2) * pageSize);
        if (currentPageSize < pageSize) {
            adapter.loadMoreEnd(false);//没有更多数据
        } else {
            requestData(REQUEST_STATE_LOADMORE);
        }
    }
}
