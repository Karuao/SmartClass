package team.qdu.smartclass.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.ClassUser;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.SBaseActivity;

/**
 * Created by asus on 2018/3/7.
 */

public class ClassMemberAdapter extends SBaseAdapter<ClassUser> {
    public ClassMemberAdapter(Context context, List itemList) {
        super(context);
        this.itemList = itemList;
    }

    /**
     * 组件集合，对应list.xml中的控件
     *
     * @author Administrator
     */
    public final class Compo {
        public TextView userName;
        public TextView userSno;
        public TextView userExp;
        public TextView userRank;
        public ImageView classMemberImg;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Compo compo = null;
        if (convertView == null) {
            compo = new Compo();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.class_member_listitem, null);
            compo.userName = (TextView) convertView.findViewById(R.id.tv_class_membername2);
            compo.userSno = (TextView) convertView.findViewById(R.id.tv_class_membernum2);
            compo.userExp = (TextView) convertView.findViewById(R.id.tv_class_memberexp2);
            compo.userRank = (TextView) convertView.findViewById(R.id.tv_class_member_rank2) ;
            compo.classMemberImg = (ImageView) convertView.findViewById(R.id.iv_class_memberimg2);
            convertView.setTag(compo);
        } else {
            compo = (Compo) convertView.getTag();
        }
        //绑定数据
        compo.userName.setText(itemList.get(position).getUser().getName());
        compo.userSno.setText(itemList.get(position).getUser().getSno());
        compo.userExp.setText(itemList.get(position).getExp().toString());
        compo.userRank.setText(String.valueOf(position+1));
        final Compo finalCompo = compo;
        //从服务器获取图片绑定到班课成员封面上
        ((SBaseActivity) context).classAppAction.getBitmap(itemList.get(position).getUser().getAvatar(), new ActionCallbackListener<Bitmap>() {
            @Override
            public void onSuccess(Bitmap data, String message) {
                finalCompo.classMemberImg.setImageBitmap(data);
            }
            //设置红点

            @Override
            public void onFailure(String errorEvent, String message) {
            }
        });
        return convertView;
    }
}
