package team.qdu.core;

import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import team.qdu.api.FileApi;
import team.qdu.api.FileApiImpl;
import team.qdu.api.util.FileUtil;

/**
 * Created by 11602 on 2018/3/16.
 */

public class FileAppActionImpl implements FileAppAction {

    private Context context;
    private FileApi fileApi;

    public FileAppActionImpl(Context context) {
        this.context = context;
        this.fileApi = new FileApiImpl();
    }

    @Override
    public void cacheImg(final String urlTail, final Context context, final ActionCallbackListener<File> listener) {
        new AsyncTask<Void, Void, File>() {

            @Override
            protected File doInBackground(Void... params) {
                if (!((Lifeful) context).isAlive()) {
                    cancel(true);
                    return null;
                }
                return fileApi.cacheFile(urlTail, context.getExternalCacheDir() + File.separator + urlTail);
            }

            @Override
            protected void onPostExecute(File img) {
                if (!((Lifeful) context).isAlive()) {
                    return;
                }
                if (img != null) {
                    listener.onSuccess(img, "图片缓存成功");
                } else {
                    listener.onFailure(null, "图片缓存失败");
                }
            }
        }.execute();
    }

    @Override
    public void cacheFile(final String urlTail, final Context context, final ActionCallbackListener<File> listener) {
        new AsyncTask<Void, Void, File>() {

            @Override
            protected File doInBackground(Void... params) {
                if (!((Lifeful) context).isAlive()) {
                    cancel(true);
                    return null;
                }
                return fileApi.cacheFile(urlTail, context.getExternalFilesDir(null) + File.separator + urlTail);
            }

            @Override
            protected void onPostExecute(File img) {
                if (!((Lifeful) context).isAlive()) {
                    return;
                }
                if (img != null) {
                    listener.onSuccess(img, "文件下载成功");
                } else {
                    listener.onFailure(null, "文件下载失败");
                }
            }
        }.execute();
    }

    @Override
    public void getVersionInfo(final ActionCallbackListener<Map> listener) {
        new AsyncTask<Void, Void, Map>() {
            @Override
            protected Map doInBackground(Void... params) {
                return fileApi.getVersionInfo();
            }

            @Override
            protected void onPostExecute(Map response) {
                if (response != null) {
                    listener.onSuccess(response, null);
                } else {
                    listener.onFailure(null, "更新失败");
                }
            }
        }.execute();
    }

    @Override
    public void downloadApp(final String urlTail, final Context context, final ActionCallbackListener<Object> listener) {
        final String localPath = context.getExternalFilesDir(null) + File.separator + urlTail;
        new AsyncTask<Void, Integer, File>() {

            private PowerManager.WakeLock mWakeLock;

            private final static String TAG = "ApkDownloadHttpEngine";
            //The development database
            //    private final static String SERVER_URL = "http://10.0.2.2/";
//            private final static String SERVER_URL = "http://47.94.7.159/";
            //The official database
            private final static String SERVER_URL = "http://140.143.134.146/";
            private final static String REQUEST_METHOD = "GET";
            private final static String ENCODE_TYPE = "UTF-8";
            private final static int TIME_OUT = 4000;

            @Override
            protected File doInBackground(Void... params) {
                try {
                    return cacheFile(urlTail, localPath);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // take CPU lock to prevent CPU from going off if the user
                // presses the power button during download
                PowerManager pm = (PowerManager) context
                        .getSystemService(Context.POWER_SERVICE);
                mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                        getClass().getName());
                mWakeLock.acquire();
            }

            @Override
            protected void onProgressUpdate(Integer... progress) {
                super.onProgressUpdate(progress);
                // if we get here, length is known, now set indeterminate to false
                listener.onSuccess(progress[0], "update");
            }

            @Override
            protected void onPostExecute(File file) {
                mWakeLock.release();
                if (file != null) {
                    listener.onSuccess(file, null);
                } else {
                    listener.onFailure(null, "软件更新失败");
                }
            }

            public File cacheFile(String urlTail, String localPath) throws IOException {
                File file = new File(localPath);
                //打印出请求z
                Log.i(TAG, "request: " + urlTail);
                HttpURLConnection connection = getConnection(urlTail);
                connection.connect();
                if (connection.getResponseCode() == 200) {
                    //获取相应的输入流对象
                    InputStream inputStream = connection.getInputStream();
                    //获取文件长度
                    int fileLength = connection.getContentLength();
                    //新建相应的文件夹
                    File directory = new File(localPath.substring(0, localPath.lastIndexOf(File.separator)));
                    FileUtil.deleteDir(directory);
                    directory.mkdirs();
                    //对应文件建立输出流
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    //新建缓存,用来存储,从网络读取数据,再写入文件
                    byte[] buffer = new byte[10240];
                    int len = 0;
                    long readedLength = 0;
                    while ((len = inputStream.read(buffer)) != -1) {//当没有读到最后的时候
                        fileOutputStream.write(buffer, 0, len);//将缓存中的存储的文件流秀娥问file文件
                        readedLength += len;
                        if (fileLength > 0) // only if total length is known
                            publishProgress((int) (readedLength * 100 / fileLength));
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
        }.execute();
    }
}
