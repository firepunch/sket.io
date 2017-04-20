package sket.model.action;

import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
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
            String g = "https://graph.facebook.com/v2.2/"+FBConnection.FB_APP_ID+
                    "?access_token=" + accessToken;
            URL u = new URL(g);
            HttpsURLConnection c = (HttpsURLConnection)u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    c.getInputStream()));
            String inputLine;
            StringBuffer b = new StringBuffer();
            while ((inputLine = in.readLine()) != null)
                b.append(inputLine + "\n");
            in.close();
            graph = b.toString();
            System.out.println(graph);
        } catch (Exception e) {
            throw new RuntimeException("ERROR in getting FB graph data. " + e);
        }
        return graph;
    }

    public Map getGraphData(String fbGraph) {
        Map fbProfile = new HashMap();
        try {
            JSONObject json = new JSONObject(fbGraph);
            fbProfile.put("id", json.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR in parsing FB graph data. " + e);
        }
        return fbProfile;
    }
}