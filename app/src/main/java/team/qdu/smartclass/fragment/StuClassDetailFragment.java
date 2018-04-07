package team.qdu.smartclass.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.Class;
import team.qdu.model.User;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.SBaseActivity;
import team.qdu.smartclass.activity.StuClassMainActivity;
import team.qdu.smartclass.util.LoadingDialogUtil;

/**
 * Created by rjmgc on 2018/1/17.
 */

public class StuClassDetailFragment extends SBaseFragment {

    //标题栏班课名
    TextView titleBarClassNameTxt;
    ImageView stuClassDetailImg;
    TextView stuClassName;
    TextView stuClassNum;
    TextView stuClassTeacher;
    TextView stuClassUniversity;
    TextView stuClassDepartment;
    TextView stuClassDetail;
    TextView stuClassExam;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.class_tab05, container, false);
        titleBarClassNameTxt = (TextView) view.findViewById(R.id.txt_titlebar_classname);
        titleBarClassNameTxt.setText(((SBaseActivity)getActivity()).getCourse());
        stuClassDetailImg = (ImageView) view.findViewById(R.id.img_class);
        stuClassName = (TextView) view.findViewById(R.id.tv_class_classname);
        stuClassNum = (TextView) view.findViewById(R.id.tv_class_classnum);
        stuClassTeacher = (TextView) view.findViewById(R.id.tv_class_teacher);
        stuClassUniversity = (TextView) view.findViewById(R.id.tv_class_university);
        stuClassDepartment = (TextView) view.findViewById(R.id.tv_class_department);
        stuClassDetail = (TextView) view.findViewById(R.id.tv_class_goal_details);
        stuClassExam = (TextView) view.findViewById(R.id.tv_class_exam_details);
        final StuClassMainActivity parentActivity = (StuClassMainActivity) getActivity();
        parentActivity.classAppAction.getClassInfor(getClassId(), new ActionCallbackListener<Class>() {
            @Override
            public void onSuccess(Class data, String message) {
                stuClassName.setText(data.getCourse());
                stuClassNum.setText(data.getName());
                if (TextUtils.isEmpty(data.getDetail())) {
                    stuClassDetail.setText("暂无内容");
                } else {
                    stuClassDetail.setText(data.getDetail());
                }
                if (TextUtils.isEmpty(data.getExam_shedule())) {
                    stuClassExam.setText("暂无内容");
                } else {
                    stuClassExam.setText(data.getExam_shedule());
                }
                final String userId = data.getUser_id().toString();
                LoadingDialogUtil.createLoadingDialog(getActivity(),"加载中...");
                parentActivity.userAppAction.getUserInforById(userId, new ActionCallbackListener<User>() {
                    @Override
                    public void onSuccess(User user, String message) {
                        stuClassTeacher.setText(user.getName());
                        stuClassUniversity.setText(user.getUniversity());
                        stuClassDepartment.setText(user.getDepartment());
                        LoadingDialogUtil.closeDialog();
                    }

                    @Override
                    public void onFailure(String errorEvent, String message) {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        LoadingDialogUtil.closeDialog();
                    }
                });
                //从服务器获取图片
                parentActivity.classAppAction.getBitmap(data.getAvatar(), new ActionCallbackListener<Bitmap>() {
                    @Override
                    public void onSuccess(Bitmap data, String message) {
                        stuClassDetailImg.setImageBitmap(data);
                    }

                    @Override
                    public void onFailure(String errorEvent, String message) {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
