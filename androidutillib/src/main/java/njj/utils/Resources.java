package njj.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

/**
 * Created by jian on 2016/10/14.
 */
public class Resources {

    public static Drawable drawable(@DrawableRes int res) {
        return getContext().getResources().getDrawable(res);
    }

    public static int dimenInPx(@DimenRes int res) {
        return getContext().getResources().getDimensionPixelSize(res);
    }

    public static String string(@StringRes int resId) {
        return getContext().getResources().getString(resId);
    }

    public static float dimen(@DimenRes int res) {
        return getContext().getResources().getDimension(res);
    }

    public static int color(@ColorRes int res) {
        return getContext().getResources().getColor(res);
    }

    private static Context getContext() {
        return MyApplication.getApplication();
    }
}
