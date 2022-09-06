package com.example.farmsecurity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setTitle("메인화면");

       
        Button log_inform = (Button) findViewById(R.id.log_inform);
        Button log_check = (Button) findViewById(R.id.log_check);

        // 로그 알림
        log_inform.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Loginform.class);
                startActivity(intent);
            }
        });

        // 로그 확인
        log_check.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Logcheck.class);
                startActivity(intent);
            }
        });

    }
}
