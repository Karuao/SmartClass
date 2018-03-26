package team.qdu.smartclass.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.Attendance_user;
import team.qdu.model.User;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.SBaseActivity;
import team.qdu.smartclass.activity.ShowSignInResultActivity;

/**
 * Created by asus on 2018/3/22.
 */

public class SignInStudentAdapter extends SBaseAdapter<Attendance_user> {
    public SignInStudentAdapter(Context context, List itemList) {
        super(context);
        this.itemList = itemList;
    }

    public final class Compo {
        public TextView stuName;
        public TextView stuSno;
        public TextView signInState;
        public TextView attendanceUserId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Compo compo = null;
        if (convertView == null) {
            compo = new Compo();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.class_signin_result_listitem, null);
            compo.stuName = (TextView) convertView.findViewById(R.id.tv_class_sign_student_name);
            compo.stuSno = (TextView) convertView.findViewById(R.id.tv_class_sign_student_sno);
            compo.signInState = (TextView) convertView.findViewById(R.id.tv_class_sign_state2_student) ;
            compo.attendanceUserId = (TextView)convertView.findViewById(R.id.tv_class_sign_attendanceUserId) ;
            convertView.setTag(compo);
        } else {
            compo = (SignInStudentAdapter.Compo) convertView.getTag();
        }
        //绑定数据
        compo.signInState.setText(itemList.get(position).getAttendance_status());
        compo.stuName.setText(itemList.get(position).getUser().getName());
        compo.stuSno.setText(itemList.get(position).getUser().getSno());
        compo.attendanceUserId.setText(itemList.get(position).getAttendance_user_id().toString());
        return convertView;
    }

}
