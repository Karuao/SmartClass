package team.qdu.api.net;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 11602 on 2018/1/30.
 */

public class ImgCacheHttpEngine {

    private final static String TAG = "ImgCacheHttpEngine";
    //The development database
    private final static String SERVER_URL = "http://10.0.2.2/";
    //    private final static String SERVER_URL = "http://47.94.7.159/";
    //The official database
//    private final static String SERVER_URL = "http://140.143.134.146/";
    private final static String REQUEST_METHOD = "GET";
    private final static String ENCODE_TYPE = "UTF-8";
    private final static int TIME_OUT = 4000;

    private static ImgCacheHttpEngine instance = null;

    public static ImgCacheHttpEngine getInstance() {
        if (instance == null) {
            instance = new ImgCacheHttpEngine();
        }
        return instance;
    }

    public File cacheImg(String urlTail) throws IOException {
        //打印出请求
        Log.i(TAG, "request: " + urlTail);
        HttpURLConnection connection = getConnection(urlTail);
        connection.connect();
        if (connection.getResponseCode() == 200) {
            //获取相应的输入流对象
            InputStream inputStream = connection.getInputStream();
            //新建相应的文件
            String filePath = Environment.getExternalStorageDirectory() + File.separator + urlTail;
            new File(filePath.substring(0, filePath.lastIndexOf(File.separator))).mkdirs();
            File file = new File(Environment.getExternalStorageDirectory() + "/" + urlTail);
            //对应文件建立输出流
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            //新建缓存,用来存储,从网络读取数据,再写入文件
            byte[] buffer = new byte[10240];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {//当没有读到最后的时候
                fileOutputStream.write(buffer, 0, len);//将缓存中的存储的文件流秀娥问file文件
            }
            //释放资源
            fileOutputStream.close();
            inputStream.close();
            connection.disconnect();
            return file;
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
