package com.example.roboticVacuum.service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.roboticVacuum.dto.HttpUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HttpService extends AsyncTask<String, Void, String> {
    public static String RECORD_NAME = "test";
    public static boolean IS_RECORDING = false;

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
/*
        HttpUrl.INSERT_URL query
        INSERT INTO CMD_TABLE(NAME, MOVE) VALUES(strings[1], strings[2]);

        HttpUrl.SELECT_URL query
        SELECT MOVE FROM CMD_TABLE WHERE NAME = strings[1];
*/
        if (url.equals(HttpUrl.INSERT_URL.getValue())) {
            try {
                String postData = "name=" + strings[1] + "&" + "move=" + strings[2];
                this.httpConnect(url, postData);

                return "POST_SUCCESS";
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (url.equals(HttpUrl.SELECT_URL.getValue())) {
            String postData = "name=" + strings[1];
            try {
                return this.httpConnect(url, postData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String fromDoInBackgroundString) {
        super.onPostExecute(fromDoInBackgroundString);
        if (fromDoInBackgroundString == null) {
            Log.e("FAILED", "HTTP Connect ERROR");
        } else if (fromDoInBackgroundString.equals("POST_SUCCESS")) {
            Log.i("DATA ", "INSERT SUCCESS");
        } else {
            Log.d("DATA ", fromDoInBackgroundString);
            List<String> moveList = doParse(fromDoInBackgroundString);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private List<String> doParse(String jsonString) {
        List<String> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONObject(jsonString).getJSONArray("cmd_table");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                list.add((String) item.get("move"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    } // JSON을 가공하여 ArrayList에 넣음


    private String httpConnect(String url, String postData) throws IOException {
        URL serverURL = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) serverURL.openConnection();

        conn.setReadTimeout(5000);
        conn.setConnectTimeout(5000);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept-Charset", "UTF-8"); // Accept-Charset 설정.
        conn.setRequestProperty("Context_Type", "application/x-www-form-urlencode");

        OutputStreamWriter outputStream = new OutputStreamWriter(conn.getOutputStream());
        outputStream.write(postData);
        outputStream.flush();
        outputStream.close();

        InputStream ips = conn.getInputStream();

        InputStreamReader isr = new InputStreamReader(ips, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(isr);

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }

        bufferedReader.close();
        conn.disconnect();

        return sb.toString().trim();
    }
}