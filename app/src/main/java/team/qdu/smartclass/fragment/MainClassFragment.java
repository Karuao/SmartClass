package team.qdu.smartclass.fragment;

/**
 * 班课界面
 * Created by rjmgc on 2017/12/11.
 */

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

import java.util.Date;
import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.ClassUser;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.MainActivity;
import team.qdu.smartclass.activity.SBaseActivity;
import team.qdu.smartclass.activity.StuClassMainActivity;
import team.qdu.smartclass.activity.TeaClassMainActivity;
import team.qdu.smartclass.adapter.ClassAdapter;
import team.qdu.smartclass.util.LoadingDialogUtil;


public class MainClassFragment extends SBaseFragment implements AdapterView.OnItemClickListener {

    //当前页面
    private View currentPage;
    private MainActivity parentActivity;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ClassAdapter classAdapter;
    public static boolean refreshFlag = false;
    //是否可以跳转班课，用来防止快速点击进入两个班课
    private Date lastClickTime = new Date();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentPage = inflater.inflate(R.layout.fragment_main_class, container, false);
        parentActivity = (MainActivity) getActivity();
        initView();
        initEvent();
        return currentPage;
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

    private void initView() {
        listView = (ListView) currentPage.findViewById(R.id.list_mainclass);
        swipeRefreshLayout = (SwipeRefreshLayout) currentPage.findViewById(R.id.swipe_refresh_layout);
        getJoinedClasses();
    }

    private void initEvent() {
        classAdapter = new ClassAdapter(getActivity());
        listView.setAdapter(classAdapter);
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
                getJoinedClasses();
            }
        });
    }

    //获取登录用户加入的班课列表
    private void getJoinedClasses() {
        parentActivity.classAppAction.getJoinedClasses(getUserId(), this, new ActionCallbackListener<List<ClassUser>>() {
            @Override
            public void onSuccess(List<ClassUser> data, String message) {
                //取消已结束班课
                //将已结束班课放到List末端
//                int size = data.size();
//                for (int i = 0; i < size; ) {
//                    if ("已结束".equals(data.get(i).getIf_allow_to_join())) {
//                        data.add(data.remove(i));
//                        size--;
//                    } else {
//                        i++;
//                    }
//                }
                classAdapter.setItems(data);
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
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, long id) {
        Date currentClickTime = new Date();
        if (currentClickTime.getTime() - lastClickTime.getTime() < 1000) {
            lastClickTime = currentClickTime;
            return;
        }
        lastClickTime = currentClickTime;
        //跳转班课内部界面，根据classId和userId判断身份，跳转老师或学生界面
        LoadingDialogUtil.createLoadingDialog(getActivity(), "加载中...");//加载中动画，用来防止用户重复点击
        final String classId = ((TextView) view.findViewById(R.id.txt_classId)).getText().toString();
        parentActivity.classAppAction.jumpClass(classId, getUserId(), this, new ActionCallbackListener<ClassUser>() {
            @Override
            public void onSuccess(ClassUser data, String message) {
                if ("否".equals(data.getIf_in_class())) {
                    //已经不再班课中则刷新班课列表并提示
                    getJoinedClasses();
                    Toast.makeText(getActivity(), "您已被移出该班课", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent;
                    if ("老师".equals(data.getTitle())) {
                        intent = new Intent(getContext(), TeaClassMainActivity.class);
                    } else {
                        intent = new Intent(getContext(), StuClassMainActivity.class);
//                        intent.putExtra("ifNewMaterial", data.getIf_new_material());
                        intent.putExtra("unbrowseMaterailNum", data.getUnbrowse_material_num());
                        intent.putExtra("ifNewHomework", data.getIf_new_homework());
                        intent.putExtra("unreadInformationNum", data.getUnread_information_num());
                    }
                    setUserTitle(data.getTitle());
                    setClassId(classId);
                    setClassUserId(data.getClass_user_id().toString());
                    ((SBaseActivity) getActivity()).setCourse(data.getMy_class().getCourse());
                    startActivity(intent);
                    //取消红点显示
//                    BadgeView badgeView = (BadgeView) view.findViewWithTag("badgeView");
//                if ("是".equals(classAdapter.getItem(position).getIf_new_class_thing())) {
//                    if (badgeView != null) {
//                        ((ViewGroup) badgeView.getParent()).removeView(badgeView);
////                    badgeView.decrementBadgeCount(1);
//                    }
                    //无论进入有红点的班课还是应该有红点但是没刷新列表的班课，都了解了班课内的推送，下次将不再显示红点
//                    parentActivity.classAppAction.readNew(getClassUserId(), "classList");
                }
                LoadingDialogUtil.closeDialog();//关闭加载中动画
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                LoadingDialogUtil.closeDialog();//关闭加载中动画
            }
        });
    }
}