package com.example.farmsecurity;

import com.example.farmsecurity.API.CameraAPI;
import com.example.farmsecurity.API.InitMyAPI;
import com.example.farmsecurity.API.LogAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private static InitMyAPI initMyAPI;
    private static CameraAPI cameraAPI;
    private static LogAPI logAPI;
    //사용하고 있는 서버 BASE 주소
    private static String MemberUrl = "http://15.165.86.204:8080/member/";
    private static String CameraUrl = "http://15.165.86.204:8080/camera/";
    private static String LogUrl = "http://15.165.86.204:8080/log/";

    private RetrofitClient() {
        //로그를 보기 위한 Interceptor
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Gson gson = new GsonBuilder().setLenient().create();

        //retrofit 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MemberUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client) //로그 기능 추가
                .build();

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(CameraUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        Retrofit retrofit3 = new Retrofit.Builder()
                .baseUrl(LogUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        initMyAPI = retrofit.create(InitMyAPI.class);
        cameraAPI = retrofit2.create(CameraAPI.class);
        logAPI = retrofit3.create(LogAPI.class);
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public static InitMyAPI getRetrofitInterface() { return initMyAPI; }

    public static CameraAPI getRetrofitInterface2() { return cameraAPI; }

    public static LogAPI getRetrofitInterface3() { return logAPI; }
}
