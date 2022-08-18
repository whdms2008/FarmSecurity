package com.example.farmsecurity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.farmsecurity.Response.LogResponse;
import com.example.farmsecurity.Response.MainResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmActivity extends AppCompatActivity {
    private RetrofitClient retrofitclient;
    private LogAPI logAPI;
    TextView tv_name, tv_level, tv_time;
    Button btnHome;
    ImageView iv_image;
    String login_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_inform);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreference", Activity.MODE_PRIVATE);

        retrofitclient = RetrofitClient.getInstance();
        logAPI = RetrofitClient.getRetrofitInterface3();
        login_id = sharedPreferences.getString("inputId", null);

        tv_name=(TextView) findViewById(R.id.camera_name);
        tv_time=(TextView) findViewById(R.id.current_time);
        tv_level=(TextView) findViewById(R.id.current_level);

        btnHome=(Button) findViewById(R.id.home);

        iv_image=(ImageView) findViewById(R.id.image_View);

        logAPI.selectLog(login_id).enqueue(new Callback<LogResponse>() {
            @Override
            public void onResponse(Call<LogResponse> call, Response<LogResponse> response) {
                if(response.isSuccessful() && response.body()!=null) {
                    LogResponse result = response.body();
                    tv_name.setText(result.getCameraName);
                    tv_time.setText(result.getTime);
                    tv_level.setText(result.getLevel);
                    Glide.with(AlarmActivity.this).load(result.getLink).override(200,200).into(iv_image);
                }
            }

            @Override
            public void onFailure(Call<LogResponse> call, Throwable t) { t.printStackTrace(); }

        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlarmActivity.this, Main.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
