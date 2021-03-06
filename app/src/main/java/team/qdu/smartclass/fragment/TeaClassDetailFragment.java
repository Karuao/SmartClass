package team.qdu.smartclass.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.Class;
import team.qdu.model.User;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.SBaseActivity;
import team.qdu.smartclass.activity.TeaClassMainActivity;
import team.qdu.smartclass.util.LoadingDialogUtil;

/**
 * Created by rjmgc on 2018/1/17.
 */

public class TeaClassDetailFragment extends SBaseFragment {

    private boolean isPrepared;
    //标题栏班课名
    TextView titleBarClassNameTxt;
    CheckBox checkBox;
    TextView hint;
    View hint2;
    TextView allow;
    TextView className;
    TextView classNum;
    TextView classTea;
    TextView classUniversity;
    TextView classDepartment;
    TextView classCode;
    TextView classDetail;
    TextView classExam;
    ImageView teaClassDetailImg;

    public static boolean refreshFlag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.class_tab05_admin, container, false);
        refreshFlag = false;
        titleBarClassNameTxt = (TextView) view.findViewById(R.id.txt_titlebar_classname);
        titleBarClassNameTxt.setText(((SBaseActivity) getActivity()).getCourse());
        checkBox = (CheckBox) view.findViewById(R.id.chk_join);
        allow = (TextView) view.findViewById(R.id.tv_join);
        className = (TextView) view.findViewById(R.id.tv_class_classname);
        classNum = (TextView) view.findViewById(R.id.tv_class_classnum);
        classTea = (TextView) view.findViewById(R.id.tv_class_teacher);
        classUniversity = (TextView) view.findViewById(R.id.tv_class_university);
        classDepartment = (TextView) view.findViewById(R.id.tv_class_department);
        classCode = (TextView) view.findViewById(R.id.tv_class_code);
        classDetail = (TextView) view.findViewById(R.id.tv_class_goal_details_admin);
        classExam = (TextView) view.findViewById(R.id.tv_class_exam_details_admin);
        teaClassDetailImg = (ImageView) view.findViewById(R.id.img_class);
        hint = (TextView) view.findViewById(R.id.hint);
        hint2 = view.findViewById(R.id.hint2);
        isPrepared = true;
        lazyLoad();
        return view;
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        initOrRefresh();
    }

    public void initOrRefresh() {
        final String classId = getClassId();
        final TeaClassMainActivity parentActivity = (TeaClassMainActivity) getActivity();
        parentActivity.classAppAction.getClassInfor(classId, this, new ActionCallbackListener<Class>() {
            @Override
            public void onSuccess(Class cls, String message) {
                className.setText(cls.getCourse());
                classNum.setText(cls.getName());
                classCode.setText(classId);
                classUniversity.setText(cls.getUniversity());
                classDepartment.setText(cls.getDepartment());
                if (TextUtils.isEmpty(cls.getDetail())) {
                    classDetail.setText("暂无内容");
                } else {
                    classDetail.setText(cls.getDetail());
                }
                if (TextUtils.isEmpty(cls.getExam_shedule())) {
                    classExam.setText("暂无内容");
                } else {
                    classExam.setText(cls.getExam_shedule());
                }
                String userId = cls.getUser_id().toString();
                parentActivity.userAppAction.getUserInforById(userId, TeaClassDetailFragment.this, new ActionCallbackListener<User>() {
                    @Override
                    public void onSuccess(User user, String message) {
                        classTea.setText(user.getName());
                    }

                    @Override
                    public void onFailure(String errorEvent, String message) {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                });
                //判断班课是否允许加入并设置checked
                if (cls.getIf_allow_to_join().equals("是")) {
                    checkBox.setChecked(true);
                } else if (cls.getIf_allow_to_join().equals("否")) {
                    checkBox.setChecked(false);
                }
                //checkBox监听器
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (!isChecked) {
                            TeaClassMainActivity parentActivity = (TeaClassMainActivity) getActivity();
                            LoadingDialogUtil.createLoadingDialog(getActivity(), "加载中...");
                            parentActivity.classAppAction.notAllowToJoin(classId, TeaClassDetailFragment.this, new ActionCallbackListener<Void>() {
                                @Override
                                public void onSuccess(Void data, String message) {
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                    LoadingDialogUtil.closeDialog();
                                }

                                @Override
                                public void onFailure(String errorEvent, String message) {
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                    LoadingDialogUtil.closeDialog();
                                }
                            });
                        } else {
                            TeaClassMainActivity parentActivity = (TeaClassMainActivity) getActivity();
                            LoadingDialogUtil.createLoadingDialog(getActivity(), "加载中...");
                            parentActivity.classAppAction.allowToJoin(classId, TeaClassDetailFragment.this, new ActionCallbackListener<Void>() {
                                @Override
                                public void onSuccess(Void data, String message) {
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                    LoadingDialogUtil.closeDialog();
                                }

                                @Override
                                public void onFailure(String errorEvent, String message) {
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                    LoadingDialogUtil.closeDialog();
                                }
                            });
                        }
                    }
                });
                //从服务器获取图片
                if (cls.getAvatar() != null) {
                    parentActivity.fileAppAction.cacheImg(cls.getAvatar(), getActivity(), new ActionCallbackListener<File>() {
                        @Override
                        public void onSuccess(File data, String message) {
                            Glide.with(getActivity()).load(data.getPath()).into(teaClassDetailImg);
                        }

                        @Override
                        public void onFailure(String errorEvent, String message) {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //页面从后台返回到前台运行
    @Override
    public void onResume() {
        super.onResume();
        if (refreshFlag) {
            initOrRefresh();
            refreshFlag = false;
        }
    }


}