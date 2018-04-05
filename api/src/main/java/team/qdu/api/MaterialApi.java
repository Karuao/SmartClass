package team.qdu.api;

import java.util.List;

import team.qdu.model.ApiResponse;
import team.qdu.model.Material;
import team.qdu.model.Material_User;

/**
 * Created by n551 on 2018/3/16.
 */

public interface MaterialApi {
    public ApiResponse<List<Material>> getTeaMaterial(String classid);
    public ApiResponse<List<Material_User>> getStuMaterial(String classid,String userid);
    public ApiResponse<Void> deleteMaterial(String materialid);
    public ApiResponse<Void> downloadMaterial(String classid,String userid,String name,String material_user_id);
}
