package team.qdu.smartclass;

import android.app.Application;

import team.qdu.core.AppAction;
import team.qdu.core.AppActionImpl;

/**
 * Application类，应用级别的操作都放这里
 *
 * Created by Rock on 2017/4/23.
 */

public class SApplication extends Application {

    private AppAction appAction;

    @Override
    public void onCreate() {
        super.onCreate();
        appAction = new AppActionImpl(this);
    }

    public AppAction getAppAction() {
        return appAction;
    }
}
