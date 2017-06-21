package com.dashi.fracesuit.permissions;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Fracesuit on 2017/6/13.
 */

public class PermissionsHelp {
    //跳转到请求权限的请求码
    public static final int PERMISSION_REQUEST_CODE = 5678;

    private Activity activity;
    private static PermissionsHelp permissionsHelp;

    private RxPermissions rxPermissions;

    private PermissionsHelp(@NonNull Activity activity) {
        this.activity = activity;
        rxPermissions = new RxPermissions(activity);
    }

    private PermissionsHelp() {
        throw new RuntimeException("必须使用带有一个参数的构造方法");
    }

    //这里是单例的话，会造成
    public static PermissionsHelp with(@NonNull Activity activity) {
        if (permissionsHelp == null) {
            synchronized (PermissionsHelp.class) {
                if (permissionsHelp == null)
                    permissionsHelp = new PermissionsHelp(activity);
            }
        }
        return PermissionsHelp.permissionsHelp;
    }

    public void requestPermissions(@NonNull String... permissions) {
        requestPermissions(null, permissions);
    }

    public void requestPermissions(OnRequestPermissionsListener onRequestPermissionsListener, @NonNull String... permissions) {
        PermissionModel[] permissionModels = new PermissionModel[permissions.length];
        int i = 0;
        for (String p : permissions) {
            permissionModels[i++] = new PermissionModel(p);
        }
        requestPermissions(onRequestPermissionsListener, permissionModels);
    }

    public void requestPermissions(final OnRequestPermissionsListener onRequestPermissionsListener, @NonNull PermissionModel... permissionModels) {
        Observable.from(permissionModels)
                .flatMap(new Func1<PermissionModel, Observable<PermissionModel>>() {
                    @Override
                    public Observable<PermissionModel> call(final PermissionModel permissionModel) {
                        return rxPermissions.requestEach(permissionModel.getAndroidPermission()).map(new Func1<Permission, PermissionModel>() {
                            @Override
                            public PermissionModel call(Permission permission) {
                                permissionModel.permission = permission;
                                return permissionModel;
                            }
                        });
                    }
                })
                .subscribe(new Subscriber<PermissionModel>() {
                    @Override
                    public void onCompleted() {
                        if (onRequestPermissionsListener != null) {
                            onRequestPermissionsListener.completed();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (onRequestPermissionsListener != null) {
                            onRequestPermissionsListener.error(e);
                        }
                    }

                    @Override
                    public void onNext(PermissionModel permissionModel) {
                        Permission permission = permissionModel.permission;
                        boolean isMust = permissionModel.getMust();
                        String content = permissionModel.getRemindContent();

                        if (permission.granted) {
                            if (onRequestPermissionsListener != null) {
                                onRequestPermissionsListener.grant(permission, true);
                            }
                        } else if (permission.shouldShowRequestPermissionRationale) {//小米的这个永远是false
                            // 这里是点击拒绝，并且没有勾选不再询问
                            if (onRequestPermissionsListener != null) {
                                onRequestPermissionsListener.grant(permission, false);
                            }
                            if (isMust) {
                                destoryActivity(this);
                            }

                        } else {
                            if (isMust) {
                                //这里是勾选了不再询问
                                showMessageDialog(this, content, null);
                            }
                        }
                    }

                });

    }


    private void showMessageDialog(final Subscriber<PermissionModel> subscriber, String content, final OnDialogListener onDialogListener) {
        //如果有弹窗事件，那么就释放监听流，以防后面操作会释放了activity，导致异常
        if (!subscriber.isUnsubscribed()) subscriber.unsubscribe();
        new MaterialDialog.Builder(activity)
                .title(R.string.permission_title)
                .content(content)
                .positiveText(R.string.permission_confirm)
                .negativeText(R.string.permission_cancel)
                .canceledOnTouchOutside(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (onDialogListener != null) {
                            onDialogListener.onPositive();
                            dialog.dismiss();
                        }
                        //跳转到权限设置界面
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri1 = Uri.parse("package:" + activity.getPackageName());
                        intent.setData(uri1);
                        activity.startActivityForResult(intent, PERMISSION_REQUEST_CODE);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (onDialogListener != null) {
                            onDialogListener.onNegative();
                            dialog.dismiss();
                        }
                        destoryActivity();
                    }
                })
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (onDialogListener != null) {
                            onDialogListener.onDismiss();
                        }
                    }
                })
                .show();

    }

    private void destoryActivity(Subscriber<PermissionModel> subscriber) {
        if (!subscriber.isUnsubscribed()) subscriber.unsubscribe();
        permissionsHelp = null;
        activity.finish();
    }

    private void destoryActivity() {
        permissionsHelp = null;
        activity.finish();
    }
}
