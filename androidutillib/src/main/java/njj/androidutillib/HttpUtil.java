package njj.com.myapplication1;

import android.text.TextUtils;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jian on 2016/12/15.
 */
public class HttpUtil {

    String Path = "http://test.bidit.cn/api/getServerTime/?clientType=1&version=4&versionName=test_2.0.2";

    /**
     * @param address
     * @param listener
     * @return 将String 改为 void。因为网络请求是耗时操作，我们需要在子线程中执行，
     * 但是子线程无法通过return语句来返回数据，也不能在子线程中更新UI，所以利用回调来实现
     * 除非使用runOnUiThread()方法。
     */
    public static void sendHttpRequest(final String address,
                                       final Map<String, String> params,
                                       final HttpCallbackListener listener) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(address);
            connection = (HttpURLConnection) url.openConnection();

            // 请求方式：GET 或者 POST
            connection.setRequestMethod("POST");
            // 设置读取超时时间
            connection.setReadTimeout(5000);
            // 设置连接超时时间
            connection.setConnectTimeout(5000);
            // 接收输入流
            connection.setDoInput(true);
            // 启动输出流，当需要传递参数时开启
            connection.setDoOutput(true);
            /*
             * 添加Header，告诉服务端一些信息，比如读取某个文件的多少字节到多少字节，是不是可以压缩传输，
             * 客户端支持的编码格式，客户端类型，配置，需求等。
             */
//            connection.setRequestProperty("Connection","Keep-Alive"); // 维持长连接
//            connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            // 添加参数, 写入参数之前不能读取服务器响应，如获得code
            addParams(address, connection.getOutputStream(), params);

            // 发起请求
            connection.connect();

            /**
             * getInputStream实际才发送请求，并得到网络返回的输入流
             */
            InputStream is = connection.getInputStream();
            // 服务器响应code，200表示请求成功并返回
            int code = connection.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK) {
                listener.onError("错误code = " + code);
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            if (listener != null) {
                listener.onSuccess(response.toString());
            }
            /*return response.toString();*/
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onError(e.toString());
            }
            /*return e.getMessage();*/
        } finally {
            if (connection != null) connection.disconnect();
        }
    }

    /**
     * 使用NameValuePair和BasicNameValuePair需要在build.gradle中的android闭包中添加：
     * useLibrary 'org.apache.http.legacy'
     */
    private static void addParams(String address, OutputStream output, Map<String, String> params)
            throws IOException {
        List<NameValuePair> paramList = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        StringBuilder paramStr = new StringBuilder();
        for (NameValuePair pair : paramList) {
            if (!TextUtils.isEmpty(paramStr)) {
                paramStr.append("&");
            }
            paramStr.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            paramStr.append("=");
            paramStr.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
        // 将参数写入到输出流
        writer.write(paramStr.toString());
        // 刷新对象输出流，将任何字节都写入潜在的流中
        writer.flush();
        // 关闭流对象。此时，不能再向对象输出流写入任何数据，先前写入的数据存在于内存缓冲区中,
        // 之后调用的getInputStream()函数时才把准备好的http请求正式发送到服务器
        writer.close();

        /**
         * 打印请求全路径的url
         */
        StringBuilder urlStr = new StringBuilder(address);
        urlStr.append("?");
        urlStr.append(paramStr.toString());
        Log.i("niejianjian", " -> url -> " + urlStr);
    }

    public interface HttpCallbackListener {
        void onSuccess(String response);

        void onError(String errorInfo);
    }

}
