package com.example.farmsecurity;

import com.example.farmsecurity.Response.LogResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LogAPI {
    @GET("select/{memberId}")
    Call<LogResponse> selectLog(@Path("memberId") String id);
}
