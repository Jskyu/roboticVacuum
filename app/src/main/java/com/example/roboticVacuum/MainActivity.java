package com.example.roboticVacuum;

import static com.example.roboticVacuum.R.*;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.roboticVacuum.databinding.ActivityMainBinding;
import com.example.roboticVacuum.service.BtnEventService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private final BtnEventService btnEventService = new BtnEventService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                id.navigation_modeScreen, id.navigation_bluetooth, id.navigation_remocon)
                .build();
        NavController navController = Navigation.findNavController(this, id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        findViewById(id.btnRandom).setOnClickListener(this);
        findViewById(id.btnCircle).setOnClickListener(this);
        findViewById(id.btnWallFollowing).setOnClickListener(this);
        findViewById(id.btnWave).setOnClickListener(this);
        findViewById(id.btnRemoteUp).setOnClickListener(this);
        findViewById(id.btnRemoteDown).setOnClickListener(this);
        findViewById(id.btnRemoteRight).setOnClickListener(this);
        findViewById(id.btnRemoteLeft).setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        switch (id) {
            case R.id.btnRandom:
                //btnRandom Click Event
                btnEventService.pressedRandomButton((Button) findViewById(id));
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
            case R.id.btnRemoteUp:
                btnEventService.pressedUpButton();
                break;
            case R.id.btnRemoteDown:
                btnEventService.pressedDownButton();
                break;
            case R.id.btnRemoteRight:
                btnEventService.pressedRightButton();
                break;
            case R.id.btnRemoteLeft:
                btnEventService.pressedLeftButton();
                break;
        }
    }
}