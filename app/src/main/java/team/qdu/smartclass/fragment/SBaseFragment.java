package team.qdu.smartclass.fragment;


import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

/**
 * Created by 11602 on 2017/10/19.
 */

public abstract class SBaseFragment extends Fragment {

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
}
