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
import team.qdu.model.Class;
import team.qdu.model.ClassUser;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.TeaClassMainActivity;
import team.qdu.smartclass.adapter.ClassAdapter;
import team.qdu.smartclass.adapter.ClassMemberAdapter;

/**
 * Created by rjmgc on 2018/1/17.
 */

public class TeaClassMemberFragment extends SBaseFragment {

    private View currentPage;
    //标题栏班课名
    private TextView titleBarClassNameTxt;
    private  TextView memberCount;
    private ListView listView;
    private TeaClassMainActivity parentActivity;
    public static boolean refreshFlag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentPage = inflater.inflate(R.layout.class_tab02_admin, container, false);
        initView();
        getClassMembers();
        return currentPage;
    }

    public void initView() {
        parentActivity = (TeaClassMainActivity) getActivity();
        memberCount = (TextView)currentPage.findViewById(R.id.iv_class_member_allpeople);
        listView = (ListView) currentPage.findViewById(R.id.list_classMember);
        titleBarClassNameTxt = (TextView) currentPage.findViewById(R.id.txt_titlebar_classname);
        titleBarClassNameTxt.setText(getActivity().getIntent().getStringExtra("className"));
    }

    //获取登录用户加入的班课列表
    private void getClassMembers() {
        parentActivity.memberAppAction.getClassMembers(getClassId(), new ActionCallbackListener<List<ClassUser>>() {
            @Override
            public void onSuccess(List<ClassUser> data, String message) {
                memberCount.setText(String.valueOf(data.size()));
                listView.setAdapter(new ClassMemberAdapter(getActivity(), data));
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
