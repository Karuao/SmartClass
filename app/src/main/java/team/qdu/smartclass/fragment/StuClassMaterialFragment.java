package team.qdu.smartclass.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.Material;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.SBaseActivity;
import team.qdu.smartclass.activity.StuClassMainActivity;
import team.qdu.smartclass.adapter.StuMaterialAdapter;

/**
 * Created by rjmgc on 2018/1/17.
 */

public class StuClassMaterialFragment extends SBaseFragment {
    ListView listview;
    StuClassMainActivity parentActivity;
    //刷新标志
    public static boolean refreshFlag;
    private View currentPage;
    //标题栏班课名
    private TextView titleBarClassNameTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentPage = inflater.inflate(R.layout.class_tab01, container, false);
        parentActivity = (StuClassMainActivity) getActivity();
        listview = (ListView) currentPage.findViewById(R.id.class_material_listView);
        refreshFlag = false;
        initView();
        getMaterial();
        return currentPage;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (refreshFlag) {
            getMaterial();
            refreshFlag = false;
        }
    }

    public void initView() {
        titleBarClassNameTxt = (TextView) currentPage.findViewById(R.id.txt_titlebar_classname);
        titleBarClassNameTxt.setText(((SBaseActivity)getActivity()).getCourse());
    }
    private void getMaterial() {
        String classid = getClassId();
        parentActivity.materialAppAction.getStuMaterial(classid,new ActionCallbackListener<List<Material>>() {

            @Override
            public void onSuccess(List<Material> data, String message) {
                listview.setAdapter(new StuMaterialAdapter(getActivity(), data));
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}