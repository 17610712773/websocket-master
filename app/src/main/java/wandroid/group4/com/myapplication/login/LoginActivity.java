package wandroid.group4.com.myapplication.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;

import wandroid.group4.com.myapplication.R;
import wandroid.group4.com.myapplication.chat.ChatActivity;
import wandroid.group4.com.myapplication.msgs.MessageEvent;
import wandroid.group4.com.myapplication.server.WebSocketManager;

public class LoginActivity extends AppCompatActivity {

    private EditText editUsername;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        editUsername = (EditText) findViewById(R.id.edit_username);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = getUid();
                String username = editUsername.getText().toString();
                WebSocketManager.getInstance().login(uid,username);
            }
        });
        WebSocketManager.getInstance();
        EventBus.getDefault().register(this);
    }
    //生成一个假的用户UID
    private String getUid(){
        return new Date().getTime()+""+Math.floor(Math.random()*899+100);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleMessage(MessageEvent msg){
        if(msg.Type == MessageEvent.MsgType.LOGIN){
            Intent intent = new Intent(this, ChatActivity.class);
            String s = editUsername.getText().toString();
            intent.putExtra("name",s);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
