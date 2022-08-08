package com.example.farmsecurity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Find_Idpw extends AppCompatActivity {
    private RetrofitClient retrofitClient;
    private InitMyAPI initMyAPI;

    EditText id, name, phone, phone2;
    Button btn_find_id, btn_find_pw;
    Boolean check_id=false;
    Boolean check_pw=false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_idpw);

        AlertDialog.Builder Dialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        retrofitClient = RetrofitClient.getInstance();
        initMyAPI = RetrofitClient.getRetrofitInterface();

        id = (EditText) findViewById(R.id.id);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        phone2 = (EditText) findViewById(R.id.phone2);
        btn_find_id = (Button) findViewById(R.id.btn_find_id);
        btn_find_pw = (Button) findViewById(R.id.btn_find_pw);

        // 아이디 찾기 버튼
        btn_find_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name = name.getText().toString().trim();
                String user_phone = phone.getText().toString().trim();

                CheckBlankId();

                if(check_id==true){
                    initMyAPI.selectId(user_name, user_phone).enqueue(new Callback<MainResponse>() {
                        @Override
                        public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                            if(response.isSuccessful() && response.body()!=null) {
                                MainResponse result = response.body();
                                String find_id = result.GetId();

                                Dialog.setMessage("아이디 : "+find_id).setTitle("아이디 확인")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                finish();
                                            }
                                        }).show();
                            }
                            else {
                                Toast chToast = Toast.makeText(getApplicationContext(), "일치하는 아이디가 존재하지 않습니다.",Toast.LENGTH_LONG);
                                chToast.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MainResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            }
        });

        // 비밀번호 찾기 버튼
        btn_find_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id = id.getText().toString().trim();
                String user_phone = phone2.getText().toString().trim();

                CheckBlankPw();

                if(check_pw==true){
                    initMyAPI.selectPw(user_id, user_phone).enqueue(new Callback<MainResponse>() {
                        @Override
                        public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                            if(response.isSuccessful() && response.body()!=null) {
                                MainResponse result = response.body();
                                Intent intent = new Intent(Find_Idpw.this, Change_Pw.class);
                                intent.putExtra("Id", user_id);
                                startActivity(intent);
                            }
                            else {
                                Toast chToast = Toast.makeText(getApplicationContext(), "일치하는 아이디가 존재하지 않습니다.",Toast.LENGTH_LONG);
                                chToast.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MainResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            }
        });
    }

    // 아이디 빈칸 확인 함수
    public void CheckBlankId() {
        String user_name = name.getText().toString().trim();
        String user_phone = phone.getText().toString().trim();

        if(user_name.isEmpty()||user_phone.isEmpty()){
            Toast chToast = Toast.makeText(getApplicationContext(), "이름과 전화번호를 입력해주세요.",Toast.LENGTH_LONG);
            chToast.show();
            check_id=false;
        }
        else { check_id=true; }
    }

    // 비밀번호 빈칸 확인 함수
    public void CheckBlankPw() {
        String user_id = id.getText().toString().trim();
        String user_phone2 = phone2.getText().toString().trim();

        if(user_id.isEmpty()||user_phone2.isEmpty()){
            Toast chToast = Toast.makeText(getApplicationContext(), "아이디와 전화번호를 입력해주세요.",Toast.LENGTH_LONG);
            chToast.show();
            check_pw=false;
        }
        else { check_pw=true; }
    }
}