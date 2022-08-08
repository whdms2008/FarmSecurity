package com.example.farmsecurity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.security.spec.AlgorithmParameterSpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Change_Pw extends AppCompatActivity {

    private RetrofitClient retrofitClient;
    private InitMyAPI initMyAPI;

    EditText new_pw, new_pw2;
    Button btn_change_pw;
    Boolean Check=false;
    Boolean Check_Blank=false;
    Boolean rule_Pw=false;
    
    String symbol = "([0-9].*[!,@,#,^,&,*,(,)])|([!,@,#,^,&,*,(,)].*[0-9])"; // 숫자, 특수문자
    String alpha = "([a-z].*[A-Z])|([A-Z].*[a-z])";                          // 영문자
    
    // AES256 암호화에 필요한 것
    public static String secretKey = "01234567890123450123456789012345";
    public static byte[] ivBytes = "0123456789012345".getBytes();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pw);

        retrofitClient = RetrofitClient.getInstance();
        initMyAPI = RetrofitClient.getRetrofitInterface();
        AlertDialog.Builder Dialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        new_pw=(EditText) findViewById(R.id.new_pw);
        new_pw2=(EditText) findViewById(R.id.new_pw2);
        btn_change_pw=(Button) findViewById(R.id.btn_change_pw);

        Intent intent = getIntent();
        String Id=intent.getStringExtra("Id");

        // 비밀번호 변경 버튼
        btn_change_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_Pw = new_pw.getText().toString().trim();
                new_Pw = aesEncode(new_Pw);

                CheckBlankPw();

                if(Check_Blank==true){
                    RulePw();
                    if(rule_Pw==true){
                        CheckPw();
                    }
                }

                ChangePwRequest changePwRequest = new ChangePwRequest(new_Pw);

                if(Check==true){
                    initMyAPI.updatePw(Id, changePwRequest).enqueue(new Callback<MainResponse>() {
                        @Override
                        public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                            Dialog.setMessage("비밀번호 변경이 완료되었습니다.").setTitle("성공")
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
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
    }

    // 비밀번호 빈칸 확인 함수
    public void CheckBlankPw() {
        String new_Pw = new_pw.getText().toString().trim();
        String new_Pw2 = new_pw2.getText().toString().trim();

        if(new_Pw.isEmpty()||new_Pw2.isEmpty()){
            Toast chToast = Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.",Toast.LENGTH_LONG);
            chToast.show();
            Check_Blank=false;
        }
        else { Check_Blank=true; }
    }

    // 비밀번호 일치 확인 함수
    public void CheckPw() {
        String new_Pw=new_pw.getText().toString().trim();
        String new_Pw2=new_pw2.getText().toString().trim();

        if(new_Pw.equals(new_Pw2)) {
            Check=true;
        }
        else {
            Toast chToast = Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.",Toast.LENGTH_LONG);
            chToast.show();
        }
    }

    // 비밀번호 유효성 함수
    public void RulePw() {
        String user_Pw = new_pw.getText().toString();
        AlertDialog.Builder Dialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        if(user_Pw.length()>=8){
            if(user_Pw.contains(" ")){
                Toast.makeText(this, "비밀번호에 공백을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
            else {
                Pattern pattern_symbol = Pattern.compile(symbol);
                Pattern pattern_alpha = Pattern.compile(alpha);
                Matcher matcher_symbol = pattern_symbol.matcher(user_Pw);
                Matcher matcher_alpha = pattern_alpha.matcher(user_Pw);

                if(matcher_symbol.find()&&matcher_alpha.find()) {
                    rule_Pw=true;
                }
                else {
                    Toast.makeText(this, "부적절한 비밀번호입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else {
            Toast.makeText(this, "비밀번호는 8자 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
        }
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
}
