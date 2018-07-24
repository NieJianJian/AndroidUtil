package njj.androidutil;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import njj.utils.Density;

/**
 * Created by niejianjian on 2018/7/24.
 */

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Density.setDefault(this);
        setContentView(R.layout.activity_second);
    }
}
