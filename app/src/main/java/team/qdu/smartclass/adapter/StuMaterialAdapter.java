package team.qdu.smartclass.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import team.qdu.model.Material_User;
import team.qdu.smartclass.R;

/**
 * Created by n551 on 2018/1/29.
 */

public class StuMaterialAdapter extends SBaseAdapter<Material_User> {

    public StuMaterialAdapter(Context context, List itemList) {
        super(context);
        this.itemList = itemList;
    }

    public final class Compo {
        public TextView nameTxt;
        public TextView timeTxt;
        public TextView urlTxt;
        public TextView materialidTxt;
        public TextView materialuseridTxt;
        public ImageView resourceImg;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Compo compo = null;
        if (convertView == null) {
            compo = new Compo();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.class_resource_listitem, null);
            compo.nameTxt = (TextView) convertView.findViewById(R.id.tv_class_filename);
            compo.timeTxt= (TextView) convertView.findViewById(R.id.tv_class_filetime);
            compo.urlTxt=(TextView)convertView.findViewById(R.id.tv_materialurl);
            compo.materialidTxt= (TextView) convertView.findViewById(R.id.tv_materialid);
            compo.materialuseridTxt= (TextView) convertView.findViewById(R.id.tv_material_user_id);
            compo.resourceImg= (ImageView) convertView.findViewById(R.id.iv_class_file);
            convertView.setTag(compo);
        } else {
            compo = (Compo) convertView.getTag();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        compo.nameTxt.setText(itemList.get(position).getName());
        compo.timeTxt.setText(sdf.format(itemList.get(position).getCreate_date_time()));
        compo.materialidTxt.setText(Integer.toString(itemList.get(position).getMaterial_id()));
        compo.materialuseridTxt.setText(Integer.toString(itemList.get(position).getMaterial_user_id()));
        compo.urlTxt.setText(itemList.get(position).getUrl());
        String ext = MimeTypeMap.getFileExtensionFromUrl((String) compo.urlTxt.getText());
        if(ext.equals("pptx")||ext.equals("ppt")){
            compo.resourceImg.setImageResource(R.drawable.ppt);
        }
        else if(ext.equals("doc")||ext.equals("docx")){
            compo.resourceImg.setImageResource(R.drawable.word);
        }
        else if(ext.equals("xlsx")){
            compo.resourceImg.setImageResource(R.drawable.excel);
        }
        else if(ext.equals("rar")||ext.equals("zip")){
            compo.resourceImg.setImageResource(R.drawable.zip);
        }
        else if(ext.equals("txt")){
            compo.resourceImg.setImageResource(R.drawable.txt);
        }
        return convertView;
    }
}
