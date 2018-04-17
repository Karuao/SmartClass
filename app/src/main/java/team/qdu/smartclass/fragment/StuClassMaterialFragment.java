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
import team.qdu.model.Material_User;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.SBaseActivity;
import team.qdu.smartclass.activity.StuClassMainActivity;
import team.qdu.smartclass.adapter.StuMaterialAdapter;
import team.qdu.smartclass.util.OpenFileUtil;

/**
 * Created by rjmgc on 2018/1/17.
 */

public class StuClassMaterialFragment extends SBaseFragment implements AdapterView.OnItemClickListener {
    ListView listview;
    StuClassMainActivity parentActivity;
    //刷新标志
    public static boolean refreshFlag;
    private View currentPage;
    //标题栏班课名
    private TextView titleBarClassNameTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentPage = inflater.inflate(R.layout.class_tab01, container, false);
        parentActivity = (StuClassMainActivity) getActivity();
        listview = (ListView) currentPage.findViewById(R.id.class_material_listView);
        refreshFlag = false;
        initView();
        getMaterial();
        listview.setOnItemClickListener(this);
        return currentPage;
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

    private void getMaterial() {
        String classid = getClassId();
        String userid = getUserId();
        parentActivity.materialAppAction.getStuMaterial(classid, userid, this, new ActionCallbackListener<List<Material_User>>() {

            @Override
            public void onSuccess(List<Material_User> data, String message) {
                listview.setAdapter(new StuMaterialAdapter(getActivity(), data));
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
        final String material_user_id = ((TextView) view.findViewById(R.id.tv_material_user_id)).getText().toString();
        final String urltail = url;
        final String classid = getClassId();
        final String userid = getUserId();

        if (CheckIfFileExist(url) == false) {
            new AlertDialog.Builder(getContext())
                    .setTitle("提示")
                    .setMessage("确定要下载此资源？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final String url = ((TextView) view.findViewById(R.id.tv_materialurl)).getText().toString();

                            String urltail = url;

                            parentActivity.fileAppAction.cacheFile(urltail, getActivity(), new ActionCallbackListener<File>() {
                                @Override
                                public void onSuccess(File data, String message) {
                                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                    DownloadMaterial(classid, userid, name, material_user_id);
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
        } else {
            OpenFileUtil openFileUtil = new OpenFileUtil();
            OpenFileUtil.openFileByPath(getContext(), url);
        }
    }

    private void DownloadMaterial(String classid, String userid, String name, String material_user_id) {
        parentActivity.materialAppAction.downloadMaterial(classid, userid, name, material_user_id, this, new ActionCallbackListener<Void>() {

            @Override
            public void onSuccess(Void data, String message) {

            }

            @Override
            public void onFailure(String errorEvent, String message) {

            }
        });
    }

    public boolean CheckIfFileExist(final String urlTail) {
        File img = new File(getActivity().getExternalFilesDir(null) + File.separator + urlTail);
        return img.exists();
    }
}