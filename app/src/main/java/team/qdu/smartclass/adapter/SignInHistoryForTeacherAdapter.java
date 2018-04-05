package team.qdu.smartclass.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import team.qdu.model.Attendance;
import team.qdu.smartclass.R;

/**
 * Created by asus on 2018/3/17.
 */

public class SignInHistoryForTeacherAdapter extends SBaseAdapter<Attendance> {

    public SignInHistoryForTeacherAdapter(Context context) {
        super(context);
    }

    public final class Compo {
        public TextView signInTime;
        public TextView signInNumber;
        public TextView totalClassMember;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Compo compo = null;
        if (convertView == null) {
            compo = new Compo();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.class_signin_listitem_teacher, null);
            compo.signInTime = (TextView) convertView.findViewById(R.id.tv_class_sign_time_teacher);
            compo.signInNumber = (TextView) convertView.findViewById(R.id.tv_class_sign_number);
            compo.totalClassMember = (TextView) convertView.findViewById(R.id.total_class_member) ;
            convertView.setTag(compo);
        } else {
            compo = (SignInHistoryForTeacherAdapter.Compo) convertView.getTag();
        }
        //绑定数据
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String date = sdf.format(itemList.get(position).getCreate_date_time());
        compo.signInTime.setText(date);
        compo.totalClassMember.setText(itemList.get(position).getStu_num().toString());
        compo.signInNumber.setText(itemList.get(position).getAttendance_stu_count().toString());
        return convertView;
    }
}
