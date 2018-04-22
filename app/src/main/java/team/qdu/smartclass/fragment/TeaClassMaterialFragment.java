package team.qdu.smartclass.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.Material;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.SBaseActivity;
import team.qdu.smartclass.activity.TeaClassMainActivity;
import team.qdu.smartclass.adapter.TeaMaterialAdapter;
import team.qdu.smartclass.util.OpenFileUtil;

/**
 * Created by rjmgc on 2018/1/17.
 */

public class TeaClassMaterialFragment extends SBaseFragment implements AdapterView.OnItemClickListener {
    ListView listview;
    TeaClassMainActivity parentActivity;
    //刷新标志
    public static boolean refreshFlag;
    private View currentPage;
    //标题栏班课名
    private boolean isPrepared;
    private TextView titleBarClassNameTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentPage = inflater.inflate(R.layout.class_tab01_admin, container, false);
        parentActivity = (TeaClassMainActivity) getActivity();
        listview = (ListView) currentPage.findViewById(R.id.class_material_listView);
        refreshFlag = false;
        initView();
        isPrepared = true;
        lazyLoad();
        listview.setOnItemClickListener(this);
        return currentPage;
    }
    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        getMaterial();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (refreshFlag) {
                getMaterial();
            refreshFlag = false;
        }
    }

    public void initView() {
        titleBarClassNameTxt = (TextView) currentPage.findViewById(R.id.txt_titlebar_classname);
        titleBarClassNameTxt.setText(((SBaseActivity) getActivity()).getCourse());
    }

    public void getMaterial() {
        parentActivity.materialAppAction.getTeaMaterial(parentActivity.getClassId(), this, new ActionCallbackListener<List<Material>>() {

            @Override
            public void onSuccess(List<Material> data, String message) {
                listview.setAdapter(new TeaMaterialAdapter(getActivity(), data));
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
        final String name = ((TextView) view.findViewById(R.id.tv_class_filename)).getText().toString();
        final String url = ((TextView) view.findViewById(R.id.tv_materialurl)).getText().toString();
        final String urltail = url;


        if (CheckIfFileExist(url) == false) {
            new AlertDialog.Builder(this.getContext())
                    .setTitle("提示")
                    .setMessage("确定要下载此资源？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            parentActivity.fileAppAction.cacheFile(urltail, getActivity(), new ActionCallbackListener<File>() {
                                @Override
                                public void onSuccess(File data, String message) {
                                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                    OpenFileUtil openFileUtil = new OpenFileUtil();
                                    OpenFileUtil.openFileByPath(getContext(), url);
                                }

                                @Override
                                public void onFailure(String errorEvent, String message) {
                                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }
        else {
            OpenFileUtil openFileUtil = new OpenFileUtil();
            OpenFileUtil.openFileByPath(getContext(), url);
        }
    }


    public boolean CheckIfFileExist(final String urlTail){
        File img = new File(getActivity().getExternalFilesDir(null) + File.separator + urlTail);
        return img.exists();
    }

}







