package team.qdu.smartclass.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.Class;
import team.qdu.smartclass.R;
import team.qdu.smartclass.adapter.TeaClassFragmentPagerAdapter;
import team.qdu.smartclass.adapter.TeaMaterialAdapter;
import team.qdu.smartclass.fragment.TeaClassMaterialFragment;

/**
 * 班课主页
 * <p>
 * Created by Rock on 2017/4/23.
 */

public class TeaClassMainActivity extends SBaseActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener {

    private ViewPager classVpager;
    private TeaClassFragmentPagerAdapter teaClassFragmentPagerAdapter;
    private TeaMaterialAdapter teaMaterialAdapter;
    //tab
    private LinearLayout tabResource;
    private LinearLayout tabMember;
    private LinearLayout tabHomework;
    private LinearLayout tabInform;
    private LinearLayout tabClassinfo;

    private ImageView imgResource;
    private ImageView imgMember;
    private ImageView imgHomework;
    private ImageView imgInform;
    private ImageView imgClassinfo;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;
    public static final int PAGE_FIVE = 4;

    TextView allow;
    CheckBox ifAllowTojoin;
    TextView hint;
    View hint2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_mainpage);
        teaClassFragmentPagerAdapter = new TeaClassFragmentPagerAdapter(getSupportFragmentManager());

        initView();
        initEvents();
    }

    private void initEvents() {
        //点击效果
        tabResource.setOnClickListener(this);
        tabMember.setOnClickListener(this);
        tabHomework.setOnClickListener(this);
        tabInform.setOnClickListener(this);
        tabClassinfo.setOnClickListener(this);
    }

    //初始化View
    private void initView() {
        //页面切换
        classVpager = (ViewPager) findViewById(R.id.class_viewpager);

        tabResource = (LinearLayout) findViewById(R.id.ll_class_resource);
        tabMember = (LinearLayout) findViewById(R.id.ll_class_member);
        tabHomework = (LinearLayout) findViewById(R.id.ll_class_homework);
        tabInform = (LinearLayout) findViewById(R.id.ll_class_inform);
        tabClassinfo = (LinearLayout) findViewById(R.id.ll_class_classinfo);

        imgResource = (ImageView) findViewById(R.id.iv_class_resource);
        imgMember = (ImageView) findViewById(R.id.iv_class_member);
        imgHomework = (ImageView) findViewById(R.id.iv_class_homework);
        imgInform = (ImageView) findViewById(R.id.iv_class_inform);
        imgClassinfo = (ImageView) findViewById(R.id.iv_class_classinfo);

        classVpager.setAdapter(teaClassFragmentPagerAdapter);
        classVpager.setCurrentItem(2);
        classVpager.addOnPageChangeListener(this);

        //初始化tab按钮颜色，作业为选中
        resetImg();
        imgHomework.setImageResource(R.drawable.class_homework_select);
    }

    //初始化View2
    public void initView2() {
        allow = (TextView) findViewById(R.id.tv_join);
        ifAllowTojoin = (CheckBox) findViewById(R.id.chk_join);
        hint = (TextView) findViewById(R.id.hint);
        hint2 = (View) findViewById(R.id.hint2);
    }

    //切换图片颜色
    private void resetImg() {
        imgResource.setImageResource(R.drawable.class_resource);
        imgMember.setImageResource(R.drawable.class_member);
        imgHomework.setImageResource(R.drawable.class_homework);
        imgInform.setImageResource(R.drawable.class_inform);
        imgClassinfo.setImageResource(R.drawable.class_classinfo);
    }

    public void toSignInforTeacher(View view) {
        startActivity(new Intent(TeaClassMainActivity.this, TeaMemberSigninActivity.class));
    }

    //TeaClassHomeworkFragment老师作业界面发布作业点击事件
    public void toPublish(View view) {
        startActivity(new Intent(TeaClassMainActivity.this, TeaPublishHomeworkActivity.class));
    }

    public void toCreateInform(View view) {
        startActivity(new Intent(TeaClassMainActivity.this, CreateInformActivity.class));
    }

    @Override
    public void onClick(View view) {
        resetImg();
        switch (view.getId()) {
            case R.id.ll_class_resource:
                imgResource.setImageResource(R.drawable.class_resource_select);
                classVpager.setCurrentItem(0);
                break;
            case R.id.ll_class_member:
                imgMember.setImageResource(R.drawable.class_member_select);
                classVpager.setCurrentItem(1);
                break;
            case R.id.ll_class_homework:
                imgHomework.setImageResource(R.drawable.class_homework_select);
                classVpager.setCurrentItem(2);
                break;
            case R.id.ll_class_inform:
                imgInform.setImageResource(R.drawable.class_inform_select);
                classVpager.setCurrentItem(3);
                break;
            case R.id.ll_class_classinfo:
                imgClassinfo.setImageResource(R.drawable.class_classinfo_select);
                classVpager.setCurrentItem(4);
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {
        int currentItem = classVpager.getCurrentItem();
        resetImg();
        switch (currentItem) {
            case 0:
                imgResource.setImageResource(R.drawable.class_resource_select);
                break;
            case 1:
                imgMember.setImageResource(R.drawable.class_member_select);
                break;
            case 2:
                imgHomework.setImageResource(R.drawable.class_homework_select);
                break;
            case 3:
                imgInform.setImageResource(R.drawable.class_inform_select);
                break;
            case 4:
                imgClassinfo.setImageResource(R.drawable.class_classinfo_select);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    public void deleteMaterial(final View view){

        new AlertDialog.Builder(TeaClassMainActivity.this)
                .setTitle("提示")
                .setMessage("确定要删除此资源？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String materialid= ((TextView)((View) view.getParent()).findViewById(R.id.tv_materialid)).getText().toString();
                                TeaClassMainActivity.this.materialAppAction.deleteMaterial(materialid, new ActionCallbackListener<Void>() {

                                    @Override
                                    public void onSuccess(Void data, String message) {
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                        TeaClassMaterialFragment.refreshFlag = true;
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(String errorEvent, String message) {
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                })
                .setNegativeButton("取消", null)
                .show();
    }


    //结束班课按钮
    public void finishClass(final View view) {
        this.classAppAction.getClassInfor(getClassId(), new ActionCallbackListener<Class>() {
            @Override
            public void onSuccess(Class data, String message) {
                if (data.getIf_allow_to_join().equals("已结束")) {
                    new AlertDialog.Builder(TeaClassMainActivity.this)
                            .setTitle("提示")
                            .setMessage("此班课已结束！")
                            .setPositiveButton("确定", null)
                            .show();
                } else {
                    initView2();
                    new AlertDialog.Builder(TeaClassMainActivity.this)
                            .setTitle("提示")
                            .setMessage("确定要结束此班课？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String classId = getClassId();
                                    TeaClassMainActivity.this.classAppAction.finishClass(classId, new ActionCallbackListener<Void>() {
                                        @Override
                                        public void onSuccess(Void data, String message) {
                                            allow.setVisibility(View.GONE);
                                            ifAllowTojoin.setVisibility(View.GONE);
                                            hint.setVisibility(View.GONE);
                                            hint2.setVisibility(View.GONE);
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(String errorEvent, String message) {
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //删除班课按钮
    public void deleteClass(View view) {
        new AlertDialog.Builder(TeaClassMainActivity.this)
                .setTitle("提示")
                .setMessage("确定要删除此班课？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TeaClassMainActivity.this.classAppAction.deleteClass(getClassId(), new ActionCallbackListener<Void>() {
                            @Override
                            public void onSuccess(Void data, String message) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(TeaClassMainActivity.this, MainActivity.class));
                            }

                            @Override
                            public void onFailure(String errorEvent, String message) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }


    //编辑班课按钮
    public void compileClass(View view) {
        this.classAppAction.getClassInfor(getClassId(), new ActionCallbackListener<Class>() {
            @Override
            public void onSuccess(Class data, String message) {
                if (data.getIf_allow_to_join().equals("已结束")) {
                    new AlertDialog.Builder(TeaClassMainActivity.this)
                            .setTitle("提示")
                            .setMessage("班课结束后不允许编辑！")
                            .setPositiveButton("确定", null)
                            .show();
                } else {
                    startActivity(new Intent(TeaClassMainActivity.this, ModifyClassActivity.class));
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //更改作业状态点击事件，由进行中到评价中或由评价中到已结束
    public void toChangeStatus(View view) {
        AlertDialog alert;
        final View listItem = (View) view.getParent().getParent();
        final String homeworkStatus = ((TextView) listItem.findViewById(R.id.txt_homework_underway_status))
                .getText().toString();
        final String homeworkId = ((TextView) listItem.findViewById(R.id.txt_homework_underway_id))
                .getText().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(TeaClassMainActivity.this);
        //生成对话框
        if ("进行中".equals(homeworkStatus)) {
            alert = builder.setTitle("开始评价")
                    .setMessage("开始评价后将无法再提交作业。")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            changeHomeworkStatus(homeworkId, homeworkStatus);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create();
        } else {
            alert = builder.setTitle("结束作业")
                    .setMessage("确认结束作业？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //查询未评价人数是0，然后再结束作业，否则不能结束作业
                            homeworkAppAction.getNotEvaluateStuNum(homeworkId, new ActionCallbackListener<Integer>() {
                                @Override
                                public void onSuccess(Integer data, String message) {
                                    if (data == 0) {
                                        changeHomeworkStatus(homeworkId, homeworkStatus);
                                    } else {
                                        Toast.makeText(TeaClassMainActivity.this, "仍有" + data
                                                + "人作业未评价,请评价后再结束作业", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(String errorEvent, String message) {
                                    Toast.makeText(TeaClassMainActivity.this,
                                            "结束班课失败，请稍后再试", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create();
        }
        alert.show();
    }


    //改变作业状态
    private void changeHomeworkStatus(String homeworkId, final String homeworkStatus) {
        homeworkAppAction.changeHomeworkStatus(homeworkId, homeworkStatus, new ActionCallbackListener<Void>() {
            @Override
            public void onSuccess(Void data, String message) {
                teaClassFragmentPagerAdapter.getTeaClassHomeworkFragment().getTeaHomeworkFragmentPagerAdapter().
                        getTeaHomeworkUnderwayFragment().setHomeworkList();
                if ("评价中".equals(homeworkStatus)) {
                    teaClassFragmentPagerAdapter.getTeaClassHomeworkFragment().getTeaHomeworkFragmentPagerAdapter().
                            getTeaHomeworkFinishFragment().setHomeworkList();
                }
                Toast.makeText(TeaClassMainActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(TeaClassMainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

