package com.dashi.fracesuit.permissions;

import com.tbruyelle.rxpermissions.Permission;

/**
 * Created by zhiren.zhang on 2017/6/21.
 */

public interface OnRequestPermissionsListener {
    void grant(Permission permission, boolean granted);//授予结果

    void completed();//完成，此处有可能是同意，有可能是拒绝

    void error(Throwable e);//错误
}
