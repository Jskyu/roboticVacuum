package com.example.roboticVacuum;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.roboticVacuum.databinding.ActivityMainBinding;
import com.example.roboticVacuum.dto.CommandDTO;
import com.example.roboticVacuum.service.BtnEventService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private final BtnEventService btnEventService = new BtnEventService();
    private final String url = "http://192.168.0.2/test.php";
    private ArrayList<CommandDTO> list = new ArrayList<>();
    private TextView textView;
    private String jsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_modeScreen, R.id.navigation_bluetooth, R.id.navigation_remocon)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        findViewById(R.id.btnRandom).setOnClickListener(this);
        findViewById(R.id.btnCircle).setOnClickListener(this);
        findViewById(R.id.btnWallFollowing).setOnClickListener(this);
        findViewById(R.id.btnWave).setOnClickListener(this);

//        findViewById(R.id.btnRemoteUp).setOnClickListener(this);
//        findViewById(R.id.btnRemoteDown).setOnClickListener(this);
//        findViewById(R.id.btnRemoteRight).setOnClickListener(this);
//        findViewById(R.id.btnRemoteLeft).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnRandom:
                //btnRandom Click Event
                Button btnRandom = findViewById(R.id.btnRandom);
                textView = findViewById(R.id.textView);
                JsonParse jsonParse = new JsonParse();      // AsyncTask 생성
                jsonParse.execute(url);     // AsyncTask 실행
                btnRandom.setText(String.valueOf(list.size()));
//                btnEventService.pressedRandomButton(findViewById(id));
                break;
            case R.id.btnCircle:
                btnEventService.pressedCircleButton();
                break;
            case R.id.btnWallFollowing:
                btnEventService.pressedWallFollowingButton();
                break;
            case R.id.btnWave:
                btnEventService.pressedWaveButton();
                break;
//            case R.id.btnRemoteUp:
//                btnEventService.pressedUpButton(isRecording);
//                break;
//            case R.id.btnRemoteDown:
//                btnEventService.pressedDownButton(isRecording);
//                break;
//            case R.id.btnRemoteRight:
//                btnEventService.pressedRightButton(isRecording);
//                break;
//            case R.id.btnRemoteLeft:
//                btnEventService.pressedLeftButton(isRecording);
//                break;
        }
    }

    //https://1d1cblog.tistory.com/133
    public class JsonParse extends AsyncTask<String, Void, String> {
        private String TAG = "JsonParseTest";

        @Override
        protected String doInBackground(String... strings) { // execute 의 매개변수를 받아와서 사용
            String url = strings[0];
            try {
                URL serverURL = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) serverURL.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseCode = httpURLConnection.getResponseCode();

                InputStream ips;
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    ips = httpURLConnection.getInputStream();
                } else {
                    ips = httpURLConnection.getErrorStream();
                }

                InputStreamReader isr = new InputStreamReader(ips, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(isr);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();
                Log.d(TAG, sb.toString().trim());

                return sb.toString().trim();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String fromDoInBackgroundString) {  // doInBackgroundString 에서 return 한 값을 받음
            super.onPostExecute(fromDoInBackgroundString);

            if (fromDoInBackgroundString == null)
                textView.setText("error");
            else {
                jsonString = fromDoInBackgroundString;
                list.clear();
                doParse();
                Log.d(TAG, list.get(0).getName());
                textView.setText(list.get(0).getName());
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

        private void doParse() {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("cmd_table");

                for (int i = 0; i < jsonArray.length(); i++) {
                    CommandDTO commandDTO = new CommandDTO();

                    JSONObject item = jsonArray.getJSONObject(i);
                    commandDTO.setIndex(item.getLong("index"));
                    commandDTO.setName(item.getString("name"));
                    commandDTO.setMove(item.getInt("move"));

                    list.add(commandDTO);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (CommandDTO dto : list) {
                Log.d(">>", dto.toString());
            }
        } // JSON을 가공하여 ArrayList에 넣음
    }
}
