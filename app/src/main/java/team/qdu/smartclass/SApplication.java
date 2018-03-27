package team.qdu.smartclass;

import android.app.Activity;
import android.app.Application;
import android.os.StrictMode;

import com.lzy.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import team.qdu.core.ClassAppAction;
import team.qdu.core.ClassAppActionImpl;
import team.qdu.core.HomeworkAppAction;
import team.qdu.core.HomeworkAppActionImpl;
import team.qdu.core.ImgAppAction;
import team.qdu.core.ImgAppActionImpl;
import team.qdu.core.InformAppAction;
import team.qdu.core.InformAppActionImpl;
import team.qdu.core.MaterialAppAction;
import team.qdu.core.MaterialAppActionImpl;
import team.qdu.core.MemberAppAction;
import team.qdu.core.MemberAppActionImpl;
import team.qdu.core.UserAppAction;
import team.qdu.core.UserAppActionImpl;
import team.qdu.smartclass.util.GlideImageLoader;

/**
 * Application类，应用级别的操作都放这里
 * <p>
 * Created by Rock on 2017/4/23.
 */

public class SApplication extends Application {

    private UserAppAction userAppAction;

    private ClassAppAction classAppAction;

    private InformAppAction informAppAction;

    private HomeworkAppAction homeworkAppAction;

    private MemberAppAction memberAppAction;

    private MaterialAppAction materialAppAction;

    private ImgAppAction imgAppAction;

    private static List<Activity> activityList = new ArrayList<>();

    private ImagePicker imagePicker;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化JPush SDK
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //配置StrictMode忘了干什么用了-.-
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        initImagePicker();
        userAppAction = new UserAppActionImpl(this);
        classAppAction = new ClassAppActionImpl(this);
        informAppAction = new InformAppActionImpl(this);
        homeworkAppAction = new HomeworkAppActionImpl(this);
        memberAppAction = new MemberAppActionImpl(this);
        materialAppAction = new MaterialAppActionImpl(this);
        imgAppAction = new ImgAppActionImpl(this);
    }

    private void initImagePicker() {
        //作业图片List点击事件
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);
    }

    public UserAppAction getUserAppAction() {
        return userAppAction;
    }

    public ClassAppAction getClassAppAction() {
        return classAppAction;
    }

    public InformAppAction getInformAppAction() {
        return informAppAction;
    }

    public HomeworkAppAction getHomeworkAppAction() {
        return homeworkAppAction;
    }

    public MemberAppAction getMemberAppAction() {
        return memberAppAction;
    }

    public MaterialAppAction getMaterialAppAction() {
        return materialAppAction;
    }

    public ImgAppAction getImgAppAction() {
        return imgAppAction;
    }

    public ImagePicker getImagePicker() {
        return imagePicker;
    }

    //向list中添加Activity
    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static List<Activity> getActivityList() {
        return activityList;
    }

    //finish list中的所有的Activity
    public static void clearActivity() {
        if (activityList != null) {
            for (Activity activity : activityList) {
                activity.finish();
            }
            activityList.clear();
        }
    }
}
