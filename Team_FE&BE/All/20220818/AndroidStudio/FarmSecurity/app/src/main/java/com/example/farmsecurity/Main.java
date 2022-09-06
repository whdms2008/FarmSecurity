package com.example.farmsecurity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;

public class Main extends AppCompatActivity {

    private LogCheckFragment logcheckFragment = new LogCheckFragment();
    private CameraFragment cameraFragment = new CameraFragment();
    private UserPageFragment userpageFragment = new UserPageFragment();
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation);

        SharedPreferences pref = getSharedPreferences("sharedPreference", MODE_PRIVATE);
        String LogId=pref.getString("inputId","");
        bundle.putString("id",LogId);
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame_layout, logcheckFragment).commit();

        NavigationBarView navigationBarView = findViewById(R.id.menu_bottom_navigation);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_log_check:
                        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame_layout, logcheckFragment).commit();
                        return true;
                    case R.id.menu_camera:
                        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame_layout, cameraFragment).commit();
                        cameraFragment.setArguments(bundle);
                        return true;
                    case R.id.menu_user_page:
                        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame_layout, userpageFragment).commit();
                        userpageFragment.setArguments(bundle);
                        return true;
                }
                return false;
            }
        });
    }
}
