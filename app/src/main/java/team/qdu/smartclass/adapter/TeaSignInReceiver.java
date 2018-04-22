package team.qdu.smartclass.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;
import team.qdu.smartclass.SApplication;
import team.qdu.smartclass.activity.TeaMemberSigniningActivity;

import static android.content.ContentValues.TAG;

/**
 * Created by asus on 2018/3/25.
 */

public class TeaSignInReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "onReceive - " + intent.getAction());

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
           // ((TeaMemberSigniningActivity) SApplication.getActivityList().get(0)).getSignInStudentAdapter().addItems();
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            System.out.println("收到了自定义消息："+bundle.getString(JPushInterface.EXTRA_MESSAGE));
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
            ((TeaMemberSigniningActivity) SApplication.getActivity(TeaMemberSigniningActivity.class)).getSignInStudent();
            ((TeaMemberSigniningActivity) SApplication.getActivity(TeaMemberSigniningActivity.class)).signInStuNum.setText(bundle.getString(JPushInterface.EXTRA_MESSAGE));
//            ((TeaMemberSigniningActivity) SApplication.getActivityList().get(0)).getSignInStudent();
//            ((TeaMemberSigniningActivity) SApplication.getActivityList().get(0)).signInStuNum.setText(bundle.getString(JPushInterface.EXTRA_MESSAGE));
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            System.out.println("收到了通知");
            // 在这里可以做些统计，或者做些其他工作
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            System.out.println("用户点击打开了通知");
            // 在这里可以自己写代码去定义用户点击后的行为
            Intent i = new Intent(context, TeaMemberSigniningActivity.class);  //自定义打开的界面
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }
    }

