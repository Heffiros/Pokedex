package com.example.max.pokedex;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class jsonReader extends AsyncTask<String, Void, JSONArray> {

    private HttpClient client;
    static InputStream is = null;
    static JSONArray jObj = null;
    static String json = "";

    public JSONArray result;

    public jsonReader() {
        this.client = new DefaultHttpClient();
    }

    @Override
    protected JSONArray doInBackground(String... params) {
        try {
            HttpResponse response = this.client.execute(new HttpGet(params[0]));
            is = response.getEntity().getContent();
        } catch (Exception e) { Log.e("RPC", "Exception", e); }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        try {
            jObj = new JSONArray(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jObj;
    }
}