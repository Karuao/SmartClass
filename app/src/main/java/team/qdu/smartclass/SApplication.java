package team.qdu.smartclass;

import android.app.Activity;
import android.app.Application;
import android.os.StrictMode;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import team.qdu.core.ClassAppAction;
import team.qdu.core.ClassAppActionImpl;
import team.qdu.core.HomeworkAppAction;
import team.qdu.core.HomeworkAppActionImpl;
import team.qdu.core.InformAppAction;
import team.qdu.core.InformAppActionImpl;
import team.qdu.core.MemberAppAction;
import team.qdu.core.MemberAppActionImpl;
import team.qdu.core.UserAppAction;
import team.qdu.core.UserAppActionImpl;

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

    private static List<Activity> activityList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化JPush SDK
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //配置StrictMode忘了干什么用了-.-
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        userAppAction = new UserAppActionImpl(this);
        classAppAction = new ClassAppActionImpl(this);
        informAppAction = new InformAppActionImpl(this);
        homeworkAppAction = new HomeworkAppActionImpl(this);
        memberAppAction = new MemberAppActionImpl(this);
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

    //向list中添加Activity
    public static void addActivity(Activity activity) {
        activityList.add(activity);
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
