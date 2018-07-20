package njj.utils.permission;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by niejianjian on 2018/7/17.
 */

public class ReqPermisson {

    private Context mContext;
    private String[] mPermission;
    private String[] mDefaultPermission = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE}; // 预留默认的权限
    private PermissionCallback mCallback;
    private boolean mIsShowDialog = true;
    private String mAgainMsg, mSettingMsg;

    private ReqPermisson(Context context) {
        this.mContext = context;
    }

    public static ReqPermisson init(Context context) {
        return new ReqPermisson(context);
    }

    /**
     * 传递需要请求的权限。所有的权限申请，需要先在AndroidManifest.xml文件中进行声明。否则无效。
     *
     * @param permission
     * @return
     */
    public ReqPermisson addPermission(String... permission) {
        this.mPermission = permission;
        return this;
    }

    /**
     * 选择禁止使用权限后，是否弹窗提示权限重要性，默认关闭
     * 可以选择使用默认的弹窗，也可以屏蔽掉默认弹窗后，根据callback函数的回调方法，进行自定义的处理。
     *
     * @param isShow true表示弹窗提示，false表示不提示
     * @return
     */
    public ReqPermisson isShowDialog(boolean isShow) {
        this.mIsShowDialog = isShow;
        return this;
    }

    /**
     * 自定义禁止授予权限dialog的弹窗提示信息。（未勾选下次不在询问）
     *
     * @param againMsg
     * @return
     */
    public ReqPermisson setAgainMsg(String againMsg) {
        this.mAgainMsg = againMsg;
        return this;
    }

    /**
     * 自定义禁止授予权限dialog的弹窗提示信息。（勾选下次不在询问）
     *
     * @param settingMsg
     * @return
     */
    public ReqPermisson setSettingMsg(String settingMsg) {
        this.mSettingMsg = settingMsg;
        return this;
    }

    /**
     * 不需要实现回调函数的权限请求
     */
    public void request() {
        List<String> permissionList = checkPermission();
        if (permissionList == null) return;
        if (!permissionList.isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("permissions", (Serializable) permissionList);
            bundle.putBoolean("isShowDialog", mIsShowDialog);
            mContext.startActivity(new Intent(mContext, PermissionActivity.class).putExtras(bundle));
        }
    }

    /**
     * 需要实现回调的权限请求
     *
     * @param callback
     */
    public void request(PermissionCallback callback) {
        if (callback == null) {
            Log.d("ReqPermission", "The Callbakc function is null");
            return;
        }
        List<String> permissionList = checkPermission();
        if (permissionList == null || permissionList.isEmpty()) {
            callback.onFinish(false);
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("permissions", (Serializable) permissionList);
        bundle.putBoolean("isShowDialog", mIsShowDialog);
        bundle.putString("againDialogMsg", mAgainMsg);
        bundle.putString("settingDialogMsg", mSettingMsg);
        PermissionActivity.startAct(mContext, bundle, callback);
    }

    private List<String> checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return null; // android 6.0以下，直接返回
        if (mPermission == null || mPermission.length <= 0) {
            Log.d("ReqPermission", "params is null");
            return null;  // 防止没有调用addPermission函数，没有将权限数组传递过来。
        }
        List<String> permissionList = new ArrayList<>();
        for (String per : mPermission) {
            if (ContextCompat.checkSelfPermission(mContext, per) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(per);
            }
        }
        return permissionList;
    }
}
