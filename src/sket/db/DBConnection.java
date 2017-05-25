package sket.db;

import org.json.JSONObject;
import sket.Configure;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.*;

/**
 * DB ID/PW 설정하기
 * Created by ymr on 2017-05-02.
 */
public class DBConnection {
    private Connection conn = null;
    private static Statement statement;
    private static ResultSet resultSet;

    public DBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(Configure.url, Configure.dbUser, Configure.dbPW);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Failed connect DriverManager " + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed insert data " + e);
        }
    }

    /* 새로운 문제 삽입 */
    public void insertQuiz(String category, List<String> wordList) throws UnsupportedEncodingException {
        Iterator itr = wordList.iterator();

        while (itr.hasNext()) {
            Object element = itr.next();
            String query = "INSERT INTO quiz (category, name) VALUES ('" + category + "','" + element + "');";
            try {
                statement.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed insert data " + e);
            }
        }
        DBClose();
    }

    /* 한 문제 랜덤으로 선택 */
    public String selectQuiz() {
        String query = "SELECT name FROM quiz ORDER BY RAND() LIMIT 1;";
        String quiz = null;
        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed select quiz data " + e);
        }
        try {
            if (resultSet.next()) {
                quiz = resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBClose();
        return quiz;
    }

    /* 소셜로그인 후 정보 삽입 */
    public void insertUser(String id, String nick) throws SQLException {
        String query;

        if (nick != null && !nick.isEmpty()) {
            int rowCnt = 0;

            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM user");
            while (rs.next()) {
                rowCnt = rs.getInt("COUNT(*)");
            }
            query = "INSERT INTO user VALUES ('" + id + "','nick" + rowCnt + "', 1, 300, 0, 0);";
            System.out.println("sq  " + query);
        } else {
            query = "INSERT INTO user VALUES ('" + id + "','" + nick + "', 1, 300, 0, 0);";
        }
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed insert user data " + e);
        }
        DBClose();
    }

    /* 회원정보 조회 */
    public JSONObject selectUser(String id, String type) throws SQLException {
        JSONObject jsonObject = new JSONObject();
        String query = "SELECT * FROM user WHERE id=" + id;
        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed select user data " + e);
        }
        if (resultSet.next()) {
            jsonObject.put("type", type);
            jsonObject.put("id", resultSet.getString("id"));
            jsonObject.put("nick", resultSet.getString("nick"));
            jsonObject.put("level", resultSet.getString("level"));
            jsonObject.put("limitExp", resultSet.getString("limitexp"));
            jsonObject.put("totalExp", resultSet.getString("totalexp"));
            jsonObject.put("curExp", resultSet.getString("curexp"));
        } else {
            jsonObject.put("id", "null");
        }
        return jsonObject;
    }

    /* 게스트인지 확인 */
    public boolean isGuest(String id) throws SQLException {
        String query = "SELECT * FROM user WHERE id=" + id;
        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed isGuest in DB " + e);
        }

        DBClose();
        if (resultSet.next()) {
            return false;
        } else {
            return true;
        }
    }

    private void DBClose() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed closing DB connection " + e);
        }
    }
}