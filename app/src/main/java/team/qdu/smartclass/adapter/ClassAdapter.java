package team.qdu.smartclass.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import team.qdu.smartclass.R;

/**
 * Created by rjmgc on 2018/1/17.
 */
public class ClassAdapter extends SBaseAdapter<Map<String, Object>> {

    public ClassAdapter(Context context, List itemList) {
        super(context);
        this.itemList = itemList;
    }

    /**
     * 组件集合，对应list.xml中的控件
     *
     * @author Administrator
     */
    public final class Zujian {
        public TextView titleTxt;
        public TextView classNameTxt;
        public TextView teacherTxt;
        public ImageView classImg;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Zujian zujian = null;
        if (convertView == null) {
            zujian = new Zujian();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.item_listview, null);
            zujian.titleTxt = (TextView) convertView.findViewById(R.id.titleTv);
            zujian.classNameTxt = (TextView) convertView.findViewById(R.id.tv_classname);
            zujian.teacherTxt = (TextView) convertView.findViewById(R.id.tv_teacher);
            zujian.classImg = (ImageView) convertView.findViewById(R.id.img_class);
            convertView.setTag(zujian);
        } else {
            zujian = (Zujian) convertView.getTag();
        }
        //绑定数据
        zujian.titleTxt.setText((String) itemList.get(position).get("title"));
        zujian.classNameTxt.setText((String) itemList.get(position).get("info"));
        zujian.teacherTxt.setText((String) itemList.get(position).get("teacher"));
        zujian.classImg.setImageDrawable(context.getResources().getDrawable((int) itemList.get(position).get("img")));
        return convertView;
    }
}
