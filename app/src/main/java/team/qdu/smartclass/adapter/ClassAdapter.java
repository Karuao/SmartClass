package team.qdu.smartclass.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import team.qdu.model.Class;
import team.qdu.smartclass.R;

/**
 * Created by rjmgc on 2018/1/17.
 */
public class ClassAdapter extends SBaseAdapter<Class> {

    public ClassAdapter(Context context, List itemList) {
        super(context);
        this.itemList = itemList;
    }

    /**
     * 组件集合，对应list.xml中的控件
     *
     * @author Administrator
     */
    public final class Compo {
        public TextView classIdTxt;
        public TextView titleTxt;
        public TextView classNameTxt;
        public TextView teacherTxt;
        public ImageView classImg;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Compo compo = null;
        if (convertView == null) {
            compo = new Compo();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.item_listview, null);
            compo.classIdTxt = (TextView) convertView.findViewById(R.id.tv_class_id);
            compo.titleTxt = (TextView) convertView.findViewById(R.id.titleTv);
            compo.classNameTxt = (TextView) convertView.findViewById(R.id.tv_classname);
            compo.teacherTxt = (TextView) convertView.findViewById(R.id.tv_teacher);
            compo.classImg = (ImageView) convertView.findViewById(R.id.img_class);
            convertView.setTag(compo);
        } else {
            compo = (Compo) convertView.getTag();
        }
        //绑定数据
        compo.classIdTxt.setText(Integer.toString(itemList.get(position).getClass_id()));
        compo.titleTxt.setText((String) itemList.get(position).getCourse());
        compo.classNameTxt.setText((String) itemList.get(position).getName());
        compo.teacherTxt.setText((String) itemList.get(position).getTeacher());
//        compo.classImg.setImageDrawable(context.getResources().getDrawable((int) itemList.get(position).get("img")));
        return convertView;
    }
}
