package team.qdu.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.util.List;

import team.qdu.api.MemberApi;
import team.qdu.api.MemberApiImpl;
import team.qdu.model.ApiResponse;
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

}
