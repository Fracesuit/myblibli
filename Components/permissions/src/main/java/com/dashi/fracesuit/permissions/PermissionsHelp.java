package com.dashi.fracesuit.permissions;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.tbruyelle.rxpermissions.RxPermissions;

/**
 * Created by Fracesuit on 2017/6/13.
 */

public class PermissionsHelp {
    /*// ... type definitions
    // Describes when the annotation will be discarded
    @Retention(RetentionPolicy.SOURCE)
    // Enumerate valid values for this interface
    @StringDef({ FILTER_BLUE, FILTER_RED, FILTER_GRAY })
    // Create an interface for validating int types
    public @interface FilterColorDef { }
    // Mark the argument as restricted to these enumerated types
    public FilterColorDescriptor(@FilterColorDef String filterColor) {
        this.filterColor = filterColor;
    }

    // get data
    @FilterColorDef
    public String getFilterColor() {
        return filterColor;
    }*/
    public interface OnRequestPermissionsBack {
        void granted();//同意

        void refuse();//拒绝
    }

    private static PermissionsHelp permissionsHelp;

    private RxPermissions rxPermissions;

    private PermissionsHelp(@NonNull Activity activity) {
        rxPermissions = new RxPermissions(activity);
    }

    public static PermissionsHelp with(@NonNull Activity activity) {
        if (permissionsHelp == null) {
            synchronized (PermissionsHelp.class) {
                if (permissionsHelp == null)
                    permissionsHelp = new PermissionsHelp(activity);
            }
        }
        return PermissionsHelp.permissionsHelp;
    }

   /* public void requestPermissions(String... permissions) {
        rxPermissions.requestEach(permissions)
                .subscribe();

    }*/


}
