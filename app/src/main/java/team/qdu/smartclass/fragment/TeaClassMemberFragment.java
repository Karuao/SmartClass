package team.qdu.smartclass.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.ClassUser;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.SBaseActivity;
import team.qdu.smartclass.activity.ShowMemberDetailActivity;
import team.qdu.smartclass.activity.TeaClassMainActivity;
import team.qdu.smartclass.adapter.ClassMemberAdapter;
import team.qdu.smartclass.adapter.SignInHistoryForTeacherAdapter;

/**
 * Created by rjmgc on 2018/1/17.
 */

public class TeaClassMemberFragment extends SBaseFragment implements AdapterView.OnItemClickListener{

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
        getClassMembers();
        return currentPage;
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
        Bitmap bmp =((BitmapDrawable) ((ImageView) view.findViewById(R.id.iv_class_memberimg)).getDrawable()).getBitmap();
        Intent intent = new Intent(getContext(), ShowMemberDetailActivity.class);
        intent.putExtra("memberName",memberName);
        intent.putExtra("memberSno",memberSno);
        intent.putExtra("memberExp",memberExp);
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte [] bitmapByte =baos.toByteArray();
        intent.putExtra("bitmap", bitmapByte);
        startActivity(intent);
    }

    //获取登录用户的班课成员列表
    private void getClassMembers() {
        parentActivity.memberAppAction.getClassMembers(getClassId(), new ActionCallbackListener<List<ClassUser>>() {
            @Override
            public void onSuccess(List<ClassUser> data, String message) {
                memberCount.setText(String.valueOf(data.size()));
                classMemberAdapter.setItems(data);
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
