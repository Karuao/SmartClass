package team.qdu.core;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;

import team.qdu.api.ImgApi;
import team.qdu.api.ImgApiImpl;

/**
 * Created by 11602 on 2018/3/16.
 */

public class ImgAppActionImpl implements ImgAppAction {

    private Context context;
    private ImgApi imgApi;

    public ImgAppActionImpl(Context context) {
        this.context = context;
        this.imgApi = new ImgApiImpl();
    }

    @Override
    public void cacheImg(final String urlTail, final ActionCallbackListener<File> listener) {
        new AsyncTask<Void, Void, File>() {

            @Override
            protected File doInBackground(Void... params) {
                return imgApi.cacheImg(urlTail);
            }

            @Override
            protected void onPostExecute(File img) {
                if (img != null) {
                    listener.onSuccess(img, "图片缓存成功");
                } else {
                    listener.onFailure(null, "图片缓存失败");
                }
            }
        }.execute();
    }
}
