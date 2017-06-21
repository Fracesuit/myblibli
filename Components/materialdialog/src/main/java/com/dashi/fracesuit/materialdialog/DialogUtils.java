package com.dashi.fracesuit.materialdialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by Fracesuit on 2017/5/24.
 */

public class DialogUtils {

    public interface OnCallBackListener {
        void onPositive();

        void onNegative();

        void onDismiss();
    }

    public static void showMessageDialog(Activity activity, String title, String content, boolean canceled, final OnCallBackListener onCallBackListener) {
        new MaterialDialog.Builder(activity)
                .title(title)
                .content(content)
                .positiveText("确认")
                .negativeText("取消")
                .canceledOnTouchOutside(canceled)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (onCallBackListener != null) {
                            dialog.dismiss();
                            onCallBackListener.onPositive();
                        }

                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (onCallBackListener != null) {
                            dialog.dismiss();
                            onCallBackListener.onNegative();
                        }

                    }
                })
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if(onCallBackListener!=null)
                        {
                            onCallBackListener.onDismiss();
                        }
                    }
                })
                .show();
    }

}
