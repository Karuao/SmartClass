package team.qdu.smartclass.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import team.qdu.model.Inform;
import team.qdu.smartclass.R;

/**
 * Created by n551 on 2018/1/29.
 */

public class TeaInfoAdapter extends SBaseAdapter<Inform> {

    public TeaInfoAdapter(Context context, List itemList) {
        super(context);
        this.itemList = itemList;
    }

    public final class Compo {
        public TextView timeTxt;
        public TextView numberTxt;
        public TextView UnreadTxt;
        public TextView informTxt;
        public TextView idTxt;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Compo compo = null;
        if (convertView == null) {
            compo = new Compo();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.class_inform_admin_listitem, null);
            compo.idTxt= (TextView) convertView.findViewById(R.id.tv_inform_id);
            compo.timeTxt = (TextView) convertView.findViewById(R.id.tv_class_inform_time);
            compo.numberTxt = (TextView) convertView.findViewById(R.id.tv_class_inform_people);
            compo.informTxt = (TextView) convertView.findViewById(R.id.txt_commitstu_sno);
            convertView.setTag(compo);
        } else {
            compo = (Compo) convertView.getTag();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        compo.idTxt.setText(Integer.toString(itemList.get(position).getInform_id()));
        Date date=new Date(itemList.get(position).getCreate_date_time().getTime());
        compo.timeTxt.setText(sdf.format(date));
        compo.numberTxt.setText(Integer.toString(itemList.get(position).getRead_num()));
        compo.informTxt.setText(itemList.get(position).getDetail());
        return convertView;
    }
}
