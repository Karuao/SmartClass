package team.qdu.smartclass.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import team.qdu.model.ClassUserExp;
import team.qdu.smartclass.R;

/**
 * Created by asus on 2018/4/5.
 */

public class ExpDetailAdapter extends SBaseAdapter<ClassUserExp>{

    public ExpDetailAdapter(Context context) {
        super(context);
    }

    public final class Compo {
        public TextView expDetail;
        public TextView expTime;
        public TextView exp;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Compo compo = null;
        if (convertView == null) {
            compo = new Compo();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.class_student_expdetail_listitem, null);
            compo.expDetail = (TextView) convertView.findViewById(R.id.tv_class_getExp_way);
            compo.expTime = (TextView) convertView.findViewById(R.id.tv_class_getExp_time);
            compo.exp = (TextView) convertView.findViewById(R.id.tv_class_getExp_exp) ;
            convertView.setTag(compo);
        } else {
            compo = (ExpDetailAdapter.Compo) convertView.getTag();
        }
        //绑定数据
        compo.expDetail.setText(itemList.get(position).getDetail());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String date = sdf.format(itemList.get(position).getCreate_date_time());
        compo.expTime.setText(date);
        compo.exp.setText(itemList.get(position).getExp().toString());
        return convertView;
    }
}
