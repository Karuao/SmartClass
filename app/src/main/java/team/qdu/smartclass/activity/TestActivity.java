package team.qdu.smartclass.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team.qdu.smartclass.R;


/**
 * 创建班课
 * TestActivity
 * Created by Rock on 2017/4/23.
 */
public class TestActivity extends ListActivity {
    // private List<String> data = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.item_listview,
                new String[]{"title", "info", "teacher", "img"},
                new int[]{R.id.titleTv, R.id.tv_classname, R.id.tv_teacher, R.id.img_class});
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private List<Map<String, Object>> getData() {
        ClassBean xx = new ClassBean();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "G1");
        map.put("info", "google 1");
        map.put("teacher", "teacher 1");
        map.put("img", R.drawable.add_class_img);
        list.add(map);
        return list;
    }

    public void toSet(View view) {
        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.item_listview,
                new String[]{"title", "info", "teacher", "img"},
                new int[]{R.id.titleTv, R.id.tv_classname, R.id.tv_teacher, R.id.img_class});
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        startActivity(new Intent(TestActivity.this, ChangeInfoActivity.class));
    }
}