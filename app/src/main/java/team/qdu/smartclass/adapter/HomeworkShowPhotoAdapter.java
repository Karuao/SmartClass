package team.qdu.smartclass.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import team.qdu.smartclass.R;

/**
 * Created by 11602 on 2018/3/13.
 */

public class HomeworkShowPhotoAdapter extends SBaseAdapter<Bitmap> {

    public final class Compo {
        public ImageView homeworkPhotoImg;
    }

    public HomeworkShowPhotoAdapter(Context context) {
        super(context);
    }

    public List<Bitmap> getImages() {
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
        compo.homeworkPhotoImg.setImageBitmap(itemList.get(position));

        return convertView;
    }
}
