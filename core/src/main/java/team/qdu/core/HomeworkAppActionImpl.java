package team.qdu.core;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void publishHomework(final String title, final String deadline, final String detail,
                                final List<File> photoList, final String classId, final Lifeful lifeful, final ActionCallbackListener<Void> listener) {
        if (TextUtils.isEmpty(title)) {
            listener.onFailure(ErrorEvent.PARAM_NULL, "标题不能为空");
            return;
        }
        if (TextUtils.isEmpty(detail) && photoList.size() == 0) {
            listener.onFailure(ErrorEvent.PARAM_NULL, "作业内容和上传图片不能全为空");
            return;
        }
        //截至日期不能小于等于当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date deadlineDate = new Date();
        Date currentDate = new Date();
        try {
            deadlineDate = sdf.parse(deadline);
        } catch (ParseException e) {
            e.printStackTrace();
            listener.onFailure(ErrorEvent.PROGRESS_EXCEPTION, "发布作业失败，请稍后再试");
        }
        if ((currentDate.getTime() - currentDate.getTime() % 60) >= deadlineDate.getTime()) {
            listener.onFailure(ErrorEvent.PARAM_ILLEGAL, "截至日期不能小于等于当前时间");
            return;
        }
        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                if (!lifeful.isAlive()) {
                    cancel(true);
                    return null;
                }
                return homeworkApi.publishHomework(title, deadline, detail, photoList, classId);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (!lifeful.isAlive()) {
                    return;
                }
                if (response.isSuccess()) {
                    listener.onSuccess(null, response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }

    @Override
    public void getHomeworkList(final String classId, final String userId, final String userTitle, final String requestStatus, final Lifeful lifeful, final ActionCallbackListener<List> listener) {
        new AsyncTask<Void, Void, ApiResponse<List>>() {

            @Override
            protected ApiResponse<List> doInBackground(Void... params) {
                if (!lifeful.isAlive()) {
                    cancel(true);
                    return null;
                }
                return homeworkApi.getHomeworkList(classId, userId, userTitle, requestStatus);
            }

            @Override
            protected void onPostExecute(ApiResponse<List> response) {
                if (!lifeful.isAlive()) {
                    return;
                }
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObjList(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }

    @Override
    public void changeHomeworkStatus(final String homeworkId, final String homeworkStatus, final String homeworkTitle, final Lifeful lifeful, final ActionCallbackListener<Void> listener) {
        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                if (!lifeful.isAlive()) {
                    cancel(true);
                    return null;
                }
                return homeworkApi.changeHomeworkStatus(homeworkId, homeworkStatus, homeworkTitle);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (!lifeful.isAlive()) {
                    return;
                }
                if (response.isSuccess()) {
                    listener.onSuccess(null, response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }

    @Override
    public void getStuHomeworkDetail(final String homeworkAnswerId, final Lifeful lifeful, final ActionCallbackListener<HomeworkAnswerWithBLOBs> listener) {
        new AsyncTask<Void, Void, ApiResponse<HomeworkAnswerWithBLOBs>>() {

            @Override
            protected ApiResponse<HomeworkAnswerWithBLOBs> doInBackground(Void... params) {
                if (!lifeful.isAlive()) {
                    cancel(true);
                    return null;
                }
                return homeworkApi.getStuHomeworkDetail(homeworkAnswerId);
            }

            @Override
            protected void onPostExecute(ApiResponse<HomeworkAnswerWithBLOBs> response) {
                if (!lifeful.isAlive()) {
                    return;
                }
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObj(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }


    @Override
    public void commitHomework(final String homeworkAnswerId, final String homeworkId, final String classId, final String userId, final String ifSubmit, final String homeworkTitle, final String detail, final List<File> photoList, final String delPhotoesUrl, final Lifeful lifeful, final ActionCallbackListener<Void> listener) {
        if (TextUtils.isEmpty(detail) && photoList.size() == 0) {
            listener.onFailure(ErrorEvent.PARAM_NULL, "提交的文字和图片不能全为空");
            return;
        }

        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                if (!lifeful.isAlive()) {
                    cancel(true);
                    return null;
                }
                return homeworkApi.commitHomework(homeworkAnswerId, homeworkId, classId, userId, ifSubmit, homeworkTitle, detail, photoList, delPhotoesUrl);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (!lifeful.isAlive()) {
                    return;
                }
                if (response.isSuccess()) {
                    listener.onSuccess(null, response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }

    @Override
    public void getHomeworkDetail(final String homeworkId, final Lifeful lifeful, final ActionCallbackListener<HomeworkWithBLOBs> listener) {
        new AsyncTask<Void, Void, ApiResponse<HomeworkWithBLOBs>>() {

            @Override
            protected ApiResponse<HomeworkWithBLOBs> doInBackground(Void... params) {
                if (!lifeful.isAlive()) {
                    cancel(true);
                    return null;
                }
                return homeworkApi.getHomeworkDetail(homeworkId);
            }

            @Override
            protected void onPostExecute(ApiResponse<HomeworkWithBLOBs> response) {
                if (!lifeful.isAlive()) {
                    return;
                }
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObj(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }

    @Override
    public void getHomeworkAnswerList(final String homeworkId, final Lifeful lifeful, final ActionCallbackListener<List<HomeworkAnswerWithBLOBs>> listener) {
        new AsyncTask<Void, Void, ApiResponse<List<HomeworkAnswerWithBLOBs>>>() {

            @Override
            protected ApiResponse<List<HomeworkAnswerWithBLOBs>> doInBackground(Void... params) {
                if (!lifeful.isAlive()) {
                    cancel(true);
                    return null;
                }
                return homeworkApi.getHomeworkAnswerList(homeworkId);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<HomeworkAnswerWithBLOBs>> response) {
                if (!lifeful.isAlive()) {
                    return;
                }
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObjList(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }

    @Override
    public void commitHomeworkEvaluation(final String homeworkAnswerId, final String exp, final String remark, final List<File> photoList, final String delPhotoesUrl, final Lifeful lifeful, final ActionCallbackListener<Void> listener) {
        if (TextUtils.isEmpty(exp) || Integer.parseInt(exp) < 0 || Integer.parseInt(exp) > 10) {
            listener.onFailure(ErrorEvent.PARAM_ILLEGAL, "评分应在0-10分之间");
            return;
        }
        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                if (!lifeful.isAlive()) {
                    cancel(true);
                    return null;
                }
                return homeworkApi.commitHomeworkEvaluation(homeworkAnswerId, exp, remark, photoList, delPhotoesUrl);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (!lifeful.isAlive()) {
                    return;
                }
                if (response.isSuccess()) {
                    listener.onSuccess(null, response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }

    @Override
    public void getNotEvaluateStuNum(final String homeworkId, final Lifeful lifeful, final ActionCallbackListener<Integer> listener) {
        new AsyncTask<Void, Void, ApiResponse<Integer>>() {

            @Override
            protected ApiResponse<Integer> doInBackground(Void... params) {
                if (!lifeful.isAlive()) {
                    cancel(true);
                    return null;
                }
                return homeworkApi.getNotEvaluateStuNum(homeworkId);
            }

            @Override
            protected void onPostExecute(ApiResponse<Integer> response) {
                if (!lifeful.isAlive()) {
                    return;
                }
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObj(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }
}
