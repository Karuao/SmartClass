package team.qdu.smartclass.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import team.qdu.model.Attendance_user;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.SBaseActivity;

/**
 * Created by asus on 2018/3/21.
 */

public class SignInHistoryForStudentAdapter extends SBaseAdapter<Attendance_user>{

    public SignInHistoryForStudentAdapter(Context context) {
        super(context);
    }

    public final class Compo {
        public TextView signInTime1;
        public TextView signInTime2;
        public TextView signInState;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Compo compo = null;
        if (convertView == null) {
            compo = new Compo();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.class_signin_listitem_student, null);
            compo.signInTime1 = (TextView) convertView.findViewById(R.id.tv_class_sign_time1_student);
            compo.signInTime2 = (TextView) convertView.findViewById(R.id.tv_class_sign_time2_student);
            compo.signInState = (TextView) convertView.findViewById(R.id.tv_class_sign_state_student) ;
            convertView.setTag(compo);
        } else {
            compo = (SignInHistoryForStudentAdapter.Compo) convertView.getTag();
        }
        //绑定数据
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String date1 = sdf1.format(itemList.get(position).getCreate_date_time());
        compo.signInTime1.setText(date1);
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        String date2 = sdf2.format(itemList.get(position).getModify_date_time());
        compo.signInTime2.setText(date2);
        compo.signInState.setText(itemList.get(position).getAttendance_status());
        if(itemList.get(position).getAttendance_status().equals("未签到")){
            compo.signInState.setTextColor((context).getResources().getColor(R.color.colorRank));
        }else {
            compo.signInState.setTextColor((context).getResources().getColor(R.color.hinter));
        }
        return convertView;
    }
}
