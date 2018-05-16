package team.qdu.smartclass.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
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

    int rank = 1;
    int lastExp;
    private List rankList = new ArrayList();


    public ClassMemberAdapter(Context context) {
        super(context);
    }

    @Override
    public void setItems(List<ClassUser> itemList){
        for(int i = 0 ;i < itemList.size();i++ ){
            if(i > 0) {
                lastExp = itemList.get(i - 1).getExp();
                if (itemList.get(i).getExp() == lastExp) {
                    rankList.add(i,rank);
                } else {
                    rank++;
                    rankList.add(i,rank);
                }
            }else if(i == 0){
                rankList.add(i,rank);
            }
        }
        super.setItems(itemList);
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
        public TextView memberId;
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
            compo.memberId = (TextView)convertView.findViewById(R.id.memberId);
           // compo.myRank = (TextView)convertView.findViewById(R.id.tv_class_member_rank);
            convertView.setTag(compo);
        } else {
            compo = (Compo) convertView.getTag();
        }
        //绑定数据
        compo.userRank.setText(rankList.get(position).toString());
        compo.classMemberImg.setImageResource(R.mipmap.ic_useravatar_def);
        compo.userName.setText(itemList.get(position).getUser().getName());
        compo.userSno.setText(itemList.get(position).getUser().getSno());
        compo.userExp.setText(itemList.get(position).getExp().toString());
        compo.classUserId.setText(Integer.toString(itemList.get(position).getClass_user_id()));
        compo.memberAvatar.setText(itemList.get(position).getUser().getAvatar());
        compo.memberId.setText(itemList.get(position).getUser().getUser_id().toString());
        final Compo finalCompo = compo;
        //从服务器获取图片绑定到班课成员封面上
        if (!TextUtils.isEmpty(itemList.get(position).getUser().getAvatar())) {
            ((SBaseActivity) context).fileAppAction.cacheImg(itemList.get(position).getUser().getAvatar(), context, new ActionCallbackListener<File>() {
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

    public void initRank(){
        rank =1;
    }
}
