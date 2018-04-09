package team.qdu.smartclass.fragment;

/**
 * 个人信息界面
 * Created by rjmgc on 2017/12/11.
 */

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.User;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.MainActivity;

public class MainUserFragment extends SBaseFragment {

    TextView userAccount;
    TextView userName;
    TextView userGender;
    TextView userUniversity;
    TextView userDepartment;
    TextView userMotto;
    TextView userNumber;
    ImageView personAvatar;

    //刷新页面标志
    public static boolean refreshFlag = false;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main_user, container, false);
        userAccount=(TextView) view.findViewById(R.id.userAccount);
        userName=(TextView) view.findViewById(R.id.userName);
        userGender=(TextView) view.findViewById(R.id.userGender);
        userNumber=(TextView)view.findViewById(R.id.userNumber);
        userUniversity=(TextView) view.findViewById(R.id.userUniversity);
        userDepartment=(TextView) view.findViewById(R.id.userDepartment);
        userMotto=(TextView) view.findViewById(R.id.userMotto);
        personAvatar=(ImageView)view.findViewById(R.id.userAvatar);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout_user);
        scrollView = (ScrollView)view.findViewById(R.id.scrollView2);
        initView();
        initEvent();
        return view;
    }

    //页面从后台返回到前台运行
    @Override
    public void onResume() {
        super.onResume();
        if (refreshFlag) {
            initView();
            refreshFlag = false;
        }
    }

    private void initView() {
        final MainActivity parentActivity= (MainActivity) getActivity();
        parentActivity.userAppAction.getUserInforById(getUserId(),new ActionCallbackListener<User>() {
            @Override
            public void onSuccess(User user, String message) {
                if(user.getAvatar()!=null) {
                    //从服务器获取图片
                    parentActivity.fileAppAction.cacheImg(user.getAvatar(), new ActionCallbackListener<File>() {
                        @Override
                        public void onSuccess(File data, String message) {
                            Glide.with(getActivity()).load(data.getPath()).into(personAvatar);
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onFailure(String errorEvent, String message) {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
                userAccount.setText(user.getAccount());
                userName.setText(user.getName());
                userGender.setText(user.getGender());
                userNumber.setText(user.getSno());
                userUniversity.setText(user.getUniversity());
                userDepartment.setText(user.getDepartment());
                userMotto.setText(user.getStatus_message());
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initEvent() {
        if (scrollView != null) {
            scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setEnabled(scrollView.getScrollY() == 0);
                        // 设置下拉进度的主题颜色
                        swipeRefreshLayout.setColorSchemeResources(R.color.colorSecondary);
                        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
                        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                // 开始刷新，设置当前为刷新状态
                                swipeRefreshLayout.setRefreshing(true);
                                // TODO 获取数据
                                initView();
                            }
                        });
                    }
                }
            });
        }
    }
}