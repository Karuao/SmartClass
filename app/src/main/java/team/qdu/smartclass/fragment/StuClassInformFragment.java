package team.qdu.smartclass.fragment;

import android.content.Intent;
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
import team.qdu.model.Inform_User;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.StuClassMainActivity;
import team.qdu.smartclass.activity.StuInformDetailActivity;
import team.qdu.smartclass.adapter.StuInfoAdapter;

/**
 * Created by rjmgc on 2018/1/17.
 */

public class StuClassInformFragment extends SBaseFragment implements AdapterView.OnItemClickListener {
    ListView listview;
    StuClassMainActivity parentActivity;
    //刷新标志
    public static boolean refreshFlag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.class_tab04, container, false);
        parentActivity = (StuClassMainActivity) getActivity();
        listview = (ListView) view.findViewById(R.id.class_inform_listView);
        getInform();
        listview.setOnItemClickListener(this);
        return view;

    }
    @Override
    public void onResume() {
        super.onResume();
        if (refreshFlag) {
            getInform();
            refreshFlag = false;
        }
    }

    private void getInform() {
        String classid = getActivity().getIntent().getStringExtra("classId");
        String userid = getActivity().getIntent().getStringExtra("userId");

        parentActivity.informAppAction.getUserInform(classid, userid, new ActionCallbackListener<List<Inform_User>>() {

            @Override
            public void onSuccess(List<Inform_User> data, String message) {
                listview.setAdapter(new StuInfoAdapter(getActivity(), data));
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final String detail = ((TextView) view.findViewById(R.id.tv_class_inform_details)).getText().toString();
        final String time = ((TextView) view.findViewById(R.id.tv_class_inform_time)).getText().toString();
        final String inform_user_id = ((TextView) view.findViewById(R.id.tv_inform_user_id)).getText().toString();
        parentActivity.informAppAction.ClickInform(inform_user_id, new ActionCallbackListener<Void>() {
            @Override
            public void onSuccess(Void data, String message) {
                System.out.println(message);
                Intent intent = new Intent(getContext(), StuInformDetailActivity.class);
                intent.putExtra("detail", detail);
                intent.putExtra("time", time);
                startActivity(intent);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });

    }


}
