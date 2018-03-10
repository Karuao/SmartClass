package team.qdu.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.File;
import java.util.List;

import team.qdu.api.ClassApi;
import team.qdu.api.ClassApiImpl;
import team.qdu.model.ApiResponse;
import team.qdu.model.Class;
import team.qdu.model.ClassUser;

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
    public void getJoinedClasses(final String userId, final ActionCallbackListener<List<ClassUser>> listener) {
        new AsyncTask<Void, Void, ApiResponse<List<ClassUser>>>() {

            @Override
            protected ApiResponse<List<ClassUser>> doInBackground(Void... params) {
                return classApi.getJoinedClasses(userId);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<ClassUser>> response) {
                    if (response.isSuccess()) {
                        listener.onSuccess(response.getObjList(), response.getMsg());
                    } else {
                        listener.onFailure(response.getEvent(), response.getMsg());
                    }
            }
        }.execute();
    }

    @Override
    public void jumpClass(final String classId, final String userId, final ActionCallbackListener<ClassUser> listener) {
        new AsyncTask<Void, Void, ApiResponse<ClassUser>>() {

            @Override
            protected ApiResponse<ClassUser> doInBackground(Void... params) {
                return classApi.jumpClass(classId, userId);
            }

            @Override
            protected void onPostExecute(ApiResponse<ClassUser> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObj(), response.getMsg());
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
                    listener.onSuccess(bitmap, "图片获取成功");
                } else {
                    listener.onFailure(null, "图片获取失败");
                }
            }
        }.execute();
    }

    @Override
    public void notAllowToJoin(final String classId,final ActionCallbackListener<Void> listener){
        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return classApi.notAllowToJoin(classId);
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
    public void allowToJoin(final String classId,final ActionCallbackListener<Void> listener){
        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return classApi.allowToJoin(classId);
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
    public Class getClassInfor(final String classId, final ActionCallbackListener<Class> listener) {
        //请求Api
        new AsyncTask<Void, Void, ApiResponse<Class>>() {
            @Override
            protected ApiResponse<Class> doInBackground(Void... params) {
                return classApi.searchByClassId(classId);
            }

            @Override
            protected void onPostExecute(ApiResponse<Class> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObj(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
        return null;
    }

    @Override
    public void finishClass(final String classId,final ActionCallbackListener<Void> listener){
        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return classApi.finishClass(classId);
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
    public void deleteClass(final String classId,final ActionCallbackListener<Void> listener){
        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return classApi.deleteClass(classId);
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
    public void createClass(final File avatar, final String name, final String course, final String userId, final ActionCallbackListener<String> listener) {
        if (TextUtils.isEmpty(name)) {
            listener.onFailure(ErrorEvent.PARAM_NULL, "班级不能为空");
            return;
        }
        if (TextUtils.isEmpty(course)) {
            listener.onFailure(ErrorEvent.PARAM_NULL, "课程不能为空");
            return;
        }
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

    @Override
    public void compileClass(final String classId,final File avatar,final String className,final String course,final String university,final String department,final String goal,final String exam, final ActionCallbackListener<String> listener){
        if (TextUtils.isEmpty(className)) {
            listener.onFailure(ErrorEvent.PARAM_NULL, "班级不能为空");
            return;
        }
        if (TextUtils.isEmpty(course)) {
            listener.onFailure(ErrorEvent.PARAM_NULL, "课程不能为空");
            return;
        }
        new AsyncTask<Void, Void, ApiResponse<String>>() {

            @Override
            protected ApiResponse<String> doInBackground(Void... params) {
                return classApi.modifyClass(classId,avatar,className,course,university,department,goal,exam);
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

    @Override
    public void joinClass(final String classId, final String userId, final ActionCallbackListener<Class> listener) {
        if (TextUtils.isEmpty(classId) || classId.length() < 6) {
            listener.onFailure(ErrorEvent.PARAM_ILLEGAL, "邀请码长度大于6位");
            return;
        }
        new AsyncTask<Void, Void, ApiResponse<Class>>() {

            @Override
            protected ApiResponse<Class> doInBackground(Void... params) {
                return classApi.joinClass(classId, userId);
            }

            @Override
            protected void onPostExecute(ApiResponse<Class> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObj(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }

    @Override
    public void confirmJoinClass(final String classId, final String userId, final ActionCallbackListener<Void> listener) {
        if (TextUtils.isEmpty(classId) || classId.length() < 6) {
            listener.onFailure(ErrorEvent.PARAM_ILLEGAL, "邀请码长度大于6位");
            return;
        }
        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return classApi.confirmJoinClass(classId, userId);
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

    public void quitClass(final String classId,final String userId,final ActionCallbackListener<Void> listener){
        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return classApi.quitClass(classId, userId);
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
    public void readNew(final String classUserId, final String whichPage) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                return classApi.readNew(classUserId, whichPage);
            }
        }.execute();
    }
}
