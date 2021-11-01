package com.example.roboticVacuum.service;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/*
개발 끝날때 삭제 합시다....
계속 봐야되요...
https://1d1cblog.tistory.com/133
 */
public class HttpService extends AsyncTask<String, Void, String> {
    public static String RECORD_NAME = "test";
    public static boolean IS_RECORDING = false;
    public final static String POST_URL = "http://192.168.0.2/api-post.php";
    public final static String GET_URL = "httpL//192.168.0.2/api_select.php";

    @Override
    protected String doInBackground(String... strings) { // execute 의 매개변수를 받아와서 사용
        String url = strings[0];
        if (url.equals(POST_URL)) {
            try {
                String name = strings[1];
                String move = strings[2];
                String postData = "name=" + name + "&" + "move=" + move;

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
                Log.i("INPUT DATA : ", sb.toString().trim());
                Log.i("OUTPUT DATA : ", postData);

                conn.disconnect();
                return sb.toString().trim();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(url.equals(GET_URL)){
            /*TODO
            SELECT MOVE FROM CMD_TABLE
            WHERE CMD_TABLE.NAME = NAME
             */
        }
        return null;

    }

    @Override
    protected void onPostExecute(String fromDoInBackgroundString) {  // doInBackgroundString 에서 return 한 값을 받음
        super.onPostExecute(fromDoInBackgroundString);
        if (fromDoInBackgroundString == null) {
            Log.d("FAILED", "DB Connect ERROR");
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
}