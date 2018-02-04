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
import team.qdu.model.Inform;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.TeaClassMainActivity;
import team.qdu.smartclass.activity.TeaInformDetailActivity;
import team.qdu.smartclass.adapter.TeaInfoAdapter;

/**
 * Created by rjmgc on 2018/1/17.
 */

public class TeaClassInformFragment extends SBaseFragment implements AdapterView.OnItemClickListener {
    ListView listview;
    TeaClassMainActivity parentActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.class_tab04_admin, container, false);
        parentActivity = (TeaClassMainActivity) getActivity();
        listview = (ListView) view.findViewById(R.id.class_inform_listView);
        getInform();
        listview.setOnItemClickListener(this);
        return view;

    }

    private void getInform() {
        String classid = getActivity().getIntent().getStringExtra("classId");
        parentActivity.informAppAction.getInform(classid,new ActionCallbackListener<List<Inform>>() {

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
        final String detail = ((TextView)view.findViewById(R.id.tv_class_inform_details)).getText().toString();
        final String time=((TextView)view.findViewById(R.id.tv_class_inform_time)).getText().toString();
        final String read_num=((TextView)view.findViewById(R.id.tv_class_inform_people)).getText().toString();
        final String inform_id=((TextView)view.findViewById(R.id.tv_inform_id)).getText().toString();
        Intent intent = new Intent(getContext(), TeaInformDetailActivity.class);
        String classid = getActivity().getIntent().getStringExtra("classId");
        intent.putExtra("detail", detail);
        intent.putExtra("classId",classid);
        intent.putExtra("time", time);
        intent.putExtra("read_num",read_num);
        intent.putExtra("informid",inform_id);
        startActivity(intent);

    }

}
