package sket.model.action;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 웹 페이지에서 문제를 파싱하는 클래스
 * Created by ymr on 2017-04-20.
 */
public class ParseQuiz {
    // 단어 추출
    public void getWord(String htmlUrl) {
        Document doc = Jsoup.parse(htmlUrl);
        Elements strongs = doc.select("strong");

        for (Element elem : strongs) {
            System.out.println("text: " + elem.text());
            System.out.println("html: " + elem.html());
        }
    }
}
