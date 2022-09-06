package com.example.farmsecurity.API;

import com.example.farmsecurity.Request.ChangeAdRequest;
import com.example.farmsecurity.Request.ChangePwRequest;
import com.example.farmsecurity.Request.SignUpRequest;
import com.example.farmsecurity.Response.MainResponse;
import com.example.farmsecurity.Response.SignUpResponse;

import java.lang.reflect.Member;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InitMyAPI {
    //아이디로 검색
    @GET("select/{id}")
    Call<MainResponse> selectOne(@Path("id") String id);

    //이름과 전화번호로 검색
    @GET("select/{name}/{phone}")
    Call<MainResponse> selectId(@Path("name") String name, @Path("phone") String phone);

    //아이디와 전화번호로 검색
    @GET("select2/{id}/{phone}")
    Call<MainResponse> selectPw(@Path("id") String id, @Path("phone") String phone);

    //회원가입 정보 입력
    @POST("insert")
    Call<SignUpResponse> insertOne(@Body SignUpRequest signUpRequest);

    //입력한 아이디에 해당하는 계정 비밀번호 변경
    @POST("update/{id}")
    Call<MainResponse> updatePw(@Path("id") String id, @Body ChangePwRequest changePwRequest);

    //로그인되어있는 아이디에 해당하는 계정 주소 변경
    @POST("update2/{id}")
    Call<MainResponse> updateAddress(@Path("id") String id, @Body ChangeAdRequest changeAdRequest);

    //회원 테이블에서 해당 계정 정보 삭제
    @DELETE("delete/{id}")
    Call<Void> deleteOne(@Path("id") String id);
}