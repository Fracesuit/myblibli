package com.dashi.fracesuit.commonlibs.help;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.dashi.fracesuit.commonlibs.utils.DialogUtils;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.Subscriber;

/**
 * Created by Fracesuit on 2017/6/13.
 */

public class PermissionsHelp {
    public static final int PERMISSION_REQUEST_CODE = 100;
    private static Boolean isFirst = true;

    public interface OnRequestPermissionsBack {
        void granted();//同意

        void refuse();//拒绝

        void completed();//完成，此处有可能是同意，有可能是拒绝
    }

    private static PermissionsHelp permissionsHelp;

    private RxPermissions rxPermissions;

    private PermissionsHelp(@NonNull Activity activity) {
        rxPermissions = new RxPermissions(activity);
    }

    private PermissionsHelp() {
        throw new IllegalArgumentException("必须使用带有一个参数的构造方法");
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

    public void requestPermissions(final OnRequestPermissionsBack onRequestPermissionsBack, String... permissions) {
        rxPermissions.requestEach(permissions)
                .subscribe(new Subscriber<Permission>() {
                    @Override
                    public void onCompleted() {
                        isFirst = true;
                        if (onRequestPermissionsBack != null) {
                            onRequestPermissionsBack.completed();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        isFirst = true;
                    }

                    @Override
                    public void onNext(Permission permission) {
                        if (permission.granted) {
                            if (onRequestPermissionsBack != null) {
                                onRequestPermissionsBack.granted();
                            }
                        } else if (permission.shouldShowRequestPermissionRationale) {//小米的这个永远是false
                            // Denied permission without ask never again
                            activity.finish();
                        } else {
                            //这里有个小问题,如果有多个权限,那么会弹出多次框,这样第二个dialog会出现问题
                            if (isFirst) {
                                isFirst = false;
                                DialogUtils.showMessageDialog(activity, "权限设置", getPermissionRemindContent(permission.name), false, new DialogUtils.OnCallBackListener() {
                                    @Override
                                    public void onPositive() {
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri1 = Uri.parse("package:" + activity.getPackageName());
                                        intent.setData(uri1);
                                        activity.startActivityForResult(intent, PERMISSION_REQUEST_CODE);
                                    }

                                    @Override
                                    public void onNegative() {
                                        activity.finish();
                                    }

                                    @Override
                                    public void onDismiss() {
                                        isFirst = true;
                                    }
                                });
                            }

                        }
                    }
                });
    }

    private static String getPermissionRemindContent(String permissionName) {
        return permissionName + "为应用必需权限,点击确认去设置";
    }
}
