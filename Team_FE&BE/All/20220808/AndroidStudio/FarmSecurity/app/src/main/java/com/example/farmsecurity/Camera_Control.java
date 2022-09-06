package com.example.farmsecurity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Camera_Control extends AppCompatActivity {
    private RetrofitClient retrofitclient;
    private CameraAPI cameraAPI;
    TextView tv_camera_num, tv_camera_name;
    EditText ed_camera_num, ed_camera_name;
    Button btn_register, btn_update;
    Boolean btn = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_control);

        retrofitclient = RetrofitClient.getInstance();
        cameraAPI = RetrofitClient.getRetrofitInterface2();

        tv_camera_num=(TextView) findViewById(R.id.camera_num);
        tv_camera_name=(TextView) findViewById(R.id.camera_name);
        ed_camera_num=(EditText) findViewById(R.id.update_camera_num);
        ed_camera_name=(EditText) findViewById(R.id.update_camera_name);

        btn_register=(Button) findViewById(R.id.btn_register);
        btn_update=(Button) findViewById(R.id.btn_update);

        Intent intent = getIntent();
        String LogId=intent.getStringExtra("LoginId");

        System.out.println(LogId);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(btn==false) {
                    tv_camera_num.setVisibility(View.GONE);
                    tv_camera_name.setVisibility(View.GONE);
                    ed_camera_num.setVisibility(View.VISIBLE);
                    ed_camera_name.setVisibility(View.VISIBLE);
                    btn=true;
                }
                else {
                    String insert_num = ed_camera_num.getText().toString().trim();
                    String insert_name = ed_camera_name.getText().toString().trim();
                    CameraRequest cameraRequest = new CameraRequest(insert_num, insert_name, LogId);

                    cameraAPI.insertCamera(cameraRequest).enqueue(new Callback<CameraResponse>() {
                        @Override
                        public void onResponse(Call<CameraResponse> call, Response<CameraResponse> response) {
                            if(response.isSuccessful() && response.body()!=null) {
                                System.out.println("blabla");
                                setOrigin();
                            }
                        }

                        @Override
                        public void onFailure(Call<CameraResponse> call, Throwable t) { t.printStackTrace(); }
                    });
                }
            }
        });
    }
    public void setOrigin() {
        tv_camera_num.setVisibility(View.VISIBLE);
        tv_camera_name.setVisibility(View.VISIBLE);
        ed_camera_num.setVisibility(View.GONE);
        ed_camera_name.setVisibility(View.GONE);
        btn=false;
    }
}
