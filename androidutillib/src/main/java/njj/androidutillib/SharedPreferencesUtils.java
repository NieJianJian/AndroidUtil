//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.SharedPreferences;
import android.util.Log;
import com.os.soft.rad.context.AppContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SharedPreferencesUtils {
    private static SharedPreferences marsorPreferences = null;

    private SharedPreferencesUtils() {
    }

    private static SharedPreferences getPreferences() {
        if(marsorPreferences == null) {
            if(AppContext.appContext == null) {
                return null;
            }

            marsorPreferences = AppContext.appContext.getSharedPreferences("MarsorCommonPreferences", 0);
        }

        return marsorPreferences;
    }

    public static void savePreference(String key, Boolean value) {
        try {
            getPreferences().edit().putBoolean(key, value.booleanValue()).commit();
        } catch (Exception var3) {
            Log.e("UniversalAndroidCommon", "保存boolean型数据时出错！", var3);
        }

    }

    public static boolean getPreference(String key, boolean defValue) {
        try {
            return getPreferences().getBoolean(key, defValue);
        } catch (Exception var3) {
            Log.e("UniversalAndroidCommon", "获取boolean型数据时出错！", var3);
            return defValue;
        }
    }

    public static void savePreference(String key, float value) {
        try {
            getPreferences().edit().putFloat(key, value).commit();
        } catch (Exception var3) {
            Log.e("UniversalAndroidCommon", "保存float型数据时出错！", var3);
        }

    }

    public static float getPreference(String key, float defValue) {
        try {
            return getPreferences().getFloat(key, defValue);
        } catch (Exception var3) {
            Log.e("UniversalAndroidCommon", "获取float型数据时出错！", var3);
            return defValue;
        }
    }

    public static void savePreference(String key, int value) {
        try {
            getPreferences().edit().putInt(key, value).commit();
        } catch (Exception var3) {
            Log.e("UniversalAndroidCommon", "保存int型数据时出错！", var3);
        }

    }

    public static int getPreference(String key, int defValue) {
        try {
            return getPreferences().getInt(key, defValue);
        } catch (Exception var3) {
            Log.e("UniversalAndroidCommon", "获取int型数据时出错！", var3);
            return defValue;
        }
    }

    public static void savePreference(String key, long value) {
        try {
            getPreferences().edit().putLong(key, value).commit();
        } catch (Exception var4) {
            Log.e("UniversalAndroidCommon", "保存long型数据时出错！", var4);
        }

    }

    public static long getPreference(String key, long defValue) {
        try {
            return getPreferences().getLong(key, defValue);
        } catch (Exception var4) {
            Log.e("UniversalAndroidCommon", "获取long型数据时出错！", var4);
            return defValue;
        }
    }

    public static void savePreference(String key, String value) {
        try {
            getPreferences().edit().putString(key, value).commit();
        } catch (Exception var3) {
            Log.e("UniversalAndroidCommon", "保存字符串数据时出错！", var3);
        }

    }

    public static String getPreference(String key, String defValue) {
        try {
            return getPreferences().getString(key, defValue);
        } catch (Exception var3) {
            Log.e("UniversalAndroidCommon", "获取字符串数据时出错！", var3);
            return defValue;
        }
    }

    public static void saveSerializable(String key, Serializable value) {
        try {
            String e = AppContext.getAppDataPath(new boolean[]{true}) + key;
            FileOutputStream fos = new FileOutputStream(e);
            ObjectOutputStream sos = new ObjectOutputStream(fos);
            sos.writeObject(value);
            sos.close();
        } catch (Exception var5) {
            Log.e("UniversalAndroidCommon", "保存序列化对象的时候出错", var5);
        }

    }

    public static <T> T getSerializable(String key) {
        try {
            String e = AppContext.getAppDataPath(new boolean[]{true}) + key;
            File file = new File(e);
            if(!file.exists()) {
                return null;
            } else {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Object o = ois.readObject();
                ois.close();
                return o;
            }
        } catch (Exception var7) {
            Log.d("UniversalAndroidCommon", "获得序列化对象的时候出错");
            return null;
        }
    }
}
