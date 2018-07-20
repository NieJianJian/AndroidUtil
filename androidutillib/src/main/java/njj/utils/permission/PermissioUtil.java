package njj.utils.permission;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

/**
 * Created by niejianjian on 2018/7/20.
 */

public class PermissioUtil {

    private Context mContext;

    public PermissioUtil(Context context) {
        this.mContext = context;
    }

    /**
     * 跳转到设置界面，手动授予权限
     */
    public void gotoSettings() {
        Uri packageURI = Uri.parse("package:" + mContext.getPackageName());
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        mContext.startActivity(intent);
    }

    /**
     * 获取应用程序名称
     */
    public synchronized String getAppName() {
        try {
            PackageManager packageManager = mContext.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    mContext.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return mContext.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
