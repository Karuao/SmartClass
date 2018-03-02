package team.qdu.smartclass.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import team.qdu.model.User;
import team.qdu.smartclass.R;

/**
 * Created by n551 on 2018/2/26.
 */

public class InformCheckedAdapter extends SBaseAdapter<User> {

    public InformCheckedAdapter(Context context, List itemList) {
        super(context);
        this.itemList=itemList;
    }
    public final class Compo {
        public TextView NameTxt;
        public TextView NumberTxt;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Compo compo = null;
        if (convertView == null) {
            compo = new Compo();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.class_inform_admin_people_listitem, null);
            compo.NameTxt= (TextView) convertView.findViewById(R.id.tv_checked_name);
            compo.NumberTxt = (TextView) convertView.findViewById(R.id.tv_checked_number);
            convertView.setTag(compo);
        } else {
            compo = (Compo) convertView.getTag();
        }
        compo.NameTxt.setText((itemList.get(position).getName()));
        compo.NumberTxt.setText(itemList.get(position).getAccount());
        return convertView;
    }
}
