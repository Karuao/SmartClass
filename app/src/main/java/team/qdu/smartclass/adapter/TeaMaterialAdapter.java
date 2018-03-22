package team.qdu.smartclass.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import team.qdu.model.Material;
import team.qdu.smartclass.R;

/**
 * Created by n551 on 2018/1/29.
 */

public class TeaMaterialAdapter extends SBaseAdapter<Material> {

    public TeaMaterialAdapter(Context context, List itemList) {
        super(context);
        this.itemList = itemList;
    }

    public final class Compo {
        public TextView nameTxt;
        public TextView timeTxt;
        public TextView materialidTxt;
        public ImageButton deleteBtn;
        public TextView urlTxt;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Compo compo = null;
        if (convertView == null) {
            compo = new Compo();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.class_resource_admin_listitem, null);
            compo.nameTxt = (TextView) convertView.findViewById(R.id.tv_class_filename);
            compo.timeTxt= (TextView) convertView.findViewById(R.id.tv_class_filetime);
            compo.materialidTxt= (TextView) convertView.findViewById(R.id.tv_materialid);
            compo.deleteBtn= (ImageButton) convertView.findViewById(R.id.deleteResource);
            compo.urlTxt=(TextView)convertView.findViewById(R.id.tv_materialurl);
            convertView.setTag(compo);
        } else {
            compo = (Compo) convertView.getTag();
        }


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        compo.nameTxt.setText(itemList.get(position).getName());
        compo.timeTxt.setText(sdf.format(itemList.get(position).getCreate_date_time()));
        compo.materialidTxt.setText(Integer.toString(itemList.get(position).getMaterial_id()));
        compo.urlTxt.setText(itemList.get(position).getUrl());
/*        compo.deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((View) v.getParent()).findViewById(R.id.tv_materialid);

            }
        });*/
        return convertView;
    }
}
