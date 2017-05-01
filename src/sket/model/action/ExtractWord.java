package sket.model.action;

import java.io.*;

/**
 * 1. 한국어 기초사전의 txt파일에서 단어 추출
 * "- #00 표제어 시작"로 시작한다.
 * 2. 쿼리문 생성
 *
 * Created by firepunch on 2017-04-27.
 */
public class ExtractWord {
    String baseDir = "C:\\Temp";    //검색할 디렉토리
    String word = "- #00 표제어 시작";         //검색할 단어
    String save = "result.txt";       //검색결과가 저장된 파일명

    public File ReadInput() {
        File dir;
        try {
            //읽어들일 디렉토리의 객체
            dir = new File(baseDir);
        } catch (IOException e) {
//            if(!dir.isDirectory()) {
            e.printStackTrace();
            throw new RuntimeException(baseDir+" is not directory or exist " + e);
            System.exit(0);
        }
        return dir;
    }

    public void MakeOutput() {
        //저장할 파일 output stream 생성
        PrintWriter writer = new PrintWriter(new FileWriter(save));

        //읽어들일 파일 input stream 선언
        BufferedReader br = null;
    }

    public void MakeQuery() {
        //해당 디렉토리의 모든 파일을 리스트화
        File[] files = dir.listFiles();
        for(int i = 0; i < files.length; i++) {
            //파일이 아닌 경우 continue
            if(!files[i].isFile()) {
                continue;
            }

            //input stream object 생성
            br = new BufferedReader(new FileReader(files[i]));
            String line = "";

            //각 파일의 한 라인씩 읽어들인다.
            while((line = br.readLine()) != null) {
                //라인 내용중 검색하고자 단어가 있으면 파일에 기록한다.
                if(line.indexOf(word) != -1) {
                    writer.write(word + "=" + files[i].getAbsolutePath());
                }
            }

            writer.flush();
        }

        //input stream close.
        br.close();

        //output stream close.
        writer.close();
    }

    public static void main(String args[]) throws IOException {
        ExtractWord ew = new ExtractWord();
    }
}