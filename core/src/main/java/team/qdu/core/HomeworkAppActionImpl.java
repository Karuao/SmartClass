package team.qdu.core;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.File;
import java.util.List;

import team.qdu.api.HomeworkApi;
import team.qdu.api.HomeworkApiImpl;
import team.qdu.model.ApiResponse;
import team.qdu.model.HomeworkAnswerWithBLOBs;
import team.qdu.model.HomeworkWithBLOBs;

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
    public void getHomeworkList(final String classId, final String userId, final String userTitle, final String requestStatus, final ActionCallbackListener<List> listener) {
        new AsyncTask<Void, Void, ApiResponse<List>>() {

            @Override
            protected ApiResponse<List> doInBackground(Void... params) {
                return homeworkApi.getHomeworkList(classId, userId, userTitle, requestStatus);
            }

            @Override
            protected void onPostExecute(ApiResponse<List> response) {
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

    @Override
    public void getStuHomeworkDetail(final String homeworkAnswerId, final ActionCallbackListener<HomeworkAnswerWithBLOBs> listener) {
        new AsyncTask<Void, Void, ApiResponse<HomeworkAnswerWithBLOBs>>() {

            @Override
            protected ApiResponse<HomeworkAnswerWithBLOBs> doInBackground(Void... params) {
                return homeworkApi.getStuHomeworkDetail(homeworkAnswerId);
            }

            @Override
            protected void onPostExecute(ApiResponse<HomeworkAnswerWithBLOBs> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObj(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }

    @Override
    public void commitHomework(final String homeworkAnswerId, final String detail, final File answerPhoto, final ActionCallbackListener<Void> listener) {
        if (TextUtils.isEmpty(detail) && answerPhoto == null) {
            listener.onFailure(ErrorEvent.PARAM_NULL, "提交的文字和图片不能全为空");
            return;
        }

        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return homeworkApi.commitHomework(homeworkAnswerId, detail, answerPhoto);
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
    public void getHomeworkDetail(final String homeworkId, final ActionCallbackListener<HomeworkWithBLOBs> listener) {
        new AsyncTask<Void, Void, ApiResponse<HomeworkWithBLOBs>>() {

            @Override
            protected ApiResponse<HomeworkWithBLOBs> doInBackground(Void... params) {
                return homeworkApi.getHomeworkDetail(homeworkId);
            }

            @Override
            protected void onPostExecute(ApiResponse<HomeworkWithBLOBs> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObj(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }

    @Override
    public void getHomeworkAnswerList(final String homeworkId, final ActionCallbackListener<List<HomeworkAnswerWithBLOBs>> listener) {
        new AsyncTask<Void, Void, ApiResponse<List<HomeworkAnswerWithBLOBs>>>() {

            @Override
            protected ApiResponse<List<HomeworkAnswerWithBLOBs>> doInBackground(Void... params) {
                return homeworkApi.getHomeworkAnswerList(homeworkId);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<HomeworkAnswerWithBLOBs>> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObjList(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }

    @Override
    public void commitHomeworkEvaluation(final String homeworkAnswerId, final String exp, final String remark, final File evaluatePhoto, final ActionCallbackListener<Void> listener) {
        if (TextUtils.isEmpty(exp) || Integer.parseInt(exp) < 0 || Integer.parseInt(exp) > 10) {
            listener.onFailure(ErrorEvent.PARAM_ILLEGAL, "评分应在0-10分之间");
            return;
        }
        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return homeworkApi.commitHomeworkEvaluation(homeworkAnswerId, exp, remark, evaluatePhoto);
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
    public void getNotEvaluateStuNum(final String homeworkId, final ActionCallbackListener<Integer> listener) {
        new AsyncTask<Void, Void, ApiResponse<Integer>>() {

            @Override
            protected ApiResponse<Integer> doInBackground(Void... params) {
                return homeworkApi.getNotEvaluateStuNum(homeworkId);
            }

            @Override
            protected void onPostExecute(ApiResponse<Integer> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObj(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }
}
