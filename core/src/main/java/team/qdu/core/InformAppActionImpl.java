package team.qdu.core;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import team.qdu.api.InformApi;
import team.qdu.api.InformApiImpl;
import team.qdu.model.ApiResponse;
import team.qdu.model.Inform;
import team.qdu.model.Inform_User;
import team.qdu.model.User;

/**
 * Created by n551 on 2018/1/29.
 */

public class InformAppActionImpl implements InformAppAction {

    private Context context;
    private InformApi informApi;

    public InformAppActionImpl(Context context) {
        this.context = context;
        this.informApi = new InformApiImpl();
    }

    //获取当前班课的消息
    @Override
    public void getInform(final String classId, final Lifeful lifeful, final ActionCallbackListener<List<Inform>> listener) {
        new AsyncTask<Void, Void, ApiResponse<List<Inform>>>() {

            @Override
            protected ApiResponse<List<Inform>> doInBackground(Void... params) {
                if (!lifeful.isAlive()) {
                    cancel(true);
                    return null;
                }
                return informApi.getInform(classId);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<Inform>> response) {
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
    public void getUnreadNum(final String informid, final Lifeful lifeful, final ActionCallbackListener<Void> listener) {
        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                if (!lifeful.isAlive()) {
                    cancel(true);
                    return null;
                }
                return informApi.getUnreadNum(informid);
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
    public void getUserInform(final String classId, final String userId, final Lifeful lifeful, final ActionCallbackListener<List<Inform_User>> listener) {
        new AsyncTask<Void, Void, ApiResponse<List<Inform_User>>>() {

            @Override
            protected ApiResponse<List<Inform_User>> doInBackground(Void... params) {
                if (!lifeful.isAlive()) {
                    cancel(true);
                    return null;
                }
                return informApi.getUserInform(classId, userId);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<Inform_User>> response) {
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
    public void ClickInform(final String inform_user_id, final String classid, final String userid, final Lifeful lifeful, final ActionCallbackListener<Void> listener) {
        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                if (!lifeful.isAlive()) {
                    cancel(true);
                    return null;
                }
                return informApi.ClickInform(inform_user_id, classid, userid);
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
    public void deleteInform(final String inform_id, final Lifeful lifeful, final ActionCallbackListener<Void> listener) {
        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                if (!lifeful.isAlive()) {
                    cancel(true);
                    return null;
                }
                return informApi.deleteInform(inform_id);
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
    public void getReadPeople(final String inform_id, final Lifeful lifeful, final ActionCallbackListener<List<User>> listener) {
        new AsyncTask<Void, Void, ApiResponse<List<User>>>() {

            @Override
            protected ApiResponse<List<User>> doInBackground(Void... params) {
                if (!lifeful.isAlive()) {
                    cancel(true);
                    return null;
                }
                return informApi.getReadPeople(inform_id);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<User>> response) {
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
    public void getUnReadPeople(final String inform_id, final Lifeful lifeful, final ActionCallbackListener<List<User>> listener) {
        new AsyncTask<Void, Void, ApiResponse<List<User>>>() {

            @Override
            protected ApiResponse<List<User>> doInBackground(Void... params) {
                if (!lifeful.isAlive()) {
                    cancel(true);
                    return null;
                }
                return informApi.getUnReadPeople(inform_id);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<User>> response) {
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
    public void createInform(final String classid, final String detail, final Lifeful lifeful, final ActionCallbackListener<Void> listener) {
        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                if (!lifeful.isAlive()) {
                    cancel(true);
                    return null;
                }
                return informApi.createInform(classid, detail);
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
}
