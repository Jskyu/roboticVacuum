package com.example.roboticVacuum;

import android.os.Bundle;
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
import com.example.roboticVacuum.service.PHPRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private final BtnEventService btnEventService = new BtnEventService();
    private final String url = "http://192.168.0.2/test2.php";
    private ArrayList<CommandDTO> list = new ArrayList<>();

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
        PHPRequest jsonParse = new PHPRequest();      // AsyncTask 생성
        switch (id) {
            case R.id.btnRandom:
                //btnRandom Click Event
                Button btnRandom = findViewById(R.id.btnRandom);
                jsonParse.execute(url);     // AsyncTask 실행
                btnRandom.setText(String.valueOf(list.size()));
//                btnEventService.pressedRandomButton(findViewById(id));
                break;
            case R.id.btnCircle:
                btnEventService.pressedCircleButton();
                jsonParse.execute(url);

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
}