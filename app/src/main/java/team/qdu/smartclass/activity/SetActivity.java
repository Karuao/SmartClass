package team.qdu.smartclass.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import team.qdu.smartclass.R;


/**
 * 设置页面
 * <p>
 * Created by Rock on 2017/4/23.
 */
public class SetActivity extends SBaseActivity {
    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set);
        logout= (Button) findViewById(R.id.logout);
    }


    public void toLogout(View view) {
        resetSprfMain();
        startActivity(new Intent(SetActivity.this, LoginActivity.class));
        SetActivity.this.finish();

    }
    public void resetSprfMain(){
        sprfMain= PreferenceManager.getDefaultSharedPreferences(this);
        editorMain=sprfMain.edit();
        editorMain.putBoolean("main",false);
        editorMain.commit();
    }

    public void toBack(View view) {
        finish();
    }
}