package com.example.farmsecurity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farmsecurity.API.InitMyAPI;
import com.example.farmsecurity.R;
import com.example.farmsecurity.Response.MainResponse;
import com.example.farmsecurity.RetrofitClient;
import com.example.farmsecurity.ServiceThread;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RetrofitClient retrofitclient;
    private InitMyAPI initMyAPI;
    EditText id, pw;
    Button btn_login, btn_sign_up;
    TextView find_idpw;
    String login_id, login_pw;

    // AES256 암호화에 필요한 것들
    public static String secretKey = "01234567890123450123456789012345";
    public static byte[] ivBytes = "0123456789012345".getBytes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 자동 로그인 기능
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreference", Activity.MODE_PRIVATE);

        id = (EditText) findViewById(R.id.id);
        pw = (EditText) findViewById(R.id.pw);
        btn_login = (Button) findViewById(R.id.login);
        btn_sign_up = (Button) findViewById(R.id.sign_up);
        find_idpw = (TextView) findViewById(R.id.find_idpw);

        // 아이디, 비밀번호 sharedPreferences를 통해 저장, 다른 Activity, Fragment와 공유하기
        login_id = sharedPreferences.getString("inputId", null);
        login_pw = sharedPreferences.getString("inputPw", null);

        // 아이디 및 비밀번호가 널 값이 아닌 경우
        if(login_id != null && login_pw != null) {
            Toast.makeText(getApplicationContext(), login_id + "님 자동로그인 되었습니다.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, Main.class);
            intent.putExtra("LoginId", login_id);
            onStartForegroundService();
            startActivity(intent);
        }

        // 로그인 버튼 클릭
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id = id.getText().toString().trim();
                String user_pw = pw.getText().toString().trim();

                retrofitclient = RetrofitClient.getInstance();
                initMyAPI = RetrofitClient.getRetrofitInterface();

                initMyAPI.selectOne(user_id).enqueue(new Callback<MainResponse>() {
                    @Override
                    public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                        if(response.isSuccessful() && response.body()!=null) {
                            MainResponse result = response.body();
                            String member_pw = result.GetPassword();

                            if(aesEncode(user_pw).equals(member_pw)){
                                SharedPreferences sharedPreferences = getSharedPreferences("sharedPreference", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor autoLogin = sharedPreferences.edit();

                                autoLogin.putString("inputId", user_id);
                                autoLogin.putString("inputPw", user_pw);

                                autoLogin.commit();

                                Intent intent = new Intent(MainActivity.this, Main.class);
                                intent.putExtra("LoginId", user_id);
                                onStartForegroundService();
                                startActivity(intent);
                            }
                            // 아이디 또는 비밀번호가 다를 때 오류 알림
                            else
                            {
                                Toast chToast = Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 다릅니다.",Toast.LENGTH_LONG);
                                chToast.show();
                            }
                        }
                    }
                    // 존재하는 아이디가 없을 때 오류 알림
                    @Override
                    public void onFailure(Call<MainResponse> call, Throwable t) {
                        Toast chToast = Toast.makeText(getApplicationContext(), "존재하지 않는 아이디 입니다.",Toast.LENGTH_LONG);
                        chToast.show();
                        t.printStackTrace();
                    }
                });
            }
        });

        // 회원가입 버튼 클릭
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Sign_Up.class);
                startActivity(intent);
            }
        });

        // 아이디 비밀번호 찾기 클릭
        TextView find_idpw = (TextView) findViewById(R.id.find_idpw);
        find_idpw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Find_Idpw.class);
                startActivity(intent);
            }
        });
    }

    //AES256 암호화
    public static String aesEncode(String str) {
        try {
            byte[] textBytes = str.getBytes("UTF-8");
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);

            return Base64.encodeToString(cipher.doFinal(textBytes), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    //Foreground 서비스
    public void onStartForegroundService() {
        Intent ServiceIntent = new Intent(this, ServiceThread.class);
        ServiceIntent.setAction("startForeground");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(ServiceIntent);
        }
        else {
            startService(ServiceIntent);
        }
    }
}