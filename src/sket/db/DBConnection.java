package sket.db;

import sket.Configure;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.Iterator;
import java.util.List;

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
    public void InsertQuiz(String category, List<String> wordList) throws UnsupportedEncodingException {
        Iterator itr = wordList.iterator();

        while (itr.hasNext()) {
            Object element = itr.next();
            String sql = "INSERT INTO quiz (category, name) VALUES ('" + category + "','" + element + "');";
            try {
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed insert data " + e);
            }
        }
        DBClose();
    }

    /* 한 문제 랜덤으로 선택 */
    public String SelectQuiz() {
        String sql = "SELECT name FROM quiz ORDER BY RAND() LIMIT 1;";
        String quiz = null;
        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed insert data " + e);
        }
        try {
            if(resultSet.next()){
                quiz = resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBClose();
        return quiz;
    }

    /* 소셜로그인 후 정보 삽입 */
    public void InsertUser(String id, String nick, String name) {
        String sql = "INSERT INTO user VALUES ('" + id + "','" + nick + "','" + name + "', 1, 0, 0);";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed insert data " + e);
        }
        DBClose();
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