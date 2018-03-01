package team.qdu.smartclass.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import team.qdu.model.HomeworkAnswerWithBLOBs;
import team.qdu.smartclass.R;

/**
 * Created by 11602 on 2018/2/22.
 */

public class StuHomeworkUnderwayAdapter extends SBaseAdapter<HomeworkAnswerWithBLOBs> {

    public StuHomeworkUnderwayAdapter(Context context, List itemList) {
        super(context);
        this.itemList = itemList;
    }

    //组合集合，对应listitem中的控件
    public final class Compo {
        public TextView homeworkAnswerIdTxt;
        public TextView homeworkNameTxt;
        public TextView homeworkExpTxt;
        public TextView homeworkSubmitNumTxt;
        public TextView homeworkStatusTxt;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Compo compo = null;
        if (convertView == null) {
            compo = new Compo();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.class_homework_underway_listitem, null);
            compo.homeworkAnswerIdTxt = (TextView) convertView.findViewById(R.id.txt_homeworkanswer_underway_id);
            compo.homeworkNameTxt = (TextView) convertView.findViewById(R.id.txt_homework_underway_name);
            compo.homeworkExpTxt = (TextView) convertView.findViewById(R.id.txt_homework_underway_exp);
            compo.homeworkSubmitNumTxt = (TextView) convertView.findViewById(R.id.txt_homework_underway_submitnum);
            compo.homeworkStatusTxt = (TextView) convertView.findViewById(R.id.txt_homework_underway_status);
            convertView.setTag(compo);
        } else {
            compo = (Compo) convertView.getTag();
        }
        //绑定数据
        compo.homeworkAnswerIdTxt.setText(String.valueOf(itemList.get(position).getHomework_answer_id()));
        compo.homeworkNameTxt.setText(itemList.get(position).getHomework().getName());
        compo.homeworkExpTxt.setText(Integer.toString(itemList.get(position).getHomework().getExp()) + "经验");
        compo.homeworkSubmitNumTxt.setText(Integer.toString(itemList.get(position).getHomework().getSubmit_num()) + "人参与");
        compo.homeworkStatusTxt.setText(itemList.get(position).getHomework().getHomework_status());
        return convertView;
    }
}
