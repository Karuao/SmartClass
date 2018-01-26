package team.qdu.smartclass.fragment;

/**
 * 班课界面
 * Created by rjmgc on 2017/12/11.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.Class;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.ClassMainActivity;
import team.qdu.smartclass.activity.MainActivity;
import team.qdu.smartclass.adapter.ClassAdapter;


public class MainClassFragment extends SBaseFragment implements AdapterView.OnItemClickListener {

    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_class, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(new ClassAdapter(getActivity(), getData()));
        listView.setOnItemClickListener(this);
        return view;
    }

    //获取ListView中一行的示例数据
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "G1");
        map.put("info", "google 1");
        map.put("teacher", "teacher 1");
        map.put("img", R.drawable.add_class_img);
        list.add(map);
        return list;
    }

    //获取登录用户加入的班课列表
    private List<Map<String, Object>> getJoinedClasses() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        MainActivity parentActivity = (MainActivity) getActivity();
        parentActivity.classAppAction.getJoinedClasses(getUserId(), new ActionCallbackListener<List<Class>>() {
            @Override
            public void onSuccess(List<Class> data, String message) {

            }

            @Override
            public void onFailure(String errorEvent, String message) {

            }
        });

        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(getContext(), ClassMainActivity.class));
    }

    //从SharedPreferences获取userId
    public String getUserId() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        return  sharedPreferences.getString("userId", null);
    }
}