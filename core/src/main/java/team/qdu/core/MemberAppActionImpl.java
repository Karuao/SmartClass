package team.qdu.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.util.List;

import team.qdu.api.MemberApi;
import team.qdu.api.MemberApiImpl;
import team.qdu.model.ApiResponse;
import team.qdu.model.Attendance;
import team.qdu.model.Attendance_user;
import team.qdu.model.ClassUser;

/**
 * Created by asus on 2018/3/7.
 */

public class MemberAppActionImpl implements MemberAppAction {

    private Context context;
    private MemberApi memberApi;

    public MemberAppActionImpl(Context context) {
        this.context = context;
        this.memberApi = new MemberApiImpl();
    }

    //获取加入班课成员列表
    @Override
    public void getClassMembers(final String classId, final ActionCallbackListener<List<ClassUser>> listener) {
        new AsyncTask<Void, Void, ApiResponse<List<ClassUser>>>() {

            @Override
            protected ApiResponse<List<ClassUser>> doInBackground(Void... params) {
                return memberApi.getClassMembers(classId);
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
    public void getBitmap(final String urlTail, final ActionCallbackListener<Bitmap> listener) {
        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                return memberApi.getBitmap(urlTail);
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

    //获取成员信息
    @Override
    public ClassUser getMemberInfo(final String classUserId, final ActionCallbackListener<ClassUser> listener) {
        //请求Api
        new AsyncTask<Void, Void, ApiResponse<ClassUser>>() {
            @Override
            protected ApiResponse<ClassUser> doInBackground(Void... params) {
                return memberApi.searchByClassUserId(classUserId);
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
        return null;
    }

    //获取签到信息
    @Override
    public void getAttendanceInfo(final String classId, final ActionCallbackListener<List<Attendance>> listener) {
        //请求Api
        new AsyncTask<Void, Void, ApiResponse<List<Attendance>>>() {
            @Override
            protected ApiResponse<List<Attendance>> doInBackground(Void... params) {
                return memberApi.getAttendanceInfo(classId);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<Attendance>> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObjList(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }

    //获取学生签到信息
    @Override
    public void getAttendanceUserInfo(final String attendanceId, final ActionCallbackListener<List<Attendance_user>> listener) {
        //请求Api
        new AsyncTask<Void, Void, ApiResponse<List<Attendance_user>>>() {
            @Override
            protected ApiResponse<List<Attendance_user>> doInBackground(Void... params) {
                return memberApi.getAttendanceUserInfo(attendanceId);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<Attendance_user>> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObjList(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }

    //学生获取签到历史
    @Override
    public void getStudentSignInHistory(final String userId,final String classId, final ActionCallbackListener<List<Attendance_user>> listener) {
        //请求Api
        new AsyncTask<Void, Void, ApiResponse<List<Attendance_user>>>() {
            @Override
            protected ApiResponse<List<Attendance_user>> doInBackground(Void... params) {
                return memberApi.getStudentSignInHistory(userId,classId);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<Attendance_user>> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObjList(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }

    //教师获取签到历史
    @Override
    public void getTeacherSignInHistory(final String classId, final ActionCallbackListener<List<Attendance>> listener) {
        //请求Api
        new AsyncTask<Void, Void, ApiResponse<List<Attendance>>>() {
            @Override
            protected ApiResponse<List<Attendance>> doInBackground(Void... params) {
                return memberApi.getTeacherSignInHistory(classId);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<Attendance>> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObjList(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }

    //移出班课
    @Override
    public void shiftClass(final String classUserId, final ActionCallbackListener<Void> listener) {
        //请求Api
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return memberApi.shiftClass(classUserId);
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

    //教师端设置学生为已签到
    @Override
    public void setStudentSignIn(final String attendanceUserId,final ActionCallbackListener<List<Attendance_user>> listener) {
        //请求Api
        new AsyncTask<Void, Void, ApiResponse<List<Attendance_user>>>() {
            @Override
            protected ApiResponse<List<Attendance_user>> doInBackground(Void... params) {
                return memberApi.setStudentSignIn(attendanceUserId);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<Attendance_user>> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObjList(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }

    //教师端设置学生为未签到
    @Override
    public void setStudentNotSignIn(final String attendanceUserId,final ActionCallbackListener<List<Attendance_user>> listener) {
        //请求Api
        new AsyncTask<Void, Void, ApiResponse<List<Attendance_user>>>() {
            @Override
            protected ApiResponse<List<Attendance_user>> doInBackground(Void... params) {
                return memberApi.setStudentNotSignIn(attendanceUserId);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<Attendance_user>> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObjList(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }

    //学生签到
    @Override
    public void beginSignInForStudent(final String userId,final String attendanceId,final String classUserId, final ActionCallbackListener<Attendance_user> listener) {
        //请求Api
        new AsyncTask<Void, Void, ApiResponse<Attendance_user>>() {
            @Override
            protected ApiResponse<Attendance_user> doInBackground(Void... params) {
                return memberApi.beginSignInForStudent(userId,attendanceId,classUserId);
            }

            @Override
            protected void onPostExecute(ApiResponse<Attendance_user> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObj(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }

    //教师端开始签到操作
    @Override
    public void beginSignInForTeacher(final String classId, final ActionCallbackListener<Attendance> listener) {
        //请求Api
        new AsyncTask<Void, Void, ApiResponse<Attendance>>() {
            @Override
            protected ApiResponse<Attendance> doInBackground(Void... params) {
                return memberApi.beginSignInForTeacher(classId);
            }

            @Override
            protected void onPostExecute(ApiResponse<Attendance> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObj(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }

    //教师放弃本次签到
    @Override
    public void giveUpSignIn(final String attendanceId, final ActionCallbackListener<Void> listener) {
        //请求Api
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return memberApi.giveUpSignIn(attendanceId);
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

    //教师结束本次签到
    @Override
    public void endSignIn(final String attendanceId, final ActionCallbackListener<Void> listener) {
        //请求Api
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return memberApi.endSignIn(attendanceId);
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
