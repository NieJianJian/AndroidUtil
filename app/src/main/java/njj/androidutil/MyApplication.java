package njj.androidutil;

import android.app.Application;

import njj.utils.Density;

/**
 * Created by niejianjian on 2018/7/24.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Density.setDensity(this);
//        Density.setSize(375f); // 设置基础宽高，如设计图是720*1280，density=2.0，对应的基础宽就是360f，不设置默认是360f
//        Density.setSize(360f, 640f); // 也可以同时设置基础宽和高。当以高为基准进行适配时，要用到，默认是640f
    }
}
