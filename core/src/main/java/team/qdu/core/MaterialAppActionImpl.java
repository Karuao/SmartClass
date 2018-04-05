package team.qdu.core;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import team.qdu.api.MaterialApi;
import team.qdu.api.MaterialApiImpl;
import team.qdu.model.ApiResponse;
import team.qdu.model.Material;
import team.qdu.model.Material_User;

/**
 * Created by n551 on 2018/3/16.
 */

public class MaterialAppActionImpl implements MaterialAppAction {
    private Context context;
    private MaterialApi materialApi;

    public MaterialAppActionImpl(Context context) {
        this.context = context;
        this.materialApi = new MaterialApiImpl();
    }

    @Override
    public void getTeaMaterial(final String classid, final ActionCallbackListener<List<Material>> listener) {
        new AsyncTask<Void, Void, ApiResponse<List<Material>>>() {

            @Override
            protected ApiResponse<List<Material>> doInBackground(Void... params) {
                return materialApi.getTeaMaterial(classid);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<Material>> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObjList(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }
    @Override
    public void getStuMaterial(final String classid,final String userid,  final ActionCallbackListener<List<Material_User>> listener) {
        new AsyncTask<Void, Void, ApiResponse<List<Material_User>>>() {

            @Override
            protected ApiResponse<List<Material_User>> doInBackground(Void... params) {
                return materialApi.getStuMaterial(classid,userid);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<Material_User>> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObjList(), response.getMsg());
                } else {
                    listener.onFailure(response.getEvent(), response.getMsg());
                }
            }
        }.execute();
    }
    @Override
    public void deleteMaterial(final String materialid, final ActionCallbackListener<Void> listener) {
        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return materialApi.deleteMaterial(materialid);
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
    public void downloadMaterial(final String classid,final String userid,final String name, final String material_user_id, final ActionCallbackListener<Void> listener) {
        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return materialApi.downloadMaterial(classid,userid,name,material_user_id);
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
