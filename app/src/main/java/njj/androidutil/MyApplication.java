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
        Density.setSize(375f);
        Density.setDensity(this);
    }
}
