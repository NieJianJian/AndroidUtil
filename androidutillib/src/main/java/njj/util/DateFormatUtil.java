package njj.util;

import android.text.format.DateFormat;

public class DateFormatUtil {

    /**
     * 格式化日期
     */
    public static CharSequence formatDate(long time) {
        return DateFormat.format("yyyy-MM-dd", time);
    }

    public static CharSequence formatTime(long time) {
        return DateFormat.format("yyyy-MM-dd HH:mm:ss", time);
    }

    public static CharSequence formatTimeWithoutYearField(long timeStamp) {
        return DateFormat.format("MM月dd日 HH:mm:ss", timeStamp);
    }

    public static CharSequence formatTimeWithoutYearField2(long timeStamp) {
        return DateFormat.format("MM月dd日 HH:mm:ss", timeStamp);
    }

    public static CharSequence formatTimeWithoutYearSecondField(long timeStamp) {
        return DateFormat.format("MM月dd日 HH:mm", timeStamp);
    }

    public static CharSequence formatTimeWithoutYearSecondField1(long timeStamp) {
        return DateFormat.format("yyyyMMddHH", timeStamp);
    }
}  
