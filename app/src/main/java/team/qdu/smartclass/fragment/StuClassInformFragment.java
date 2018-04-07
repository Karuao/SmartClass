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

import java.net.URISyntaxException;
import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.Inform_User;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.SBaseActivity;
import team.qdu.smartclass.activity.StuClassMainActivity;
import team.qdu.smartclass.activity.StuInformDetailActivity;
import team.qdu.smartclass.adapter.StuInfoAdapter;
import team.qdu.smartclass.util.LoadingDialogUtil;

/**
 * Created by rjmgc on 2018/1/17.
 */

public class StuClassInformFragment extends SBaseFragment implements AdapterView.OnItemClickListener {

    //标题栏班课名
    TextView titleBarClassNameTxt;
    ListView listview;
    StuClassMainActivity parentActivity;
    //刷新标志
    public static boolean refreshFlag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.class_tab04, container, false);
        titleBarClassNameTxt = (TextView) view.findViewById(R.id.txt_titlebar_classname);
        titleBarClassNameTxt.setText(((SBaseActivity)getActivity()).getCourse());
        parentActivity = (StuClassMainActivity) getActivity();
        listview = (ListView) view.findViewById(R.id.class_inform_listView);
        try {
            getInform();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        listview.setOnItemClickListener(this);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (refreshFlag) {
            try {
                getInform();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            refreshFlag = false;
        }
    }

    private void getInform()  throws URISyntaxException {
        LoadingDialogUtil.createLoadingDialog(getContext(), "加载中...");
        String classid = getClassId();
        String userid = getUserId();

        parentActivity.informAppAction.getUserInform(classid, userid, new ActionCallbackListener<List<Inform_User>>() {

            @Override
            public void onSuccess(List<Inform_User> data, String message) {
                listview.setAdapter(new StuInfoAdapter(getActivity(), data));
                LoadingDialogUtil.closeDialog();//关闭加载中动画
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                LoadingDialogUtil.closeDialog();//关闭加载中动画
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //若未读小红标数字减1
        if ("否".equals(((TextView) view.findViewById(R.id.tv_if_read)).getText().toString())) {
            ((StuClassMainActivity) getActivity()).decrementInformBadge();
        }
        final String detail = ((TextView) view.findViewById(R.id.txt_commitstu_sno)).getText().toString();
        final String time = ((TextView) view.findViewById(R.id.tv_class_inform_time)).getText().toString();
        final String inform_user_id = ((TextView) view.findViewById(R.id.tv_inform_user_id)).getText().toString();
        final String classid=getClassId();
        final String userid=getUserId();
        parentActivity.informAppAction.ClickInform(inform_user_id,classid,userid, new ActionCallbackListener<Void>() {
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
