package team.qdu.smartclass.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.ClassUser;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.SBaseActivity;

/**
 * 显示班课成员列表
 * Created by asus on 2018/3/7.
 */

public class ClassMemberAdapter extends SBaseAdapter<ClassUser> {

    public int rank=1;
    public int lastExp;

    public ClassMemberAdapter(Context context) {
        super(context);
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
        public TextView classUserId;
        public TextView memberAvatar;
        public ImageView classMemberImg;
       // public TextView myRank;
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
            compo.classMemberImg = (ImageView) convertView.findViewById(R.id.iv_class_memberimg);
            compo.classUserId = (TextView)convertView.findViewById(R.id.member_classUserId);
            compo.memberAvatar = (TextView)convertView.findViewById(R.id.memberAvatar) ;
           // compo.myRank = (TextView)convertView.findViewById(R.id.tv_class_member_rank);
            convertView.setTag(compo);
        } else {
            compo = (Compo) convertView.getTag();
        }
        //绑定数据
        compo.userName.setText(itemList.get(position).getUser().getName());
        compo.userSno.setText(itemList.get(position).getUser().getSno());
        compo.userExp.setText(itemList.get(position).getExp().toString());
        compo.classUserId.setText(Integer.toString(itemList.get(position).getClass_user_id()));
        compo.memberAvatar.setText(itemList.get(position).getUser().getAvatar());
        if(position>0) {
            lastExp = itemList.get(position - 1).getExp();
            if (itemList.get(position).getExp() == lastExp) {
                compo.userRank.setText(String.valueOf(rank));
            } else {
                rank++;
                compo.userRank.setText(String.valueOf(rank));
            }
        }else if(position==0){
            compo.userRank.setText(String.valueOf(rank));
        }
        final Compo finalCompo = compo;
        //从服务器获取图片绑定到班课成员封面上
        if (!TextUtils.isEmpty(itemList.get(position).getUser().getAvatar())) {
            ((SBaseActivity) context).fileAppAction.cacheImg(itemList.get(position).getUser().getAvatar(), new ActionCallbackListener<File>() {
                @Override
                public void onSuccess(File data, String message) {
                    Glide.with(context).load(data.getPath()).into(finalCompo.classMemberImg);
                }

                @Override
                public void onFailure(String errorEvent, String message) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
        return convertView;
    }
}
