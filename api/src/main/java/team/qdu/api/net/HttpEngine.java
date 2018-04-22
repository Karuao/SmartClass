package team.qdu.api.net;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Http引擎处理类
 * Created by Rock on 2017/9/3.
 */

public class HttpEngine {

    private final static String TAG = "HttpEngine";
    //The development database
//   private final static String SERVER_URL = "http://10.0.2.2";
//    private final static String SERVER_URL = "http://47.94.7.159";
    //The official database
    private final static String SERVER_URL = "http://140.143.134.146";
    private final static String REQUEST_METHOD = "POST";
    private final static String ENCODE_TYPE = "UTF-8";
    private final static int TIME_OUT = 8000;

    private static HttpEngine instance = null;

    public static HttpEngine getInstance() {
        if (instance == null) {
            instance = new HttpEngine();
        }
        return instance;
    }

    public <T> T postHandle(Map<String, String> paramsMap, Type typeofT, String urlTail) throws IOException {
        String data = "";
        if (paramsMap != null) {
            data = joinParams(paramsMap);
        }
        //打印出请求
        Log.i(TAG, "request: " + data);
        HttpURLConnection connection = getConnection(urlTail);
        connection.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
        connection.connect();
        OutputStream os = connection.getOutputStream();
        os.write(data.getBytes());
        os.flush();
        if (connection.getResponseCode() == 200) {
            //获取相应的输入流对象
            InputStream is = connection.getInputStream();
            //创建字节输出流对象
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //定义读取的长度
            int len = 0;
            //定义缓冲区
            byte buffer[] = new byte[1024];
            //按照缓冲区的大小，循环读取
            while ((len = is.read(buffer)) != -1) {
                //根据读取的长度写入到os对象中
                baos.write(buffer, 0, len);
            }
            //释放资源
            is.close();
            baos.close();
            connection.disconnect();
            //返回字符串
            final String result = new String(baos.toByteArray());
            //打印出结果
            Log.i(TAG, "response:" + result);
            return JSON.parseObject(result, typeofT);
        } else {
            connection.disconnect();
            return null;
        }
    }

    // 获取connection
    private HttpURLConnection getConnection(String urlTail) {
        HttpURLConnection connection = null;
        //初始化connection
        try {
            //根据地址创建URL对象
            URL url = new URL(SERVER_URL + urlTail);
            //根据URL对象打开连接
            connection = (HttpURLConnection) url.openConnection();
            //设置请求的方法
            connection.setRequestMethod(REQUEST_METHOD);
            //发送POST请求必须设置允许输入，默认为true
            connection.setDoInput(true);
            //发送POST请求必须设置允许输出，默认为false
            connection.setDoOutput(true);
            //设置不使用缓存
            connection.setUseCaches(false);
            //设置请求的超时时间
            connection.setReadTimeout(TIME_OUT);
            connection.setConnectTimeout(TIME_OUT);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Response-Type", "json");
            connection.setChunkedStreamingMode(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private String joinParams(Map<String, String> paramsMap) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : paramsMap.keySet()) {
            stringBuilder.append(key);
            stringBuilder.append("=");
            try {
                stringBuilder.append(URLEncoder.encode(paramsMap.get(key), ENCODE_TYPE));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            stringBuilder.append("&");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }
}
