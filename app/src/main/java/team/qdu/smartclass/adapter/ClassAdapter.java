package team.qdu.smartclass.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.Class;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.MainActivity;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        Compo compo = null;
        if (convertView == null) {
            compo = new Compo();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.item_list_mainclass, null);
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
        compo.titleTxt.setText(itemList.get(position).getCourse());
        compo.classNameTxt.setText(itemList.get(position).getName());
        compo.teacherTxt.setText(itemList.get(position).getTeacher());
        final Compo finalCompo = compo;
        //从服务器获取图片绑定到班课封面上
        ((MainActivity) context).classAppAction.getBitmap(itemList.get(position).getAvatar(), new ActionCallbackListener<Bitmap>() {
            @Override
            public void onSuccess(Bitmap data, String message) {
                //已结束班课班课封面在图片之上加一层灰色图层
                if ("已结束".equals(itemList.get(position).getIf_allow_to_join())) {
                    Drawable drawable = new BitmapDrawable(data);
                    drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    finalCompo.classImg.setImageDrawable(drawable);
                } else {
                    finalCompo.classImg.setImageBitmap(data);
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
            }
        });
//        compo.classImg.setImageDrawable(context.getResources().getDrawable((int) itemList.get(position).get("img")));
        return convertView;
    }
}
