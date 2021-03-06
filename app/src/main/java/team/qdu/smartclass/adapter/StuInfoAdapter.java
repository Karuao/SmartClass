package team.qdu.smartclass.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jauker.widget.BadgeView;

import java.text.SimpleDateFormat;
import java.util.List;

import team.qdu.model.Inform_User;
import team.qdu.smartclass.R;

/**
 * Created by n551 on 2018/1/29.
 */

public class StuInfoAdapter extends SBaseAdapter<Inform_User> {

    public StuInfoAdapter(Context context, List itemList) {
        super(context);
        this.itemList = itemList;
    }

    public final class Compo {
        public TextView timeTxt;
        public TextView informTxt;
        public TextView readTxt;
        public TextView idTxt;
        public LinearLayout Layout_ifRead;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Compo compo = null;
        if (convertView == null) {
            compo = new Compo();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.class_inform_listitem, null);
            compo.idTxt= (TextView) convertView.findViewById(R.id.tv_inform_user_id);
            compo.timeTxt = (TextView) convertView.findViewById(R.id.tv_class_inform_time);
            compo.informTxt = (TextView) convertView.findViewById(R.id.txt_commitstu_sno);
            compo.readTxt= (TextView) convertView.findViewById(R.id.tv_if_read);
            compo.Layout_ifRead= (LinearLayout) convertView.findViewById(R.id.Layout_ifRead);

            if(itemList.get(position).getIf_read().equals("否")){
                BadgeView badgeView = new BadgeView(context);
                //btn是控件
                badgeView.setTargetView(compo.Layout_ifRead);
                //设置相对位置
                badgeView.setBadgeMargin(0, 40, 20, 0);
                //设置显示未读消息条数
                badgeView.setText("2");
                badgeView.setTag("badgeView1");
                badgeView.setMaxHeight(40);
                badgeView.setMaxWidth(40);
                badgeView.setTextColor(Color.parseColor("#d3321b"));
            }
            convertView.setTag(compo);
        } else {
            compo = (Compo) convertView.getTag();
        }
        Integer informId=itemList.get(position).getInform_id();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        compo.idTxt.setText(Integer.toString(itemList.get(position).getInform_user_id()));

        compo.readTxt.setText(itemList.get(position).getIf_read());


        compo.timeTxt.setText(sdf.format(itemList.get(position).getCreate_date_time()));
        compo.informTxt.setText(itemList.get(position).getDetail());
        return convertView;
    }
}
