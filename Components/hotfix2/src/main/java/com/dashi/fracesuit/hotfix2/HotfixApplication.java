package com.dashi.fracesuit.hotfix2;

import android.app.Application;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Created by Fracesuit on 2017/5/24.
 */

public class HotfixApplication {
    //private static final String AESKEY = "HotfixApplication";
    private static final String AESKEY = null;

    public static void init(Application application, String appVersionName, boolean isDebug) {
        SophixManager.getInstance().setContext(application)
                .setAppVersion(appVersionName)
                .setAesKey(AESKEY)
                .setEnableDebug(isDebug)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后应用自杀
                        } else if (code == PatchStatus.CODE_LOAD_FAIL) {
                            // 内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载
                            SophixManager.getInstance().cleanPatches();
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                        }
                        Log.e("HotfixApplication", "mode==" + mode + " code==" + code + " info==" + info + " handlePatchVersion==" + handlePatchVersion);
                    }
                }).initialize();
        SophixManager.getInstance().queryAndLoadNewPatch();
    }
}
