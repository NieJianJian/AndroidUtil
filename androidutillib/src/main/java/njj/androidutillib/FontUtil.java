package njj.androidutillib;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jian on 2016/9/18.
 */
public class FontUtil {

    private static Typeface sTypeface;

    public static final String FONTS_DIR = "fonts/";
    public static final String DEF_FONT = FONTS_DIR + "PingFangMedium.ttf";

    /*在application加载的时候，初始化，缓存Typeface对象，因为在文件中加载是要耗时的*/
    public static void initFont(Context context) {
        getCustomTypeface(context);
    }

    public static Typeface getCustomTypeface(Context context) {
        if (sTypeface == null) {
            sTypeface = Typeface.createFromAsset(context.getAssets(), DEF_FONT);
        }
        return sTypeface;
//        changeFont(rootView, Typeface.createFromAsset(rootView.getContext().getAssets(), DEF_FONT));
    }

    public static void changeFont(View rootView) {
        final Typeface typeface = getCustomTypeface(rootView.getContext());
        if (typeface == null) return;

        if (rootView instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) rootView;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                changeFont(group.getChildAt(i));
            }
        } else if (rootView instanceof TextView) {
            ((TextView) rootView).setTypeface(typeface);
        }
    }

    public static void changeFont(Activity activity) {
        changeFont(activity.findViewById(android.R.id.content));
    }

    public static void changeFont(Dialog dialog) {
        changeFont(dialog.findViewById(android.R.id.content));
    }

}
