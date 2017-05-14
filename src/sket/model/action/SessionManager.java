package sket.model.action;

import sket.model.data.User;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by KwonJH on 2017-05-12.
 */
public class SessionManager {
    private static ArrayList<HttpSession> sessionList = new ArrayList<>();

    public static ArrayList<HttpSession> getSessionList(){
        return sessionList;
    }

    public static void addSession(HttpSession session){
        sessionList.add(session);
    }

    public static String getUserIdEqualSession(HttpSession session) {
        for(HttpSession httpSession : sessionList){
            if(httpSession.equals(session)){
                return ((User) httpSession.getAttribute("user")).getId();
            }
        }
        return null;
    }

    public static User getUserEqualSession(HttpSession session){
        for(HttpSession httpSession : sessionList){
            if(httpSession.equals(session)){
                return ((User)(httpSession.getAttribute("user")));
            }
        }
        return null;
    }
}
