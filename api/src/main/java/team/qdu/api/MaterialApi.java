package team.qdu.api;

import java.util.List;

import team.qdu.model.ApiResponse;
import team.qdu.model.Material;

/**
 * Created by n551 on 2018/3/16.
 */

public interface MaterialApi {
    public ApiResponse<List<Material>> getTeaMaterial(String classid);
    public ApiResponse<List<Material>> getStuMaterial(String classid);
}
