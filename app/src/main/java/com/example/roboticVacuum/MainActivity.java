package com.example.roboticVacuum;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.roboticVacuum.ui.connect.BluetoothFragment;
import com.example.roboticVacuum.ui.mode.ModeFragment;
import com.example.roboticVacuum.ui.remocon.RemoteFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

//public class MainActivity extends AppCompatActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {
public class MainActivity extends AppCompatActivity {

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final RemoteFragment remoconFragment = new RemoteFragment();
    private final BluetoothFragment bluetoothFragment = new BluetoothFragment();
    private final ModeFragment modeFragment = new ModeFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, modeFragment).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
//        bottomNavigationView

        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }
    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_remocon:
                    fragmentTransaction.replace(R.id.frameLayout, remoconFragment).commitAllowingStateLoss();
                    break;
                case R.id.navigation_modeScreen:
                    fragmentTransaction.replace(R.id.frameLayout, modeFragment).commitAllowingStateLoss();
                    break;
                case R.id.navigation_bluetooth:
                    fragmentTransaction.replace(R.id.frameLayout, bluetoothFragment).commitAllowingStateLoss();
                    break;
            }
            return true;
        }

    }
}