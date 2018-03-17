package team.qdu.core;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import team.qdu.api.MaterialApi;
import team.qdu.api.MaterialApiImpl;
import team.qdu.model.ApiResponse;
import team.qdu.model.Material;

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
    public void getStuMaterial(final String classid, final ActionCallbackListener<List<Material>> listener) {
        new AsyncTask<Void, Void, ApiResponse<List<Material>>>() {

            @Override
            protected ApiResponse<List<Material>> doInBackground(Void... params) {
                return materialApi.getStuMaterial(classid);
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
}
