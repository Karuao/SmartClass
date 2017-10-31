package team.qdu.smartclass.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import team.qdu.smartclass.R;
import team.qdu.smartclass.fragment.MainFragment;

/**
 * Created by 11602 on 2017/10/20.
 */

public class MainActivity extends SBaseActivity implements RadioGroup.OnCheckedChangeListener {

    //activity_main对象
    private TextView topbarTitleTxt;
    private RadioGroup tabbarGroup;
    private RadioButton tabClassRbtn;

    //Fragment对象
    private MainFragment fg1, fg2, fg3, fg4;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();
        topbarTitleTxt = (TextView) findViewById(R.id.txt_topbar_title);
        //对tabbarGroup设置监听器
        tabbarGroup = (RadioGroup) findViewById(R.id.group_tabbar);
        tabbarGroup.setOnCheckedChangeListener(this);
        //获取第一个单选按钮，并设置为选中状态
        tabClassRbtn = (RadioButton) findViewById(R.id.rbtn_tab_class);
        tabClassRbtn.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //隐藏之前fragment,显示当前fragment
        switch (checkedId) {
            case R.id.rbtn_tab_class:
                //设置顶部topbarTitleTxt文本
                topbarTitleTxt.setText("班课");
                if (fg2 != null) {
                    fragmentTransaction.hide(fg2);
                }
                //第一次初始化fg1后，添加到fragmentTransaction中，之后将隐藏对象显示
                if (fg1 == null) {
                    fg1 = new MainFragment();
                    fragmentTransaction.add(R.id.frame_content, fg1);
                } else {
                    fragmentTransaction.show(fg1);
                }
                fragmentTransaction.commit();
                fg1.setContent("班课");
                break;
            case R.id.rbtn_tab_me:
                topbarTitleTxt.setText("我的");
                if (fg1 != null) {
                    fragmentTransaction.hide(fg1);
                }
                if (fg2 == null) {
                    fg2 = new MainFragment();

                    fragmentTransaction.add(R.id.frame_content, fg2);
                } else {
                    fragmentTransaction.show(fg2);
                }
                fragmentTransaction.commit();
                fg2.setContent("我的");
                break;
        }
    }
}
