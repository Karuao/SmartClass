package team.qdu.api.net;

import android.util.Log;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by 11602 on 2018/1/30.
 */

public class FileHttpEngine {

    private final static String TAG = "FileHttpEngine";
    private final static String SERVER_URL = "http://10.0.2.2:8080";
    private final static String REQUEST_METHOD = "POST";
    private final static String ENCODE_TYPE = "UTF-8";
    private final static int TIME_OUT = 8000;
    private final static String BOUNDARY = java.util.UUID.randomUUID().toString();
    private final static String PREFIX = "--";
    private final static String LINEND = "\r\n";
    private final static String MULTIPART_FROM_DATA = "multipart/form-data";

    private static FileHttpEngine instance = null;

    public static FileHttpEngine getInstance() {
        if (instance == null) {
            instance = new FileHttpEngine();
        }
        return instance;
    }

    /**
     * 通过拼接的方式构造请求内容，实现参数传输以及文件传输
     *
     * @param urlTail Service net address
     * @param params  text content
     * @param files   pictures
     * @return String result of Service response
     * @throws IOException
     */
    public <T> T postHandle(Map<String, String> params, Map<String, File> files, Type typeofT, String urlTail)
            throws IOException {
        HttpURLConnection connection = getConnection(urlTail);
        // 首先组拼文本类型的参数
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINEND);
            sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
            sb.append("Content-Type: text/plain; charset=" + ENCODE_TYPE + LINEND);
            sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
            sb.append(LINEND);
            sb.append(entry.getValue());
            sb.append(LINEND);
        }
        DataOutputStream outStream = new DataOutputStream(connection.getOutputStream());
        outStream.write(sb.toString().getBytes());
        // 发送文件数据
        if (files != null)
            for (Map.Entry<String, File> file : files.entrySet()) {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(PREFIX);
                sb1.append(BOUNDARY);
                sb1.append(LINEND);
                sb1.append("Content-Disposition: form-data; name=\"uploadfile\"; filename=\""
                        + file.getValue().getName() + "\"" + LINEND);
                sb1.append("Content-Type: application/octet-stream; charset=" + ENCODE_TYPE + LINEND);
                sb1.append(LINEND);
                outStream.write(sb1.toString().getBytes());
                InputStream is = new FileInputStream(file.getValue());
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                is.close();
                outStream.write(LINEND.getBytes());
            }
        // 请求结束标志
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
        outStream.write(end_data);
        outStream.flush();
        // 得到响应码
//        int res = connection.getResponseCode();
//        InputStream in = connection.getInputStream();
//        StringBuilder sb2 = new StringBuilder();
//        if (res == 200) {
//            int ch;
//            while ((ch = in.read()) != -1) {
//                sb2.append((char) ch);
//            }
//        }
//        outStream.close();
//        connection.disconnect();
//        //返回字符串
//        final String result = sb2.toString();
//        //打印出结果
//        Log.i(TAG, "response:" + result);
//        Gson gson = new Gson();
//        return gson.fromJson(result, typeofT);
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
            Gson gson = new Gson();
            return gson.fromJson(result, typeofT);
        } else {
            connection.disconnect();
            return null;
        }
    }

    private HttpURLConnection getConnection(String urlTail) {
        HttpURLConnection conn = null;
        //初始化connection
        try {
            URL url = new URL(SERVER_URL + urlTail);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(TIME_OUT);
            conn.setReadTimeout(TIME_OUT); // 缓存的最长时间
            conn.setDoInput(true);// 允许输入
            conn.setDoOutput(true);// 允许输出
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod(REQUEST_METHOD);
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
