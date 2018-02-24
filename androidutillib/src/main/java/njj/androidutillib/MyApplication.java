package njj.androidutillib;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jian on 2016/11/7.
 */
public class MyApplication extends Application {

    // static无法跨进程访问

    private static Context mAppContext;
    /* 可见的activity组件数量 */
    private static int visibleComponentCount = 0;
    /*存放Activity对象*/
    private static List<Activity> mActivityList = new LinkedList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
        // 注册Activity的生命周期回调
        registerActivityLifecycleCallbacks(callbacks);
        // 做一些初始化的操作
//        AppLifeCycleAware.Instance.onStart(this);
    }

    /**
     * 应用是否在前台
     */
    public static boolean isForeground() {
        return visibleComponentCount > 0;
    }

    public static Context getApplication() {
        return mAppContext;
    }

    public static void addActivity(Activity activity) {
        mActivityList.add(activity);
    }

    /**
     * 退出所有的Activity
     */
    public void exit() {
        for (Activity activity : mActivityList) {
            activity.finish();
        }
    }

    /**
     * 退出指定的Activity
     */
    public static void exitActivity(Activity activity) {
        for (Activity activity1 : mActivityList) {
            if (activity == activity1) {
                activity.finish();
                mActivityList.remove(activity);
            }
        }
    }

    /**
     * 此方法只适用于虚拟机
     */
    @Override
    public void onTerminate() {
        unregisterActivityLifecycleCallbacks(callbacks);
//        AppLifeCycleAware.Instance.onStop();
        super.onTerminate();
    }

    /*应用生命周期监听*/
    private ActivityLifecycleCallbacks callbacks = new ActivityLifecycleCallbacks() {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            mActivityList.add(activity);
            visibleComponentCount += 1;
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
            visibleComponentCount -= 1;
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            mActivityList.remove(activity);
        }
    };

}
