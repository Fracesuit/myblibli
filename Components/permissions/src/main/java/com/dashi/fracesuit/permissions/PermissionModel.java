package com.dashi.fracesuit.permissions;

import com.tbruyelle.rxpermissions.Permission;

/**
 * Created by zhiren.zhang on 2017/6/21.
 */

public class PermissionModel {
    private String remindContent;
    private String androidPermission;
    private boolean must;
    public Permission permission;

    public PermissionModel(String androidPermission, String remindContent, boolean must) {
        this.remindContent = remindContent;
        this.androidPermission = androidPermission;
        this.must = must;
    }

    public PermissionModel(String androidPermission) {
        this.androidPermission = androidPermission;
        this.must = false;
    }

    public String getRemindContent() {
        return remindContent;
    }

    public String getAndroidPermission() {
        return androidPermission;
    }

    public boolean getMust() {
        return must;
    }


}
