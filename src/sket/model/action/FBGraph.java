package sket.model.action;

import org.json.JSONException;
import org.json.JSONObject;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class FBGraph {
    private String accessToken;

    public FBGraph(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getFBGraph() {
        String graph = null;
        try {
            System.out.println(accessToken);
            String g = "https://graph.facebook.com/me?access_token=" + accessToken;
            URL url = new URL(g);
            System.out.println("Attempting to open connection");
            HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
            if (conn.getResponseCode() != 200) {
                System.out.println("Throwing IO Exception Successful on " + url.toString());
                throw new IOException(conn.getResponseMessage());
            }
            //  conn.setRequestProperty("Accept-Charset","UTF-8");
            System.out.println("Attempt Successful");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String json = reader.readLine();
            JsonReader MyJsonReader = Json.createReader(reader);
            JsonObject jsonObject = MyJsonReader.readObject();

            MyJsonReader.close();
            reader.close();

            StringBuffer sb = new StringBuffer();
            sb = sb.append(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR in getting FB graph data. " + e);
        }
        return graph;
    }

    public Map getGraphData(String fbGraph) {
        Map fbProfile = new HashMap();
        try {
            JSONObject json = new JSONObject(fbGraph);
            fbProfile.put("id", json.getString("id"));
            fbProfile.put("first_name", json.getString("first_name"));
            if (json.has("email"))
                fbProfile.put("email", json.getString("email"));
            if (json.has("gender"))
                fbProfile.put("gender", json.getString("gender"));
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR in parsing FB graph data. " + e);
        }
        return fbProfile;
    }
}