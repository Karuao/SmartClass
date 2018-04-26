package team.qdu.smartclass.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import team.qdu.core.Lifeful;
import team.qdu.smartclass.R;

/**
 * @author ：程序员小冰
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 * @CSDN博客: http://blog.csdn.net/qq_21376985
 */

public class LoadingDialogUtil {

    private static class DialogEnvironment {
        private Dialog loadingDialog;
        private Context context;

        public DialogEnvironment(Dialog loadingDialog, Context context) {
            this.loadingDialog = loadingDialog;
            this.context = context;
        }
    }

    private static List<DialogEnvironment> dialogEnvironmentList = new ArrayList<>();

    public static void createLoadingDialog(Context context, String msg) {
        Date date = new Date();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v
                .findViewById(R.id.dialog_loading_view);// 加载布局
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        tipTextView.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.MyDialogStyle);// 创建自定义样式dialog
        loadingDialog.setCancelable(true); // 是否可以按“返回键”消失
        loadingDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        /**
         *将显示Dialog的方法封装在这里面
         */
        Window window = loadingDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.PopWindowAnimStyle);
        loadingDialog.show();
        System.out.println(new Date().getTime() - date.getTime());
        dialogEnvironmentList.add(new DialogEnvironment(loadingDialog, context));
    }

    /**
     * 关闭dialog
     *
     * http://blog.csdn.net/qq_21376985
     *note:Rock
     *也可以用((ContextWrapper)dialog.getContext()).getBaseContext()获取Activity
     */
    public static void closeDialog() {
//        for (DialogEnvironment dialogEnvironment : dialogEnvironmentList) {
//            if (((Lifeful) dialogEnvironment.context).isAlive() && dialogEnvironment.loadingDialog != null && dialogEnvironment.loadingDialog.isShowing()) {
//                dialogEnvironment.loadingDialog.dismiss();
//            }
//        }
        for (int i = 0; i < dialogEnvironmentList.size(); i++) {
            if (((Lifeful) dialogEnvironmentList.get(i).context).isAlive() && dialogEnvironmentList.get(i).loadingDialog != null && dialogEnvironmentList.get(i).loadingDialog.isShowing()) {
                dialogEnvironmentList.get(i).loadingDialog.dismiss();
            }
            dialogEnvironmentList.remove(i);
        }
    }

}