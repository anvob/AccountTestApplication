package com.anvob.accounttestapplication.AccountAuthenticator;

import android.util.Log;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anvob on 06.10.2017.
 */

public class ParseComServerAuthenticate implements ServerAuthenticate{

    static protected final String ENCODING_FORMAT = "UTF-8";
    static protected final boolean DEBUG = false; //TODO SET FALSE TO DEBUG
    static protected final String TAG = "API";
    @Override
    public String userSignUp(String name, String email, String pass, String authType) throws Exception {

        /*String url = "https://api.parse.com/1/users";

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        httpPost.addHeader("X-Parse-Application-Id","XUafJTkPikD5XN5HxciweVuSe12gDgk2tzMltOhr");
        httpPost.addHeader("X-Parse-REST-API-Key", "8L9yTQ3M86O4iiucwWb4JS7HkxoSKo7ssJqGChWx");
        httpPost.addHeader("Content-Type", "application/json");

        String user = "{\"username\":\"" + email + "\",\"password\":\"" + pass + "\",\"phone\":\"415-392-0202\"}";
        HttpEntity entity = new StringEntity(user);
        httpPost.setEntity(entity);

        String authtoken = null;
        try {
            HttpResponse response = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());

            if (response.getStatusLine().getStatusCode() != 201) {
                ParseComError error = new Gson().fromJson(responseString, ParseComError.class);
                throw new Exception("Error creating user["+error.code+"] - " + error.error);
            }


            User createdUser = new Gson().fromJson(responseString, User.class);

            authtoken = createdUser.sessionToken;

        } catch (IOException e) {
            e.printStackTrace();
        }*/
        String lastName = "";
        String phone = "";
        String url = "http://www.beboss.ru/api/userregister";

        Map<String, String> data = new HashMap<String, String>();
        data.put("os", "android");
        data.put("email", email);
        data.put("name", name + (name.equals("")? "":" ") + lastName);
        data.put("phone", phone);
        JSONObject json = readJsonFromUrl(url,true,data);

        String authtoken = json.optString("token");

        return authtoken;
    }

    public static JSONObject readJsonFromUrl(String url, boolean withJson, Map<String, String> data) {
        url = url + ((!url.contains("?")) ? "?" : "&");
        url = (withJson) ? url + "json" : url;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            if (data.size() > 0) {
                for (Map.Entry<String, String> val : data.entrySet()) {
                    pairs.add(new BasicNameValuePair(val.getKey(), val.getValue()));
                }
                post.setEntity(new UrlEncodedFormEntity(pairs, ENCODING_FORMAT));
            }
            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String rd_line;
            StringBuffer jsonText = new StringBuffer();
            while ((rd_line = rd.readLine()) != null) {
                jsonText.append(rd_line);
            }
            if (DEBUG) Log.e(TAG + " POST REQUEST", jsonText.toString());
            try {
                JSONObject json = new JSONObject(jsonText.toString());
                return json;
            } catch (Exception e) {
                Log.e(TAG, "init json error");
            }
        } catch (Exception e) {
            Log.e(TAG, "init open stream error");
        }
        return null;
    }

    @Override
    public String userSignIn(String email, String pass, String authType) throws Exception {

        /*Log.d("udini", "userSignIn");

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://api.parse.com/1/login";


        String query = null;
        try {
            query = String.format("%s=%s&%s=%s", "username", URLEncoder.encode(user, "UTF-8"), "password", pass);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        url += "?" + query;

        HttpGet httpGet = new HttpGet(url);

        httpGet.addHeader("X-Parse-Application-Id", "XUafJTkPikD5XN5HxciweVuSe12gDgk2tzMltOhr");
        httpGet.addHeader("X-Parse-REST-API-Key", "8L9yTQ3M86O4iiucwWb4JS7HkxoSKo7ssJqGChWx");

        HttpParams params = new BasicHttpParams();
        params.setParameter("username", user);
        params.setParameter("password", pass);
        httpGet.setParams(params);
//        httpGet.getParams().setParameter("username", user).setParameter("password", pass);

        String authtoken = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);

            String responseString = EntityUtils.toString(response.getEntity());
            if (response.getStatusLine().getStatusCode() != 200) {
                ParseComError error = new Gson().fromJson(responseString, ParseComError.class);
                throw new Exception("Error signing-in ["+error.code+"] - " + error.error);
            }

            User loggedUser = new Gson().fromJson(responseString, User.class);
            authtoken = loggedUser.sessionToken;

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        Map<String, String> data = new HashMap<String, String>();
        data.put("os", "android");
        data.put("login", email);
        data.put("password", pass);
        String url = "http://www.beboss.ru/api/login";
        JSONObject json = readJsonFromUrl(url, true, data);
        String authtoken = json.optString("token");
        return authtoken;
    }


    private class ParseComError implements Serializable {
        int code;
        String error;
    }
    private class User implements Serializable {

        private String firstName;
        private String lastName;
        private String username;
        private String phone;
        private String objectId;
        public String sessionToken;
        private String gravatarId;
        private String avatarUrl;


        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getSessionToken() {
            return sessionToken;
        }

        public void setSessionToken(String sessionToken) {
            this.sessionToken = sessionToken;
        }

        public String getGravatarId() {
            return gravatarId;
        }

        public void setGravatarId(String gravatarId) {
            this.gravatarId = gravatarId;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }
    }
}
