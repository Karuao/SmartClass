package team.qdu.smartclass.activity;

/**
 * 班课界面
 * Created by rjmgc on 2017/12/11.
 */
import team.qdu.smartclass.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainTab01 extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return  inflater.inflate(R.layout.main_tab_01, container, false);

    }

}