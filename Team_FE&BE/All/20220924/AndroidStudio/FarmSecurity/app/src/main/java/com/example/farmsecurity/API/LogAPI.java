package com.example.farmsecurity.API;

import com.example.farmsecurity.Response.LogResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LogAPI {
    //로그인되어있는 계정에 해당하는 로그 검색
    @GET("select/{memberId}")
    Call<LogResponse> selectLog(@Path("memberId") String id);

    //로그인되어있는 계정에 해당하는 로그 리스트 검색
    @GET("select2/{memberId}")
    Call<List<LogResponse>> selectLogList(@Path("memberId") String id);
}
