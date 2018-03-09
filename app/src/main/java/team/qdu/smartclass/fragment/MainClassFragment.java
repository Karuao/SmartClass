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

import com.jauker.widget.BadgeView;

import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.ClassUser;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.MainActivity;
import team.qdu.smartclass.activity.StuClassMainActivity;
import team.qdu.smartclass.activity.TeaClassMainActivity;
import team.qdu.smartclass.adapter.ClassAdapter;


public class MainClassFragment extends SBaseFragment implements AdapterView.OnItemClickListener {

    //当前页面
    private View currentPage;
    private MainActivity parentActivity;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static boolean refreshFlag = false;

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
        listView.setOnItemClickListener(this);
        // 设置颜色属性的时候一定要注意是引用了资源文件还是直接设置16进制的颜色，因为都是int值容易搞混
        // 设置下拉进度的背景颜色，默认就是白色的
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 开始刷新，设置当前为刷新状态
                swipeRefreshLayout.setRefreshing(true);
                // 这里是主线程
                // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
                // TODO 获取数据
                parentActivity.classAppAction.getJoinedClasses(getUserId(), new ActionCallbackListener<List<ClassUser>>() {
                    @Override
                    public void onSuccess(List<ClassUser> data, String message) {
                        listView.setAdapter(new ClassAdapter(getActivity(), data));
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(String errorEvent, String message) {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                });

                // System.out.println(Thread.currentThread().getName());
                // 这个不能写在外边，不然会直接收起来
                //swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    //获取登录用户加入的班课列表
    private void getJoinedClasses() {
        parentActivity.classAppAction.getJoinedClasses(getUserId(), new ActionCallbackListener<List<ClassUser>>() {
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
        //跳转班课内部界面，根据classId和userId判断身份，跳转老师或学生界面
        final String classId = ((TextView) view.findViewById(R.id.txt_classId)).getText().toString();
        parentActivity.classAppAction.jumpClass(classId, getUserId(), new ActionCallbackListener<ClassUser>() {
            @Override
            public void onSuccess(ClassUser data, String message) {
                Intent intent;
                if ("老师".equals(data.getTitle())) {
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

        //取消红点显示
        BadgeView badgeView = (BadgeView) view.findViewWithTag("badgeView");
        if (badgeView != null) {
            badgeView.decrementBadgeCount(1);
            parentActivity.classAppAction.readNew(getClassUserId(), "classList");
        }
    }
}