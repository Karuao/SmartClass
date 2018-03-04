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
import team.qdu.smartclass.activity.CheckHomworkCommitStatusActivity;

/**
 * Created by 11602 on 2018/3/2.
 */

public class HomeworkNotEvaluateAdapter extends SBaseAdapter<HomeworkAnswerWithBLOBs> {

    public HomeworkNotEvaluateAdapter(Context context, List itemList) {
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
        public TextView notEvaluateStuNameTxt;
        public TextView notEvaluateStuSnoTxt;
        public ImageView notEvaluateStuAvatarImg;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Compo compo = null;
        if (convertView == null) {
            compo = new Compo();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.class_homework_admin_evaluate_notevaluate_listitem, null);
            compo.homeworkAnswerIdTxt = (TextView) convertView.findViewById(R.id.txt_homeworkanswer_id);
            compo.notEvaluateStuNameTxt = (TextView) convertView.findViewById(R.id.txt_notevaluatestu_name);
            compo.notEvaluateStuSnoTxt = (TextView) convertView.findViewById(R.id.txt_notevaluatestu_sno);
            compo.notEvaluateStuAvatarImg = (ImageView) convertView.findViewById(R.id.img_notevaluatestu_avatar);
            convertView.setTag(compo);
        } else {
            compo = (Compo) convertView.getTag();
        }
        //绑定数据
        compo.homeworkAnswerIdTxt.setText(itemList.get(position).getHomework_answer_id().toString());
        compo.notEvaluateStuNameTxt.setText(itemList.get(position).getUser().getName());
        compo.notEvaluateStuSnoTxt.setText(itemList.get(position).getUser().getSno());
        //从服务器获取图片绑定到班课封面上
        final Compo finalCompo = compo;
        if (!TextUtils.isEmpty(itemList.get(position).getUser().getAvatar())) {
            ((CheckHomworkCommitStatusActivity) context).classAppAction.getBitmap(itemList.get(position).getUser().getAvatar(), new ActionCallbackListener<Bitmap>() {
                @Override
                public void onSuccess(Bitmap data, String message) {
                    finalCompo.notEvaluateStuAvatarImg.setImageBitmap(data);
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
