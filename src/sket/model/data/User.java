package sket.model.data;

public class User {

    private String id;
    private String nick;
    private int level = 1;
    private int limitExp = 300;
    private int totalExp = 0;
    private int curExp = 0;

    /* oauth 로그인 시 생성자 */
    public User(String id, String nick, int level, int limitExp, int totalExp, int curExp) {
        this.id = id;
        this.nick = nick;
        this.level = level;
        this.limitExp = limitExp;
        this.totalExp = totalExp;
        this.curExp = curExp;
    }

    /* 게스트 또는 신규 로그인 생성자 */
    public User(String id, String nick, int level, int totalExp, int curExp) {
        this.id = id;
        this.nick = nick;
        this.level = level;
        this.limitExp = 300;
        this.totalExp = totalExp;
        this.curExp = curExp;
    }

    public int getLimitExp() {
        return limitExp;
    }

    public void setLimitExp(int limitExp) {
        this.limitExp = limitExp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(int totalExp) {
        this.totalExp = totalExp;
    }

    public int getCurExp() {
        return curExp;
    }

    public void setCurExp(int curExp) {
        this.curExp = curExp;
    }
}