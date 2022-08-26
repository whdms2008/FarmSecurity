package com.example.farmsecurity.API;

import com.example.farmsecurity.Request.CameraRequest;
import com.example.farmsecurity.Response.CameraLogResponse;
import com.example.farmsecurity.Response.CameraResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CameraAPI {
    //로그인되어있는 계정으로 카메라 정보 등록
    @POST("insert")
    Call<CameraResponse> insertCamera(@Body CameraRequest cameraRequest);

    @GET("select2/{memId}")
    Call<CameraLogResponse> selectCamera(@Path("memId") String id);

    @DELETE("delete/{memId}")
    Call<Void> deleteCamera(@Path("memId") String id);
}
