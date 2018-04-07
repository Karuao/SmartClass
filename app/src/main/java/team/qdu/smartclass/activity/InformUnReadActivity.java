package team.qdu.smartclass.activity;

import android.os.Bundle;
import android.widget.ListView;

import java.net.URISyntaxException;
import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.User;
import team.qdu.smartclass.R;
import team.qdu.smartclass.adapter.InformUnCheckedAdapter;
import team.qdu.smartclass.util.LoadingDialogUtil;

/**
 * Created by n551 on 2018/2/26.
 */

public class InformUnReadActivity extends SBaseActivity {
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_inform_admin_notcheck_people);
        listview = (ListView) findViewById(R.id.class_inform_unchecked_listView);
        try {
            getUnReadPeople();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void getUnReadPeople()  throws URISyntaxException {
        LoadingDialogUtil.createLoadingDialog(this, "加载中...");
        String informid = getIntent().getStringExtra("informid");
        informAppAction.getUnReadPeople(informid, new ActionCallbackListener<List<User>>() {

            @Override
            public void onSuccess(List<User> data, String message) {
                listview.setAdapter(new InformUnCheckedAdapter(context, data));
                LoadingDialogUtil.closeDialog();//关闭加载中动画
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                LoadingDialogUtil.closeDialog();//关闭加载中动画
            }
        });
    }
}
