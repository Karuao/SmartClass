package team.qdu.smartclass.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.ClassUser;
import team.qdu.model.User;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.StuClassMainActivity;
import team.qdu.smartclass.activity.TeaClassMainActivity;
import team.qdu.smartclass.adapter.ClassMemberAdapter;

/**
 * Created by rjmgc on 2018/1/17.
 */

public class StuClassMemberFragment extends SBaseFragment  implements AdapterView.OnItemClickListener{

    private View currentPage;
    //标题栏班课名
    private TextView titleBarClassNameTxt;
    private  TextView memberCount;
    private ListView listView;
    private  TextView rank;
    private StuClassMainActivity parentActivity;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static boolean refreshFlag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentPage = inflater.inflate(R.layout.class_tab02, container, false);
        initView();
        initEvent();
        getClassMembers();
        return currentPage;
    }

    public void initView() {
        parentActivity = (StuClassMainActivity) getActivity();
        memberCount = (TextView)currentPage.findViewById(R.id.iv_class_member_allpeople);
        rank = (TextView)currentPage.findViewById(R.id.tv_class_member_rank);
        listView = (ListView) currentPage.findViewById(R.id.list_classMember);
        swipeRefreshLayout = (SwipeRefreshLayout) currentPage.findViewById(R.id.swipe_refresh_layout_member_student);
        titleBarClassNameTxt = (TextView) currentPage.findViewById(R.id.txt_titlebar_classname);
        titleBarClassNameTxt.setText(getActivity().getIntent().getStringExtra("className"));
    }

    //页面从后台返回到前台运行
    @Override
    public void onResume() {
        super.onResume();
        if (refreshFlag) {
            getClassMembers();
            refreshFlag = false;
        }
    }

    private void initEvent() {
        listView.setOnItemClickListener(this);
        // 设置下拉进度的主题颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorSecondary);
        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 开始刷新，设置当前为刷新状态
                swipeRefreshLayout.setRefreshing(true);
                // TODO 获取数据
                getClassMembers();
            }
        });
    }

    //获取登录用户加入的班课成员列表
    private void getClassMembers() {
        parentActivity.memberAppAction.getClassMembers(getClassId(), new ActionCallbackListener<List<ClassUser>>() {
            @Override
            public void onSuccess(List<ClassUser> data, String message) {
                memberCount.setText(String.valueOf(data.size()));
                listView.setAdapter(new ClassMemberAdapter(getActivity(), data));
                int classUserId = Integer.parseInt(getClassUserId());
                int myRank=1;
                int lastExp=0;
                for (ClassUser cu:data) {
                     if(classUserId!=cu.getClass_user_id()){
                        if(lastExp!=cu.getExp()) {
                            myRank++;
                            lastExp = cu.getExp();
                        }else {
                            lastExp = cu.getExp();
                        }
                    }else {
                        break;
                    }
                }
                rank.setText(String.valueOf(myRank));
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
