package njj.androidutillib;

import android.text.TextUtils;
import android.util.Log;

import java.security.MessageDigest;

public class EncryAndDecry {
    private static final String Encoding = "UTF-8";

    /**
     * 取得按照指定编码方式编码字符串的byte数组的Md5原始数组
     *
     * @param strOrigin 原始字符串
     * @param encodings 字符串使用的编码方式
     */
    public static byte[] getMd5Origin(String strOrigin, String... encodings) {
        String encoding = Encoding;
        if (TextUtils.isEmpty(strOrigin)) {
            return null;
        }

        if (encodings != null && encodings.length > 0) {
            encoding = encodings[0];
        }

        MessageDigest msgDigest = null;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
            // 注意改接口是按照指定编码形式签名
            msgDigest.update(strOrigin.getBytes(encoding));
        } catch (Exception e) {
            Log.e(EncryAndDecry.class.getName(), "不能生成指定的MD5值！", e);
            return null;
        }

        return msgDigest.digest();
    }

    /**
     * 取得按照指定编码方式编码字符串的byte数组的Md5数组 支付宝是采用该方法进行Md5串的获取
     *
     * @param strOrigin     原始字符串
     * @param originStrings 字符串采用的编码方式
     */
    public static String getMd5String(String strOrigin, String... originStrings) {

        byte[] orgin = getMd5Origin(strOrigin, originStrings);
        return encodeHex(orgin);
    }

    /**
     * 将一个byte数组变化为对应的16进制字符串。
     */
    public static String encodeHex(byte[] data) {

        /**
         * Used building output as Hex
         */
        final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }

        return new String(out);
    }

}