package team.qdu.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.File;
import java.util.List;

import team.qdu.api.ClassApi;
import team.qdu.api.ClassApiImpl;
import team.qdu.model.ApiResponse;
import team.qdu.model.Class;

/**
 * Created by 11602 on 2018/1/24.
 */

public class ClassAppActionImpl implements ClassAppAction {

    private Context context;
    private ClassApi classApi;

    public ClassAppActionImpl(Context context) {
        this.context = context;
        this.classApi = new ClassApiImpl();
    }

    //获取登录用户加入的班课列表
    @Override
    public void getJoinedClasses(final String userId, final ActionCallbackListener<List<Class>> listener) {
        new AsyncTask<Void, Void, ApiResponse<List<Class>>>() {

            @Override
            protected ApiResponse<List<Class>> doInBackground(Void... params) {
                return classApi.getJoinedClasses(userId);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<Class>> response) {
                    if (response.isSuccess()) {
                        listener.onSuccess(response.getObjList(), response.getMsg());
                    } else {
                        listener.onFailure(response.getEvent(), response.getMsg());
                    }
            }
        }.execute();
    }

    @Override
    public void jumpClass(final String classId, final String userId, final ActionCallbackListener<Void> listener) {
        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return classApi.jumpClass(classId, userId);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(null, response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }

    @Override
    public void getBitmap(final String urlTail, final ActionCallbackListener<Bitmap> listener) {
        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                return classApi.getBitmap(urlTail);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    listener.onSuccess(bitmap, "success");
                } else {
                    listener.onFailure(null, "fail");
                }
            }
        }.execute();
    }

    @Override
    public void createClass(final File avatar, final String name, final String course, final String userId, final ActionCallbackListener<String> listener) {
        new AsyncTask<Void, Void, ApiResponse<String>>() {

            @Override
            protected ApiResponse<String> doInBackground(Void... params) {
                return classApi.createClass(avatar, name, course, userId);
            }

            @Override
            protected void onPostExecute(ApiResponse<String> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObj(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }
}
