package team.qdu.smartclass.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.HomeworkAnswerWithBLOBs;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.SBaseActivity;

/**
 * Created by 11602 on 2018/3/2.
 */

public class HomeworkUncommitAdapter extends SBaseAdapter<HomeworkAnswerWithBLOBs> {

    public HomeworkUncommitAdapter(Context context, List itemList) {
        super(context);
        this.itemList = itemList;
    }

    /**
     * 组件集合，对应list.xml中的控件
     *
     * @author Administrator
     */
    public final class Compo {
        public TextView homeworkAnswerIdTxt;
        public TextView uncommitStuNameTxt;
        public TextView uncommitStuSnoTxt;
        public ImageView uncommitStuAvatarImg;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Compo compo = null;
        if (convertView == null) {
            compo = new Compo();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.class_homework_admin_underway_uncommit_listitem, null);
            compo.homeworkAnswerIdTxt = (TextView) convertView.findViewById(R.id.txt_homeworkanswer_id);
            compo.uncommitStuNameTxt = (TextView) convertView.findViewById(R.id.txt_uncommitstu_name);
            compo.uncommitStuSnoTxt = (TextView) convertView.findViewById(R.id.txt_uncommitstu_sno);
            compo.uncommitStuAvatarImg = (ImageView) convertView.findViewById(R.id.img_uncommitstu_avatar);
            convertView.setTag(compo);
        } else {
            compo = (Compo) convertView.getTag();
        }
        //绑定数据
        compo.homeworkAnswerIdTxt.setText(itemList.get(position).getHomework_answer_id().toString());
        compo.uncommitStuNameTxt.setText(itemList.get(position).getUser().getName());
        compo.uncommitStuSnoTxt.setText(itemList.get(position).getUser().getSno());
        final Compo finalCompo = compo;
        if (!TextUtils.isEmpty(itemList.get(position).getUser().getAvatar())) {
            ((SBaseActivity) context).classAppAction.getBitmap(itemList.get(position).getUser().getAvatar(), new ActionCallbackListener<Bitmap>() {
                @Override
                public void onSuccess(Bitmap data, String message) {
                    finalCompo.uncommitStuAvatarImg.setImageBitmap(data);
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
