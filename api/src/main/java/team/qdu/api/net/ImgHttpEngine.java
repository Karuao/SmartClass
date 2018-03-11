package team.qdu.api.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 11602 on 2018/1/30.
 */

public class ImgHttpEngine {

    private final static String TAG = "ImgHttpEngine";
    //The development database
    private final static String SERVER_URL = "http://10.0.2.2:80/";
    //The official database
//    private final static String SERVER_URL = "http://140.143.134.146:80/";
    private final static String REQUEST_METHOD = "GET";
    private final static String ENCODE_TYPE = "UTF-8";
    private final static int TIME_OUT = 4000;

    private static ImgHttpEngine instance = null;

    public static ImgHttpEngine getInstance() {
        if (instance == null) {
            instance = new ImgHttpEngine();
        }
        return instance;
    }

    public Bitmap getImg(String urlTail) throws IOException {
        //打印出请求
        Log.i(TAG, "request: " + urlTail);
        HttpURLConnection connection = getConnection(urlTail);
        connection.connect();
        if (connection.getResponseCode() == 200) {
            //获取相应的输入流对象
            InputStream is = connection.getInputStream();
            int length = (int) connection.getContentLength();

            byte[] imgData = new byte[length];
            byte[] buffer = new byte[512];
            int readLen = 0;
            int destPos = 0;
            while ((readLen = is.read(buffer)) > 0) {
                System.arraycopy(buffer, 0, imgData, destPos, readLen);
                destPos += readLen;
            }
            //释放资源
            is.close();
            connection.disconnect();
            return BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
        } else {
            connection.disconnect();
            return null;
        }
    }

    //获取连接
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
            //设置允许输入
            connection.setDoInput(true);
            //设置请求的超时时间
            connection.setReadTimeout(TIME_OUT);
            connection.setConnectTimeout(TIME_OUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
