package team.qdu.smartclass;

import android.app.Application;
import android.os.StrictMode;

import team.qdu.core.ClassAppAction;
import team.qdu.core.ClassAppActionImpl;
import team.qdu.core.InformAppAction;
import team.qdu.core.InformAppActionImpl;
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

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        userAppAction = new UserAppActionImpl(this);
        classAppAction = new ClassAppActionImpl(this);
        informAppAction = new InformAppActionImpl(this);
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
}
