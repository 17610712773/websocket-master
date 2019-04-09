package wandroid.group4.com.myapplication.msgs;

//数据抽取类
public class MessageEvent<T> {

    public MsgType Type;  //消息类型
    public int Code;     //消息状态值
    public String States; //消息状态描述 可有可无

    public T data;  //消息内容

    //枚举，消息类型
    public enum MsgType {
        LOGIN,
        CHAT,
    }

}
