package wandroid.group4.com.myapplication.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import wandroid.group4.com.myapplication.R;
import wandroid.group4.com.myapplication.bean.LoginBean;
import wandroid.group4.com.myapplication.bean.MessageBean;
import wandroid.group4.com.myapplication.msgs.MessageEvent;

/**
 * Created by vondear on 2019/4/9
 * <p>
 * For the brave souls who get this far: You are the chosen ones,
 * the valiant knights of programming who toil away, without rest,
 * fixing our most awful code. To you, true saviors, kings of men,
 * I say this: never gonna give you up, never gonna let you down,
 * never gonna run around and desert you. Never gonna make you cry,
 * never gonna say goodbye. Never gonna tell a lie and hurt you.
 * <p>
 * 致终于来到这里的勇敢的人：
 * 你是被上帝选中的人，是英勇的、不敌辛苦的、不眠不休的来修改我们这最棘手的代码的编程骑士。
 * 你，我们的救世主，人中之龙，我要对你说：永远不要放弃，永远不要对自己失望，永远不要逃走，辜负了自己，
 * 永远不要哭啼，永远不要说再见，永远不要说谎来伤害自己。
 */
public class ChatActivity extends AppCompatActivity {

    private RecyclerView recy;
    private   List<Object> mObjectList = new ArrayList<>();
    private Recyadapter mRecyadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
    }

    private void initView() {
        recy = (RecyclerView) findViewById(R.id.recy);
        recy.setLayoutManager(new LinearLayoutManager(this));
        mRecyadapter = new Recyadapter(this, mObjectList);
        recy.setAdapter(mRecyadapter);
        mRecyadapter.notifyDataSetChanged();
        recy.scrollToPosition(mObjectList.size()-1);
        EventBus.getDefault().register(this);
    }

    //用于接收传MessageEvent这个类的消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowMessageEvent(MessageEvent messageEvent) {
        if(messageEvent.data instanceof LoginBean){
            mObjectList.add(messageEvent.data);
            mRecyadapter.notifyDataSetChanged();
        }else if(messageEvent.data instanceof MessageBean) {
            mObjectList.add(messageEvent.data);
            mRecyadapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
