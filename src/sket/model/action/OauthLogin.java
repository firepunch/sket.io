package sket.model.action;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by ymr on 2017-05-22.
 */
public class OauthLogin {
    /* 구글 혹은 페북에서 제공해주는 json 전처리 */
    public JSONObject getRcvJson(HttpServletRequest req, String type, String starter) throws IOException {
        JSONObject rcvJson;

        try {
            StringBuffer jb = new StringBuffer();
            String line = null;
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
            rcvJson = new JSONObject(jb.toString());
            rcvJson = rcvJson.getJSONObject(starter);
        } catch (JSONException e) {
            throw new IOException("Error parsing JSON request string");
        }

        rcvJson = initSendJson(rcvJson, type);
        return rcvJson;
    }

    /* sendJson 초기화 */
    private JSONObject initSendJson(JSONObject rcvJson, String type) {
        JSONObject sendJson = new JSONObject();

        sendJson.put("type", type);
        if (type.equals("GOOGLE")) {
//            String token = rcvJson.getJSONObject("tokenObj").getString("access_token");
            sendJson.put("id", rcvJson.getString("googleId"));
            sendJson.put("picture", rcvJson.getJSONObject("profileObj").getString("imageUrl"));
        } else if (type.equals("FACEBOOK")) {
//            String token = rcvJson.getString("accessToken");
            sendJson.put("id", rcvJson.getString("id"));
            sendJson.put("picture", rcvJson.getJSONObject("picture").getJSONObject("data").getString("url"));
        }

        return sendJson;
    }
}
