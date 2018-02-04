package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import team.qdu.core.ActionCallbackListener;
import team.qdu.smartclass.R;

/**
 * Created by n551 on 2018/2/1.
 */

public class CreateInformActivity extends SBaseActivity{
    private EditText detailEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_inform_admin_push);
        initView();
    }

    private  void initView(){
        detailEdt= (EditText) findViewById(R.id.Inform_Detail);
    }

    public void toPush(View view){
        String classid = getIntent().getStringExtra("classId");
        String detail=detailEdt.getText().toString();

        this.informAppAction.createInform(classid,detail,new ActionCallbackListener<Void>(){

            @Override
            public void onSuccess(Void data, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateInformActivity.this, TeaClassMainActivity.class);
                intent.putExtra("classId", getIntent().getStringExtra("classId"));
                startActivity(intent);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void toBack(View view) {
        finish();
    }
}
