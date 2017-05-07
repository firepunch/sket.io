package sket.model.action;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import sket.db.DBConnection;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 한국어 기초사전의 txt파일에서 단어 추출 후 DB 삽입
 * 설정해야 하는 것
 * 원하는 파일의 경로, 카테고리, 75라인의 DB ID,PW
 * 원하는 파일의 경로(dir), 카테고리(category)
 * Created by firepunch on 2017-05-01.
 */

public class MakeQuiz {
    String dir = "C:\\Temp\\quizData\\test.txt";
    String startWord = "#00 표제어 시작"; // 검색 시작 단어
    String endWord = "#01 구분"; // 검색 끝 단어
    String category = "교통수단"; // DB 삽입 시 카테고리 설정

    public List<String> MakeQuiz() {
        StringBuffer sb = ReadInput();
        List<String> wordList = ExtractWord(sb);
        return wordList;
    }

    public StringBuffer ReadInput() {
        // txt 파일을 읽고 StringBuffer에 저장
        File file = new File(dir);
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e + "\n Invalid directory :: " + dir);
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Some trouble about printing line " + e);
        }
        return stringBuffer;
    }

    public List<String> ExtractWord(StringBuffer sb) {
        // 표제어 = 문제로 보여줄 단어를 추출하고 반환

        List<String> wordList = new ArrayList<String>();

        for (int start = -1; (start = sb.indexOf(startWord, start + 1) + 10) != 9; ) {
            int end = sb.indexOf(endWord, start + 1);
            String extractWord = sb.substring(start, end);
            // 3이라면 접미사이므로 제외
            if (!extractWord.matches(".*3.*")) {
                extractWord = extractWord.replaceAll("[0-9]", "");
                wordList.add(extractWord);
            }
        }
        return wordList;
    }

    public void InsertDB(StringBuffer sb) {
        // DB에 삽입
        // INSERT INTO quiz (category, name) VALUES ('과일', '사과');
        List<String> wordList = ExtractWord(sb);
        Iterator itr = wordList.iterator();

        String url = "jdbc:mysql://localhost:3307/sketio?characterEncoding=euckr";
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "password");
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
            Statement st = conn.createStatement();
            while (itr.hasNext()) {
                Object element = itr.next();
                String sql = "INSERT INTO quiz (category, name) VALUES ('" + category + "','" + element + "');";
                st.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed insert data " + e);
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed closing DB connection " + e);
        }
    }

    public static void main(String args[]) throws IOException {
        String category = "교통수단"; // DB 삽입 시 카테고리 설정
        List<String> wordList;

        MakeQuiz mq = new MakeQuiz();
        DBConnection db = new DBConnection();

        wordList = mq.MakeQuiz();
        db.InsertQuiz(category, wordList);
    }
}