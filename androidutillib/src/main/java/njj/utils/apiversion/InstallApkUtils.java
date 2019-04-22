package njj.utils.apiversion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Author: NieJ
 * Created by niejianjian on 2019/4/20.
 * 说明：关于安装apk的过程中，针对Android7.0和8.0进行了适配。
 * 在调用该方法的页面，也就是传入context的Activity，回调onActivityResult()方法进行处理，
 *
 *  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 *      super.onActivityResult(requestCode, resultCode, data);
 *      if (requestCode == InstallApkUtils.REQUEST_CODE_APP_UNKNOWN_INSTALL) {
 *          if (Build.VERSION.SDK_INT 》= Build.VERSION_CODES.O) {
 *              // 判断是否授权成功，如果直接调用onInstall()，在禁止授权的情况下，会死循环调用授权界面
 *              if (getPackageManager().canRequestPackageInstalls()) {
 *                  new InstallApkUtils(this,new File("{安装包path}")).onInstall();
 *              } else {
 *                  // 禁止授权的相关提示或操作
 *              }
 *          }
 *      }
 *  }
 */
public class InstallApkUtils {

    public static final int REQUEST_CODE_APP_UNKNOWN_INSTALL = 10012;

    private WeakReference<Context> mReference;
    private File mFile;

    public InstallApkUtils(Context context, File file) {
        mReference = new WeakReference<>(context);
        mFile = file;
    }

    /**
     * 先添加权限 android.permission.REQUEST_INSTALL_PACKAGES
     * 判断版本，8.0及其以上版本，需要处理位置应用来源
     */
    public void onInstall() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean b = getCont().getPackageManager().canRequestPackageInstalls();
            if (b) {
                onInstall2();
            } else {
                //设置安装未知应用来源的权限
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                ((Activity) getCont()).startActivityForResult(intent, REQUEST_CODE_APP_UNKNOWN_INSTALL);
            }
        } else {
            onInstall2();
        }
    }

    /**
     * android 7.0的处理。否则会报 android.os.FileUriExposedException 错误
     */
    public void onInstall2() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(getCont(), "lanyue.reader.big.fileprovider", mFile);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(mFile),
                    "application/vnd.android.package-archive");
//            intent.setDataAndType(Uri.parse("file://" + PATH),
//                    "application/vnd.android.package-archive");
        }
        getCont().startActivity(intent);
    }

    private Context getCont() {
        return mReference.get();
    }

}
