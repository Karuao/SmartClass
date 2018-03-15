package team.qdu.smartclass.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jauker.widget.BadgeView;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.ClassUser;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.SBaseActivity;

/**
 * Created by rjmgc on 2018/1/17.
 */
public class ClassAdapter extends SBaseAdapter<ClassUser> {

    public ClassAdapter(Context context) {
        super(context);
    }

    /**
     * 组件集合，对应list.xml中的控件
     *
     * @author Administrator
     */
    public final class Compo {
//        public TextView classUserIdTxt;
        public TextView classIdTxt;
        public TextView titleTxt;
        public TextView classNameTxt;
        public TextView teacherTxt;
        public ImageView classImg;
        public BadgeView badgeView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Compo compo = null;
        if (convertView == null) {
            compo = new Compo();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.itemlist_mainclass, null);
            //传递classUserId更好，不过要重构代码太多
//            compo.classUserIdTxt = (TextView) convertView.findViewById(R.id.txt_classUserId);
            compo.classIdTxt = (TextView) convertView.findViewById(R.id.txt_classId);
            compo.titleTxt = (TextView) convertView.findViewById(R.id.txt_title);
            compo.classNameTxt = (TextView) convertView.findViewById(R.id.txt_className);
            compo.teacherTxt = (TextView) convertView.findViewById(R.id.tv_teacher);
            compo.classImg = (ImageView) convertView.findViewById(R.id.img_class);
            compo.badgeView = new BadgeView(context);
            convertView.setTag(compo);
        } else {
            compo = (Compo) convertView.getTag();
        }
        //绑定数据
//        compo.classUserIdTxt.setText(Integer.toString(itemList.get(position).getClass_user_id()));
        compo.classIdTxt.setText(Integer.toString(itemList.get(position).getMy_class().getClass_id()));
        compo.titleTxt.setText(itemList.get(position).getMy_class().getCourse());
        compo.classNameTxt.setText(itemList.get(position).getMy_class().getName());
        compo.teacherTxt.setText(itemList.get(position).getUser().getName());
        final Compo finalCompo = compo;
        if (!TextUtils.isEmpty(itemList.get(position).getMy_class().getAvatar())) {
            //从服务器获取图片绑定到班课封面上
            ((SBaseActivity) context).classAppAction.getBitmap(itemList.get(position).getMy_class().getAvatar(), new ActionCallbackListener<Bitmap>() {
                @Override
                public void onSuccess(Bitmap data, String message) {
                    //取消已结束班课
                    //已结束班课班课封面在图片之上加一层灰色图层
//                if ("已结束".equals(itemList.get(position).getIf_allow_to_join())) {
//                    Drawable drawable = new BitmapDrawable(data);
//                    drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
//                    finalCompo.classImg.setImageDrawable(drawable);
//                } else {
//                    finalCompo.classImg.setImageBitmap(data);
//                }
                    finalCompo.classImg.setImageBitmap(data);
                }

                @Override
                public void onFailure(String errorEvent, String message) {
                }
            });
        }

        //设置红点
        if ("是".equals(itemList.get(position).getIf_new_class_thing())) {
            compo.badgeView.setMaxHeight(40);
            compo.badgeView.setMaxWidth(40);
            compo.badgeView.setBadgeMargin(0, 0, 5, 0);
            compo.badgeView.setTextColor(Color.parseColor("#CCFF0000"));
            compo.badgeView.setText("1");
            compo.badgeView.setTargetView(compo.teacherTxt);
            compo.badgeView.setTag("badgeView");
        }
        return convertView;
    }
}
