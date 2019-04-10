package wandroid.group4.com.myapplication.server;

import android.util.Log;

import com.google.gson.Gson;


import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import wandroid.group4.com.myapplication.bean.LoginBean;
import wandroid.group4.com.myapplication.bean.MessageBean;
import wandroid.group4.com.myapplication.msgs.MessageEvent;

public class WebSocketManager {

    private String server_url = "http://172.16.53.5:4001/";

    private Socket mSocket;
    public WebSocketManager(){
        new Task().run();
    }

    private static WebSocketManager webSocketManager;
    public static WebSocketManager getInstance(){
        synchronized (WebSocketManager.class){
            if(webSocketManager == null){
                webSocketManager = new WebSocketManager();
            }
            return webSocketManager;
        }
    }

    public void login(String uid,String username){
        JSONObject user = new JSONObject();
        try {
            user.put("userid",uid);
            user.put("username",username);
            mSocket.emit("login",user.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void dataa(int type,String userid, String username,String content){
        JSONObject data = new JSONObject();
        try {
            data.put("type",type);
            data.put("userid",userid);
            data.put("username",username);
            data.put("content",content);
            mSocket.send(data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    //链接在状态
    private Emitter.Listener connListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("conn",args.toString());
        }
    };


    private Emitter.Listener fa = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };
    //接收新消息
    private Emitter.Listener newMsgListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("msg",args.toString());
            if(args.length > 0){
                MessageEvent msgEvt1 = new MessageEvent();
                msgEvt1.Type = MessageEvent.MsgType.CHAT;
                msgEvt1.Code = 200;
                msgEvt1.data = new Gson().fromJson(args[0].toString(), MessageBean.class);
                EventBus.getDefault().post(msgEvt1);
            }
        }
    };


    //连接断开
    private Emitter.Listener connErrorListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("error",args.toString());
        }
    };

    //连接超时
    private Emitter.Listener connTimeOut = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("timeout",args.toString());
        }
    };

    //登录
    private Emitter.Listener loginListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("login",args.toString());
            if(args.length > 0){
                MessageEvent msgEvt = new MessageEvent();
                msgEvt.Type = MessageEvent.MsgType.LOGIN;
                msgEvt.Code = 200;
                msgEvt.data = new Gson().fromJson(args[0].toString(), LoginBean.class);
                EventBus.getDefault().post(msgEvt);
            }
        }
    };




    //初始化连接
    class Task implements Runnable{

        @Override
        public void run() {
            try {
                mSocket = IO.socket(server_url);
                mSocket.on(Socket.EVENT_CONNECT,connListener);// 连接成功
                mSocket.on(Socket.EVENT_MESSAGE,newMsgListener);//接受消息
                mSocket.on("login",loginListener);//登陆
                mSocket.on(Socket.EVENT_CONNECT_TIMEOUT,connTimeOut);//连接超时
                mSocket.off(Socket.EVENT_CONNECT,connErrorListener);//断开连接
                mSocket.connect();
            }catch (URISyntaxException e){
                e.printStackTrace();
            }

        }
    }


}
