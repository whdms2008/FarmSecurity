package com.example.farmsecurity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LogInform extends AppCompatActivity {

    TextView time, level;
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_inform);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreference", Activity.MODE_PRIVATE);

        time=(TextView) findViewById(R.id.current_time);
        level=(TextView) findViewById(R.id.current_level);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInform.this, Main.class);
                startActivity(intent);
            }
        });
    }
}
