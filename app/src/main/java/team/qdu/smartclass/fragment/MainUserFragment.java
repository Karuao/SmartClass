package team.qdu.smartclass.fragment;

/**
 * 个人信息界面
 * Created by rjmgc on 2017/12/11.
 */

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        initView();
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
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("user", Activity.MODE_PRIVATE);
        String account=sharedPreferences.getString("account",null);
        final MainActivity parentActivity= (MainActivity) getActivity();
        parentActivity.userAppAction.getUserInforByAccount(account,new ActionCallbackListener<User>() {
            @Override
            public void onSuccess(User user, String message) {
                if(user.getAvatar()!=null) {
                    //从服务器获取图片
                    parentActivity.userAppAction.getBitmap(user.getAvatar(), new ActionCallbackListener<Bitmap>() {
                        @Override
                        public void onSuccess(Bitmap data, String message) {
                            personAvatar.setImageBitmap(data);
                        }

                        @Override
                        public void onFailure(String errorEvent, String message) {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
            }
        });
    }
}