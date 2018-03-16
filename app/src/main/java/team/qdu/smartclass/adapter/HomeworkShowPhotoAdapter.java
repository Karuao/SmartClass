package team.qdu.smartclass.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.List;

import team.qdu.smartclass.R;

/**
 * Created by 11602 on 2018/3/13.
 */

public class HomeworkShowPhotoAdapter extends SBaseAdapter<ImageItem> {

    public final class Compo {
        public ImageView homeworkPhotoImg;
    }

    public HomeworkShowPhotoAdapter(Context context) {
        super(context);
    }

    public List<ImageItem> getImages() {
            return itemList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Compo compo = null;
        if (convertView == null) {
            compo = new Compo();
            convertView = layoutInflater.inflate(R.layout.itemlist_homework_showphoto, null);
            compo.homeworkPhotoImg = (ImageView) convertView.findViewById(R.id.img_homework_photo);
        } else {
            compo = (Compo) convertView.getTag();
        }

        //设置ListView item
        Glide.with(context).load(itemList.get(position).path).into(compo.homeworkPhotoImg);

        return convertView;
    }
}
