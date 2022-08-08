package com.example.farmsecurity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Page extends AppCompatActivity {
    private RetrofitClient retrofitClient;
    private InitMyAPI initMyAPI;

    TextView id, name, address;
    EditText update_address;
    Button btn_update, btn_cancel, btn_logout, btn_delete, btn_locate;
    Boolean btn = false;

    static String mem_Id, mem_Name, mem_Address, token;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_page);

        retrofitClient = RetrofitClient.getInstance();
        initMyAPI = RetrofitClient.getRetrofitInterface();
        AlertDialog.Builder Dialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        id = (TextView) findViewById(R.id.id);
        name = (TextView) findViewById(R.id.textview_name);
        address = (TextView) findViewById(R.id.address);
        update_address = (EditText) findViewById(R.id.update_address);

        Intent intent = getIntent();
        String LogId=intent.getStringExtra("LoginId");

        btn_update=(Button) findViewById(R.id.btn_update);
        btn_cancel=(Button) findViewById(R.id.btn_cancel);
        btn_logout=(Button) findViewById(R.id.btn_logout);
        btn_delete=(Button) findViewById(R.id.btn_delete);
        btn_locate=(Button) findViewById(R.id.btn_locate);

        getToken();

        // 회원 정보 가져오기
        initMyAPI.selectOne(LogId).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if(response.isSuccessful() && response.body()!=null) {
                    MainResponse result = response.body();
                    mem_Id = result.GetId();
                    mem_Name = result.GetName();
                    mem_Address = result.GetAddress();

                    id.setText(mem_Id);
                    name.setText(mem_Name);
                    address.setText(mem_Address);
                }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        // 정보 변경 버튼
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn==false) {
                    btn_cancel.setVisibility(View.VISIBLE);
                    address.setVisibility(View.GONE);
                    update_address.setVisibility(View.VISIBLE);
                    btn=true;
                }
                else {
                    String user_id = LogId;
                    String user_address = update_address.getText().toString().trim();
                    ChangeAdRequest changeAdRequest = new ChangeAdRequest(user_address);

                    initMyAPI.updateAddress(user_id, changeAdRequest).enqueue(new Callback<MainResponse>() {
                        @Override
                        public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                            btn = false;
                            Dialog.setMessage("주소 변경이 완료되었습니다.").setTitle("주소 변경")
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            setOrigin();
                                            address.setText(user_address);
                                        }
                                    }).show();
                        }

                        @Override
                        public void onFailure(Call<MainResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            }
        });

        // 취소 버튼
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOrigin();
            }
        });

        // 회월 탈퇴 버튼
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id=LogId;

                Dialog.setMessage("회원탈퇴 시 데이터를 복구할 수 없습니다.").setTitle("정말로 회원탈퇴 하시겠습니까?")
                        .setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) { }
                        })
                        .setNeutralButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                initMyAPI.deleteOne(user_id).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);

                                        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreference", Activity.MODE_PRIVATE);

                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.clear();
                                        editor.commit();

                                        Toast chToast = Toast.makeText(getApplicationContext(), "회원탈퇴가 완료되었습니다.",Toast.LENGTH_LONG);
                                        chToast.show();
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                            }
                        }).show();
            }
        });

        // 로그아웃 버튼
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                SharedPreferences sharedPreferences = getSharedPreferences("sharedPreference", Activity.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        btn_locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id=LogId;

                Intent intent = new Intent(User_Page.this, Camera_Control.class);
                intent.putExtra("LoginId", LogId);
                startActivity(intent);
            }
        });
    }

    // 원상태로 되돌리기
    public void setOrigin() {
        btn_cancel.setVisibility(View.GONE);
        address.setVisibility(View.VISIBLE);
        address.setText(mem_Address);
        update_address.setVisibility(View.GONE);
        btn=false;
    }

    public void getToken(){
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(!task.isSuccessful()) {
                    return;
                }
                else {
                    token = task.getResult().getToken();
                    Log.d("FCM Log", "Refreshed token: "+token);
                }
            }
        });
    }
}
