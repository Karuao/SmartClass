package team.qdu.smartclass.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.ClassUser;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.SBaseActivity;
import team.qdu.smartclass.activity.ShowMemberDetailActivity;
import team.qdu.smartclass.activity.TeaClassMainActivity;
import team.qdu.smartclass.adapter.ClassMemberAdapter;

/**
 * Created by rjmgc on 2018/1/17.
 */

public class TeaClassMemberFragment extends SBaseFragment implements AdapterView.OnItemClickListener{

    private boolean isPrepared;
    private View currentPage;
    //标题栏班课名
    private TextView titleBarClassNameTxt;
    private  TextView memberCount;
    private ClassMemberAdapter classMemberAdapter;
    private ListView listView;
    private TeaClassMainActivity parentActivity;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static boolean refreshFlag = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentPage = inflater.inflate(R.layout.class_tab02_admin, container, false);
        initView();
        initEvent();
        isPrepared = true;
        lazyLoad();
        return currentPage;
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        getClassMembers();
    }

    public void initView() {
        parentActivity = (TeaClassMainActivity) getActivity();
        memberCount = (TextView)currentPage.findViewById(R.id.iv_class_member_teacher_allpeople);
        listView = (ListView) currentPage.findViewById(R.id.list_classMember_teacher);
        swipeRefreshLayout = (SwipeRefreshLayout) currentPage.findViewById(R.id.swipe_refresh_layout_member_teacher);
        titleBarClassNameTxt = (TextView) currentPage.findViewById(R.id.txt_titlebar_classname);
        titleBarClassNameTxt.setText(((SBaseActivity)getActivity()).getCourse());
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
        classMemberAdapter = new ClassMemberAdapter(getActivity());
        listView.setAdapter(classMemberAdapter);
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
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String classUserId = ((TextView)view.findViewById(R.id.member_classUserId)).getText().toString();
        setClassUserId(classUserId);
        String memberName = ((TextView) view.findViewById(R.id.tv_class_membername2)).getText().toString();
        String memberSno = ((TextView) view.findViewById(R.id.tv_class_membernum2)).getText().toString();
        String memberExp = ((TextView) view.findViewById(R.id.tv_class_memberexp2)).getText().toString();
        String memberAvatar = ((TextView)view.findViewById(R.id.memberAvatar)).getText().toString();
        String memberId = ((TextView)view.findViewById(R.id.memberId)).getText().toString();
        Intent intent = new Intent(getContext(), ShowMemberDetailActivity.class);
        intent.putExtra("memberName",memberName);
        intent.putExtra("memberSno",memberSno);
        intent.putExtra("memberExp",memberExp);
        intent.putExtra("memberAvatar", memberAvatar);
        intent.putExtra("memberId",memberId);
        startActivity(intent);
    }

    //获取登录用户的班课成员列表
    private void getClassMembers() {
        parentActivity.memberAppAction.getClassMembers(getClassId(),this, new ActionCallbackListener<List<ClassUser>>() {
            @Override
            public void onSuccess(List<ClassUser> data, String message) {
                memberCount.setText(String.valueOf(data.size()));
                classMemberAdapter.setItems(data);
                classMemberAdapter.initRank();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}