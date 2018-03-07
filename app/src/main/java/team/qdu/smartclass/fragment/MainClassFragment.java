package team.qdu.smartclass.fragment;

/**
 * 班课界面
 * Created by rjmgc on 2017/12/11.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.Class;
import team.qdu.model.ClassUser;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.MainActivity;
import team.qdu.smartclass.activity.StuClassMainActivity;
import team.qdu.smartclass.activity.TeaClassMainActivity;
import team.qdu.smartclass.adapter.ClassAdapter;


public class MainClassFragment extends SBaseFragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private MainActivity parentActivity;
    TextView allow;
    CheckBox ifAllowTojoin;
    public static boolean refreshFlag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_class, container, false);
        parentActivity = (MainActivity) getActivity();
        listView = (ListView) view.findViewById(R.id.list_mainclass);
        allow = (TextView) view.findViewById(R.id.tv_join);
        ifAllowTojoin = (CheckBox) view.findViewById(R.id.chk_join);
        getJoinedClasses();
        listView.setOnItemClickListener(this);
        return view;
    }

    //页面从后台返回到前台运行
    @Override
    public void onResume() {
        super.onResume();
        if (refreshFlag) {
            getJoinedClasses();
            refreshFlag = false;
        }
    }

    //获取ListView中一行的示例数据
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "G1");
        map.put(" ", "google 1");
        map.put("teacher", "teacher 1");
        map.put("img", R.drawable.add_class_img);
        list.add(map);
        return list;
    }

    //获取登录用户加入的班课列表
    private void getJoinedClasses() {
        parentActivity.classAppAction.getJoinedClasses(getUserId(), new ActionCallbackListener<List<Class>>() {
            @Override
            public void onSuccess(List<Class> data, String message) {
                //将已结束班课放到List末端
                int size = data.size();
                for (int i = 0; i < size; ) {
                    if ("已结束".equals(data.get(i).getIf_allow_to_join())) {
                        data.add(data.remove(i));
                        size--;
                    } else {
                        i++;
                    }
                }
                listView.setAdapter(new ClassAdapter(getActivity(), data));
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final String classId = ((TextView) view.findViewById(R.id.txt_classId)).getText().toString();
        //跳转班课内部界面，根据classId和userId判断身份，跳转老师或学生界面
        parentActivity.classAppAction.jumpClass(classId, getUserId(), new ActionCallbackListener<ClassUser>() {
            @Override
            public void onSuccess(ClassUser data, String message) {
                Intent intent;
                if ("teacher".equals(message)) {
                    intent = new Intent(getContext(), TeaClassMainActivity.class);
                } else {
                    intent = new Intent(getContext(), StuClassMainActivity.class);
                }
                setUserTitle(data.getTitle());
                setClassId(classId);
                setClassUserId(data.getClass_user_id().toString());
                intent.putExtra("className", data.getMy_class().getName());
                intent.putExtra("ifNewMaterial", data.getIf_new_material());
                intent.putExtra("ifNewHomework", data.getIf_new_homework());
                intent.putExtra("unreadInformationNum", data.getUnread_information_num());
                startActivity(intent);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}