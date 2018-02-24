package com.example.mytest1;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jian on 2016/11/7.
 * 需要添加权限android.permission.GET_TASKS
 */
public class ActivityManagerUtils {

    private static ActivityManager mActivityManager;

    private ActivityManagerUtils() {
		
    }

	public static init(){
		init(MyApplication.getContext());
	}

	public static init(Context context){
		if (mActivityManager == null) {
            mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        }
	}

    public static ActivityManager getActivityManager() {
        return getActivityManager(MyApplication.getContext());
    }

    public static ActivityManager getActivityManager(Context context) {
        if (mActivityManager == null) {
            mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        }
        return mActivityManager;
    }

    public static boolean isForeground(Context context) {
        return isForeground(context.getClass());
    }

    /**
     * 判断是不是前台Activity，也就是判断某个Acitivity当前是否显示
     *
     * @param clazz 需要判断的类名
     */
    public static boolean isForeground(Class<?> clazz) {
        // maxNum:做多获取的Tasks的个数
        ComponentName cn = getActivityManager().getRunningTasks(1).get(0).topActivity;
//        cn.getShortClassName(); // 等同于getLocalClassName()；
//        cn.getPackageName(); // 获得包名
//        cn.getClassName(); // 获得全路径名称

        return cn.getClassName().equals(clazz.getName());
    }

    /**
     * 获得系统正在运行的进程
     */
    public static void getRuningProcessList() {
        List<ActivityManager.RunningAppProcessInfo> appList1 = getActivityManager()
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo running : appList1) {
            System.out.println("process--->" + running.processName);
            System.out.println("pid--->" + running.pid);
            // 所有已加载到进程中的包。大部分只有一个包名，和processName一致，个别的有两个包名
            System.out
                    .println("pkgList--->" + Arrays.toString(running.pkgList));
            System.out.println("uid--->" + running.uid);
        }
    }

    /**
     * 获得系统正在运行的service
     */
    public static void getRunningServiceList() {
        List<ActivityManager.RunningServiceInfo> serviceList = getActivityManager()
                .getRunningServices(1000);
        for (ActivityManager.RunningServiceInfo running : serviceList) {
            System.out.println("--->" + running.service.getClassName());
            System.out.println("--->" + running.service.getPackageName());
        }
    }

    /**
     * 获得Activity的名字
     *
     * @param activity 可传this(this代表所在Activity的对象)，可传xxxxActivity.this
     * @return 返回值为“xxxxActivity”，如果有二级包名，则返回值为“xxx.xxxxActivity”
     */
    public static String getActivityName(Activity activity) {
        return activity.getLocalClassName();
    }

    /**
     * 获得应用包名
     */
    public static String getActivityPkgName(Activity activity) {
        return activity.getPackageName();
    }

    /**
     * 返回类名，只有类名
     */
    public static String getClassName(Class<?> clazz) {
        return clazz.getSimpleName();
    }

    /**
     * 包名+（二级包名）+ 类名    中间用“.”分割
     */
    public static String getAllName(Class<?> clazz) {
        return clazz.getName();
    }


}
