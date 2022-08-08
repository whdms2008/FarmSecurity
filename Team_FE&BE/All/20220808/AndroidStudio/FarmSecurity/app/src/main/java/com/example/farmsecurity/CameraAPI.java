package com.example.farmsecurity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CameraAPI {
    @POST("insert")
    Call<CameraResponse> insertCamera(@Body CameraRequest cameraRequest);
}
