package team.qdu.smartclass.fragment;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.Fragment;

import team.qdu.core.Lifeful;

/**
 * Created by 11602 on 2017/10/19.
 */

public abstract class SBaseFragment extends Fragment implements Lifeful {

    protected boolean isVisible;

    //禁用懒加载
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible(){
        lazyLoad();
    }

    protected  void lazyLoad(){};

    protected void onInvisible(){};

    //从SharedPreferences获取userId
    public String getUserId() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        return  sharedPreferences.getString("userId", null);
    }

    public void setUserId(String userId) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("classId", userId);
        editor.commit();
    }

    //从SharedPreferences获取userId
    public String getClassId() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        return  sharedPreferences.getString("classId", null);
    }

    public void setClassId(String classId) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("classId", classId);
        editor.commit();
    }

    //从SharedPreferences获取title老师/学生
    public String getUserTitle() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        return  sharedPreferences.getString("userTitle", null);
    }

    public void setUserTitle(String userTitle) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userTitle", userTitle);
        editor.commit();
    }

    //从SharedPreferences获取classUserId
    public String getClassUserId() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        return  sharedPreferences.getString("classUserId", null);
    }

    public void setClassUserId(String classUserId) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("classUserId", classUserId);
        editor.commit();
    }

    @Override
    public boolean isAlive() {
        return activityIsAlive();
    }

    public boolean activityIsAlive() {
        Activity currentActivity = getActivity();
        if (currentActivity == null) return false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return !(currentActivity.isDestroyed() || currentActivity.isFinishing());
        } else {
            return !currentActivity.isFinishing();
        }
    }
}
