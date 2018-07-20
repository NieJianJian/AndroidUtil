package njj.utils.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niejianjian on 2018/7/17.
 */

public class PermissionActivity extends Activity {

    private List<String> mPermissionList;
    private static PermissionCallback mCallback;
    private boolean mIsShowDialog;
    private final int REQUEST_CODE = 101;
    private PermissioUtil mPermissioUtil;
    private String mAgainMsg, mSettingMsg;

    public static void startAct(Context context, Bundle bundle, PermissionCallback callback) {
        mCallback = callback;
        context.startActivity(new Intent(context, PermissionActivity.class).putExtras(bundle));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        mIsShowDialog = bundle.getBoolean("isShowDialog");
        mAgainMsg = bundle.getString("againDialogMsg");
        mSettingMsg = bundle.getString("settingDialogMsg");
        mPermissionList = (List<String>) bundle.getSerializable("permissions");
        String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);
        mPermissioUtil = new PermissioUtil(this);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<String> denyPermissions = new ArrayList<>();
        if (grantResults.length <= 0) {
            if (mCallback != null) mCallback.onCancel(denyPermissions);
            finish();
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE:
                boolean shouldApply = false;
                boolean shouldRequestPermission = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        /**
                         * shouldShowRequestPermissionRationale {true ： 拒绝 ； false : 拒绝后不在询问}
                         * 第一次打开app，在第一次弹出请求权限之前，是false。
                         */
                        shouldApply = true;
                        if (shouldRequestPermission) {
                            shouldRequestPermission =
                                    ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]);
                        }
                        denyPermissions.add(permissions[i]);
                    }
                }
                if (shouldApply) { // 有禁止所有或部分权限
                    if (mCallback != null)
                        mCallback.onDeny(!shouldRequestPermission, denyPermissions);
                    if (mIsShowDialog) { // 是否允许弹窗提示
                        showPermissionDialog(shouldRequestPermission, denyPermissions);
                    } else {
                        finish();
                    }
                } else { // 没有禁止的权限，全部都允许。完成授权。
                    if (mCallback != null) mCallback.onFinish(true);
                    finish();
                }
                break;
        }
    }

    AlertDialog mPermissionDialog;

    private void showPermissionDialog(final boolean shouldRequestPermission, final List<String> denyPermissions) {
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(this)
                    .setMessage(dialogMsg(shouldRequestPermission))
                    .setPositiveButton(
                            shouldRequestPermission ?
                                    "继续申请" : "前往设置",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mPermissionDialog.dismiss();
                                    if (!shouldRequestPermission) {
                                        mPermissioUtil.gotoSettings();
                                        finish();
                                    } else {
                                        String[] permissions = denyPermissions.toArray(new String[denyPermissions.size()]);
                                        ActivityCompat.requestPermissions(PermissionActivity.this, permissions, REQUEST_CODE);
                                    }
                                }
                            })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mCallback != null) mCallback.onCancel(denyPermissions);
                            mPermissionDialog.dismiss();
                            finish();
                        }
                    })
                    .create();
        }
        mPermissionDialog.show();
    }

    private String dialogMsg(boolean bo) {
        return bo ?
                (mAgainMsg == null ?
                        "禁止相关权限，将无法正常使用" + mPermissioUtil.getAppName() + "，是否重新申请权限？" : mAgainMsg) :
                (mSettingMsg == null ?
                        "禁止相关权限，将无法正常使用" + mPermissioUtil.getAppName() + "，请手动开启权限" : mSettingMsg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCallback = null;
        System.gc();
    }
}