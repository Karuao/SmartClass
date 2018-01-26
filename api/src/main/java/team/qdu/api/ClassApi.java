package team.qdu.api;

import java.util.List;

import team.qdu.model.ApiResponse;
import team.qdu.model.Class;

/**
 * Created by 11602 on 2018/1/24.
 */

public interface ClassApi {

    public ApiResponse<List<Class>> getJoinedClasses(String userId);
}
