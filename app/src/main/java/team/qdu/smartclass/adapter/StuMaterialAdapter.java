package team.qdu.smartclass.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import team.qdu.model.Material;
import team.qdu.smartclass.R;

/**
 * Created by n551 on 2018/1/29.
 */

public class StuMaterialAdapter extends SBaseAdapter<Material> {

    public StuMaterialAdapter(Context context, List itemList) {
        super(context);
        this.itemList = itemList;
    }

    public final class Compo {
        public TextView nameTxt;
        public TextView timeTxt;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Compo compo = null;
        if (convertView == null) {
            compo = new Compo();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.class_resource_listitem, null);
            compo.nameTxt = (TextView) convertView.findViewById(R.id.tv_class_filename);
            compo.timeTxt= (TextView) convertView.findViewById(R.id.tv_class_filetime);
            convertView.setTag(compo);
        } else {
            compo = (Compo) convertView.getTag();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        compo.nameTxt.setText(itemList.get(position).getName());
        compo.timeTxt.setText(sdf.format(itemList.get(position).getCreate_date_time()));
        return convertView;
    }
}
