package com.example.farmsecurity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.farmsecurity.Request.SignUpRequest;
import com.example.farmsecurity.Response.MainResponse;
import com.example.farmsecurity.Response.SignUpResponse;

import java.security.spec.AlgorithmParameterSpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sign_Up extends AppCompatActivity {

    private RetrofitClient retrofitClient;
    private InitMyAPI initMyAPI;
    EditText id, pw, pw_re, name, address, phone;
    Button btn_sign_up, btn_duplicate;
    Boolean Check=false;
    Boolean Check_Id=false;
    Boolean Check_Pw=false;
    Boolean Check_Blank=false;
    Boolean Rule_Id=false;
    Boolean Rule_Pw=false;
    Boolean Rule_Name=false;
    Boolean Rule_Address=false;
    Boolean Rule_Phone=false;

    String symbol = "([0-9].*[!,@,#,^,&,*,(,)])|([!,@,#,^,&,*,(,)].*[0-9])"; // 특수문자, 숫자
    String alpha = "([a-z].*[A-Z])|([A-Z].*[a-z])"; // 영문자

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        retrofitClient = RetrofitClient.getInstance();
        initMyAPI = RetrofitClient.getRetrofitInterface();
        AlertDialog.Builder Dialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        id=(EditText)findViewById(R.id.id);
        pw=(EditText)findViewById(R.id.pw);
        pw_re=(EditText)findViewById(R.id.pw_re);
        name=(EditText)findViewById(R.id.name);
        address=(EditText)findViewById(R.id.address);
        phone=(EditText)findViewById(R.id.phone);
        btn_sign_up = (Button) findViewById(R.id.btn_sign_up);
        btn_duplicate = (Button) findViewById(R.id.btn_duplication);

        // 중복검사 버튼
        btn_duplicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RuleId();
                if(Rule_Id==true){
                    CheckId();
                }
            }
        });

        // 회원가입 버튼
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBlank();
                if(Check_Blank==true){
                    RulePw();
                    RuleName();
                    RuleAddress();
                    RulePhone();
                    if(Rule_Id==true && Rule_Pw==true && Rule_Name==true && Rule_Address==true && Rule_Phone==true){
                        CheckPassword();
                    }
                }

                if(Check_Id==true && Check_Pw==true &&Check_Blank==true){
                    Check = true;
                }

                if(Check==true) {
                    SignUpProcess();
                }
                else {
                    if(Check_Id==false){
                        Dialog.setMessage("아이디 중복을 확인해 주세요.").setTitle("실패")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) { }
                                }).show();
                    }
                }
            }
        });
    }

    // DB 내 아이디 중복 확인 함수
    public void CheckId() {
        String user_id = id.getText().toString().trim();
        AlertDialog.Builder Dialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        initMyAPI.selectOne(user_id).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if(response.isSuccessful() && response.body()!=null) {
                    Dialog.setMessage("중복된 ID입니다.").setTitle("실패")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) { }
                            }).show();
                }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                Dialog.setMessage("사용 가능한 ID입니다.").setTitle("성공")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Check_Id = true;
                            }
                        }).show();
                t.printStackTrace();
            }
        });
    }

    // 비밀번호 일치 확인 함수
    public void CheckPassword() {
        String user_pw = pw.getText().toString();
        String user_pw_re = pw_re.getText().toString();
        AlertDialog.Builder Dialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        if(user_pw.equals(user_pw_re)) {
            Check_Pw = true;
        }
        else {
            Dialog.setMessage("비밀번호가 일치하지 않습니다.").setTitle("실패")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) { }
                    }).show();
        }
    }

    // 빈칸 확인 함수
    public void CheckBlank() {
        String user_id = id.getText().toString().trim();
        String user_pw = pw.getText().toString().trim();
        String user_pw_re = pw_re.getText().toString().trim();
        String user_name = name.getText().toString().trim();
        String user_address = address.getText().toString().trim();
        String user_phone = phone.getText().toString().trim();

        if(user_id.isEmpty()){
            Toast chToast = Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.",Toast.LENGTH_LONG);
            chToast.show();
        }
        else {
            if(user_pw.isEmpty()){
                Toast chToast = Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.",Toast.LENGTH_LONG);
                chToast.show();
            }
            else {
                if(user_pw_re.isEmpty()){
                    Toast chToast = Toast.makeText(getApplicationContext(), "비밀번호를 다시 입력해주세요.",Toast.LENGTH_LONG);
                    chToast.show();
                }
                else {
                    if(user_name.isEmpty()){
                        Toast chToast = Toast.makeText(getApplicationContext(), "이름을 확인해주세요.",Toast.LENGTH_LONG);
                        chToast.show();
                    }
                    else {
                        if(user_address.isEmpty()){
                            Toast chToast = Toast.makeText(getApplicationContext(), "주소를 입력해주세요.",Toast.LENGTH_LONG);
                            chToast.show();
                        }
                        else {
                            if(user_phone.isEmpty()){
                                Toast chToast = Toast.makeText(getApplicationContext(), "전화번호를 입력해주세요.",Toast.LENGTH_LONG);
                                chToast.show();
                            }
                            else { Check_Blank = true; }
                        }
                    }
                }
            }
        }
    }

    // AES 암호화에 필요한 것들
    public static String secretKey = "01234567890123450123456789012345";
    public static byte[] ivBytes = "0123456789012345".getBytes();

    // AES256 암호화
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

    // 회원가입 함수
    public void SignUpProcess() {
        String user_id = id.getText().toString().trim();
        String user_pw = pw.getText().toString().trim();
        user_pw = aesEncode(user_pw);
        String user_name = name.getText().toString().trim();
        String user_address = address.getText().toString().trim();
        String user_phone = phone.getText().toString().trim();
        AlertDialog.Builder Dialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        SignUpRequest signUpRequest = new SignUpRequest(user_id, user_pw, user_name, user_address, user_phone);

        initMyAPI.insertOne(signUpRequest).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if(response.isSuccessful() && response.body()!=null) {
                    SignUpResponse result = response.body();
                    Dialog.setMessage("회원가입이 완료되었습니다.").setTitle("성공")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            }).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    // 아이디 유효성 검사 함수
    public void RuleId() {
        String user_id = id.getText().toString();
        AlertDialog.Builder Dialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        // 아이디 길이 5~12자
        if(user_id.length()>=5&&user_id.length()<=12){
            Rule_Id=true;
        }
        else {
            Dialog.setMessage("아이디는 5~12자까지 사용할 수 있습니다.").setTitle("실패")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) { }
                    }).show();
        }
    }

    // 비밀번호 유효성 검사 함수
    public void RulePw() {
        String user_pw = pw.getText().toString();
        AlertDialog.Builder Dialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        // 최소 비밀번호 길이 8자 이상
        if(user_pw.length()>=8){
            if(user_pw.contains(" ")){
                Dialog.setMessage("비밀번호에 공백을 사용할 수 없습니다.").setTitle("실패")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) { }
                        }).show();
            }
            else {
                // 소문자, 대문자, 숫자, 특수문자 조합
                Pattern pattern_symbol = Pattern.compile(symbol);
                Pattern pattern_alpha = Pattern.compile(alpha);
                Matcher matcher_symbol = pattern_symbol.matcher(user_pw);
                Matcher matcher_alpha = pattern_alpha.matcher(user_pw);

                if(matcher_symbol.find()&&matcher_alpha.find()) {
                    Rule_Pw=true;
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

    // 이름 유효성 검사
    public void RuleName() {
        String user_name = name.getText().toString();
        AlertDialog.Builder Dialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        if(user_name.contains(" ")){
            Dialog.setMessage("이름에 공백을 사용할 수 없습니다.").setTitle("실패")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) { }
                    }).show();
        }
        else {
            int i = 0;
            for(i=0;i<user_name.length();i++){
                if(!String.valueOf(user_name.charAt(i)).matches("^[a-zA-Zㄱ-ㅎ가-힣]*$")){
                    Dialog.setMessage("이름에 특수문자를 사용할 수 없습니다.").setTitle("실패")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) { }
                            }).show();
                    Rule_Name=false;
                    break;
                }
            }

            if(i==user_name.length()){
                Rule_Name=true;
            }
        }
    }

    // 주소 유효성 검사
    public void RuleAddress() {
        String user_address = address.getText().toString();
        AlertDialog.Builder Dialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        int i = 0;
        for(i=0;i<user_address.length();i++){
            if(!String.valueOf(user_address.charAt(i)).matches("^[a-zA-Zㄱ-ㅎ가-힣]*$")){
                Dialog.setMessage("주소에 특수문자를 사용할 수 없습니다.").setTitle("실패")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) { }
                        }).show();
                Rule_Address=false;
                break;
            }
        }

        if(i==user_address.length()){
            Rule_Address=true;
        }

    }

    // 휴대폰 유효성 검사 함수
    public void RulePhone() {
        String user_phone = phone.getText().toString();
        AlertDialog.Builder Dialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        // 휴대폰 길이 11자
        if(user_phone.length()==11){
            Rule_Phone=true;
        }
        else {
            Dialog.setMessage("휴대폰 길이는 11자리입니다.").setTitle("실패")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) { }
                    }).show();
        }
    }
}
