package com.example.farmsecurity;

import com.example.farmsecurity.Request.CameraRequest;
import com.example.farmsecurity.Response.CameraResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CameraAPI {
    @POST("insert")
    Call<CameraResponse> insertCamera(@Body CameraRequest cameraRequest);
}
