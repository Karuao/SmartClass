package team.qdu.core;

import java.util.List;

import team.qdu.model.Material;

/**
 * Created by n551 on 2018/3/16.
 */

public interface MaterialAppAction {
    public void getTeaMaterial (String classid,ActionCallbackListener<List<Material>> listener);
    public void getStuMaterial (String classid,ActionCallbackListener<List<Material>> listener);

}
