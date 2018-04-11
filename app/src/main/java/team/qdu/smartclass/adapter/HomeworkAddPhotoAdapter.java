package team.qdu.smartclass.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.List;

import team.qdu.smartclass.R;

/**
 * Created by 11602 on 2018/3/13.
 */

public class HomeworkAddPhotoAdapter extends SBaseAdapter<ImageItem> {

    private int maxImgCount;

    public final class Compo {
        public ImageView homeworkPhotoImg;
        public ImageView homeworkDelPhotoImg;
    }

    //添加一个Null值，getView时遇到则不设置图片，即为添加图片按钮
    public HomeworkAddPhotoAdapter(Context context, int maxImgCount) {
        super(context);
        this.maxImgCount = maxImgCount;
        itemList.add(null);
    }

    public List<ImageItem> getImages() {
        if (itemList.size() == maxImgCount) {
            return itemList;
        }
        return new ArrayList(itemList.subList(0, itemList.size() - 1));
    }

    public int getImagesSize() {
        if (itemList.size() == maxImgCount) {
            return itemList.size();
        }
        return itemList.size() - 1;
    }

    //在最后一个空元素前插入数据
    @Override
    public void addItems(List<ImageItem> itemList) {
        this.itemList.addAll(this.itemList.size() - 1, itemList);
        if (this.itemList.size() > maxImgCount) {
            this.itemList.remove(maxImgCount);
        }
        notifyDataSetChanged();
    }

    @Override
    public void setItems(List<ImageItem> itemList) {
        this.itemList.clear();
        this.itemList.addAll(itemList);
        if (this.itemList.size() != maxImgCount) {
            this.itemList.add(null);
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Compo compo = null;
        if (convertView == null) {
            compo = new Compo();
            convertView = layoutInflater.inflate(R.layout.itemlist_homework_addphoto, null);
            compo.homeworkPhotoImg = (ImageView) convertView.findViewById(R.id.img_homework_photo);
            compo.homeworkDelPhotoImg = (ImageView) convertView.findViewById(R.id.img_homework_delphoto);
            convertView.setTag(compo);
        } else {
            compo = (Compo) convertView.getTag();
        }

        //设置ListView item
        if (itemList.get(position) != null) {
            compo.homeworkDelPhotoImg.setVisibility(View.VISIBLE);
            Glide.with(context).load(itemList.get(position).path).into(compo.homeworkPhotoImg);
            //点击删除按钮删除图片
            compo.homeworkDelPhotoImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemList.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        return convertView;
    }
}
