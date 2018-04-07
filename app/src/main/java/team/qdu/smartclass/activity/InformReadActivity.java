package team.qdu.smartclass.activity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.User;
import team.qdu.smartclass.R;
import team.qdu.smartclass.adapter.InformCheckedAdapter;

/**
 * Created by n551 on 2018/2/26.
 */

public class InformReadActivity extends SBaseActivity {
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_infrom_admin_checked_people);
        listview = (ListView) findViewById(R.id.class_inform_checked_listView);
            getReadPeople();
    }

    private void getReadPeople()  {
        String informid = getIntent().getStringExtra("informid");
        informAppAction.getReadPeople(informid, new ActionCallbackListener<List<User>>() {

            @Override
            public void onSuccess(List<User> data, String message) {
                listview.setAdapter(new InformCheckedAdapter(context, data));
            }

            @Override
            public void onFailure(String errorEvent, String message) {
            }
        });
    }
}
