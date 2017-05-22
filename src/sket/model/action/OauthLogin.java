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
    public JSONObject getRcvJson(HttpServletRequest req, String starter) throws IOException {
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
        return rcvJson;
    }
}
