package team.qdu.smartclass.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.Inform;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.TeaClassMainActivity;
import team.qdu.smartclass.activity.TeaInformDetailActivity;
import team.qdu.smartclass.adapter.TeaInfoAdapter;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by rjmgc on 2018/1/17.
 */

public class TeaClassInformFragment extends SBaseFragment implements AdapterView.OnItemClickListener {

    private View currentPage;
    //标题栏班课名
    private TextView titleBarClassNameTxt;
    ListView listview;
    TeaClassMainActivity parentActivity;
    //刷新标志
    public static boolean refreshFlag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentPage = inflater.inflate(R.layout.class_tab04_admin, container, false);
        parentActivity = (TeaClassMainActivity) getActivity();
        titleBarClassNameTxt = (TextView) currentPage.findViewById(R.id.txt_titlebar_classname);
        titleBarClassNameTxt.setText(getActivity().getIntent().getStringExtra("className"));
        listview = (ListView) currentPage.findViewById(R.id.class_inform_listView);
        getInform();
        refreshFlag = false;
        listview.setOnItemClickListener(this);
        return currentPage;

    }

    //页面从后台返回到前台运行
    @Override
    public void onResume() {
        super.onResume();
        if (refreshFlag) {
            getInform();
            refreshFlag = false;
        }
    }

    private void getInform() {
        String classid = getClassId();
        parentActivity.informAppAction.getInform(classid, new ActionCallbackListener<List<Inform>>() {

            @Override
            public void onSuccess(List<Inform> data, String message) {
                listview.setAdapter(new TeaInfoAdapter(getActivity(), data));
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final String detail = ((TextView) view.findViewById(R.id.txt_commitstu_sno)).getText().toString();
        final String time = ((TextView) view.findViewById(R.id.tv_class_inform_time)).getText().toString();
        final String read_num = ((TextView) view.findViewById(R.id.tv_class_inform_people)).getText().toString();
        final String inform_id = ((TextView) view.findViewById(R.id.tv_inform_id)).getText().toString();
        getUnreadNum(inform_id);
        Intent intent = new Intent(getContext(), TeaInformDetailActivity.class);
        String classid = getClassId();
        intent.putExtra("detail", detail);
        intent.putExtra("classId", classid);
        intent.putExtra("time", time);
        intent.putExtra("read_num", read_num);
        intent.putExtra("informid", inform_id);
        startActivity(intent);
    }

    private void getUnreadNum(String informid) {
        parentActivity.informAppAction.getUnreadNum(informid, new ActionCallbackListener<Void>() {

            @Override
            public void onSuccess(Void data, String message) {
                SharedPreferences unreadNumSetting = parentActivity.getSharedPreferences("unreadNum", MODE_PRIVATE);
                SharedPreferences.Editor editor = unreadNumSetting.edit();
                editor.putString("unreadNum", message);
                editor.commit();
            }

            @Override
            public void onFailure(String errorEvent, String message) {

            }
        });
    }

}
