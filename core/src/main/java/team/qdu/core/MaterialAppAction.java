package team.qdu.core;

import java.util.List;

import team.qdu.model.Material;
import team.qdu.model.Material_User;

/**
 * Created by n551 on 2018/3/16.
 */

public interface MaterialAppAction {
    public void getTeaMaterial (String classid,ActionCallbackListener<List<Material>> listener);
    public void getStuMaterial (String classid,String userid,ActionCallbackListener<List<Material_User>> listener);
    public void deleteMaterial(String materialid,ActionCallbackListener<Void> listener);
    public void downloadMaterial(String classid,String userid,String name,String material_user_id,ActionCallbackListener<Void> listener);

}
