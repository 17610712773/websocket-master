package wandroid.group4.com.myapplication.bean;

public class LoginBean {

    /**
     * onlineUsers : null
     * onlineCount : 0
     * user : {"userid":"1554780954386606","username":"heu"}
     */

    private Object onlineUsers;
    private int onlineCount;
    private String user;

    public Object getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(Object onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
