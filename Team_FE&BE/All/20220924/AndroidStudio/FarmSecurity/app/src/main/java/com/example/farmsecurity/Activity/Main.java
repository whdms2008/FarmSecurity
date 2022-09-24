package com.example.farmsecurity.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.farmsecurity.Fragment.CameraFragment;
import com.example.farmsecurity.Fragment.LogCheckFragment;
import com.example.farmsecurity.Fragment.UserPageFragment;
import com.example.farmsecurity.R;
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

        //MainActivity에서 저장한 값을 가져와 사용
        SharedPreferences pref = getSharedPreferences("sharedPreference", MODE_PRIVATE);
        String LogId=pref.getString("inputId","");
        bundle.putString("id",LogId);

        //프래그먼트 default값을 logcheckFragment로 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame_layout, logcheckFragment).commit();
        logcheckFragment.setArguments(bundle);

        NavigationBarView navigationBarView = findViewById(R.id.menu_bottom_navigation);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //각 버튼 클릭 시 해당 Fragment로 이동
                switch (item.getItemId()) {
                    case R.id.menu_log_check:
                        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame_layout, logcheckFragment).commit();
                        logcheckFragment.setArguments(bundle); //sharedPreference에 저장된 값을 Fragment로 전달
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
