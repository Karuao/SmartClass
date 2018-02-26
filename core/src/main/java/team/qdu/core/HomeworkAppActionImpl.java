package team.qdu.core;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.File;
import java.util.List;

import team.qdu.api.HomeworkApi;
import team.qdu.api.HomeworkApiImpl;
import team.qdu.model.ApiResponse;
import team.qdu.model.Homework;

/**
 * Created by 11602 on 2018/2/8.
 */

public class HomeworkAppActionImpl implements HomeworkAppAction {

    private Context context;
    private HomeworkApi homeworkApi;

    public HomeworkAppActionImpl(Context context) {
        this.context = context;
        this.homeworkApi = new HomeworkApiImpl();
    }

    @Override
    public void pushHomework(final String title, final String deadline, final String detail,
                             final File photo, final String classId, final ActionCallbackListener<Void> listener) {
        if (TextUtils.isEmpty(title)) {
            listener.onFailure(ErrorEvent.PARAM_NULL, "标题不能为空");
            return;
        }
        if (TextUtils.isEmpty(detail) && photo == null) {
            listener.onFailure(ErrorEvent.PARAM_NULL, "截止日期和上传图片不能全为空");
            return;
        }
        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return homeworkApi.publishHomework(title, deadline, detail, photo, classId);
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
    public void getHomeworkList(final String classId, final String userId, final String userTitle, final String requestStatus, final ActionCallbackListener<List<Homework>> listener) {
        new AsyncTask<Void, Void, ApiResponse<List<Homework>>>() {

            @Override
            protected ApiResponse<List<Homework>> doInBackground(Void... params) {
                return homeworkApi.getHomeworkList(classId, userId, userTitle, requestStatus);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<Homework>> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObjList(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }

    @Override
    public void changeHomeworkStatus(final String homeworkId, final String homeworkStatus, final ActionCallbackListener<Void> listener) {
        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return homeworkApi.changeHomeworkStatus(homeworkId, homeworkStatus);
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
}
