package com.example.farmsecurity;

import java.lang.reflect.Member;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InitMyAPI {
    @GET("select/{id}")
    Call<MainResponse> selectOne(@Path("id") String id);

    @GET("select")
    Call<List<Member>> selectAll();

    @GET("select/{name}/{phone}")
    Call<MainResponse> selectId(@Path("name") String name, @Path("phone") String phone);

    @GET("select2/{id}/{phone}")
    Call<MainResponse> selectPw(@Path("id") String id, @Path("phone") String phone);

    @POST("insert")
    Call<SignUpResponse> insertOne(@Body SignUpRequest signUpRequest);

    @POST("update/{id}")
    Call<MainResponse> updatePw(@Path("id") String id, @Body ChangePwRequest changePwRequest);

    @POST("update2/{id}")
    Call<MainResponse> updateAddress(@Path("id") String id, @Body ChangeAdRequest changeAdRequest);

    @DELETE("delete/{id}")
    Call<Void> deleteOne(@Path("id") String id);
}