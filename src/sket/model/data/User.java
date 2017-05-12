package sket.model.data;

public class User {

    private String id;
    private String password;
    private String nick;

    public User(String id, String password, String nick){
        this.id = id;
        this.password = password;
        this.nick = nick;
    }

    public String getId() {
        return id;
    }

    public void setId(String username) {
        this.id = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}