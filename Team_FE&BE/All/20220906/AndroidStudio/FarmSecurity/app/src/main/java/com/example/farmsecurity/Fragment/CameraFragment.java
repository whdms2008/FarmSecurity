package com.example.farmsecurity.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.farmsecurity.API.CameraAPI;
import com.example.farmsecurity.Activity.Main;
import com.example.farmsecurity.R;
import com.example.farmsecurity.Request.CameraRequest;
import com.example.farmsecurity.Response.CameraLogResponse;
import com.example.farmsecurity.Response.CameraResponse;
import com.example.farmsecurity.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CameraFragment extends Fragment {

    private RetrofitClient retrofitclient;
    private CameraAPI cameraAPI;
    TextView tv_camera_num, tv_camera_name;
    EditText ed_camera_num, ed_camera_name;
    Button btn_register, btn_delete;
    Boolean btn = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.camera, null);

        retrofitclient = RetrofitClient.getInstance();
        cameraAPI = RetrofitClient.getRetrofitInterface2();
        AlertDialog.Builder Dialog = new AlertDialog.Builder(getActivity());

        tv_camera_num=(TextView)view.findViewById(R.id.camera_num);
        tv_camera_name=(TextView)view.findViewById(R.id.camera_name);
        ed_camera_num=(EditText)view.findViewById(R.id.update_camera_num);
        ed_camera_name=(EditText)view.findViewById(R.id.update_camera_name);

        btn_register=(Button)view.findViewById(R.id.btn_register);
        btn_delete=(Button)view.findViewById(R.id.btn_delete);

        String LogId = this.getArguments().getString("id");

        cameraAPI.selectCamera(LogId).enqueue(new Callback<CameraLogResponse>() {
            @Override
            public void onResponse(Call<CameraLogResponse> call, Response<CameraLogResponse> response) {
                if(response.isSuccessful() && response.body()!=null) {
                    CameraLogResponse result = response.body();

                    tv_camera_num.setText(result.getCameraNum());
                    tv_camera_name.setText(result.getCameraName());

                    btn_register.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<CameraLogResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        //로그인되어있는 계정의 카메라를 등록
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(btn==false) {
                    tv_camera_num.setVisibility(View.GONE);
                    tv_camera_name.setVisibility(View.GONE);
                    ed_camera_num.setVisibility(View.VISIBLE);
                    ed_camera_name.setVisibility(View.VISIBLE);
                    btn=true;
                }
                else {
                    String insert_num = ed_camera_num.getText().toString().trim();
                    String insert_name = ed_camera_name.getText().toString().trim();
                    CameraRequest cameraRequest = new CameraRequest(insert_num, insert_name, LogId);

                    cameraAPI.insertCamera(cameraRequest).enqueue(new Callback<CameraResponse>() {
                        @Override
                        public void onResponse(Call<CameraResponse> call, Response<CameraResponse> response) {
                            if(response.isSuccessful() && response.body()!=null) {
                                Dialog.setMessage("카메라 등록이 완료되었습니다.").setTitle("카메라 등록")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                setOrigin();
                                            }
                                        }).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<CameraResponse> call, Throwable t) { t.printStackTrace(); }
                    });
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id=LogId;

                Dialog.setMessage("카메라 삭제 시 데이터를 복구할 수 없습니다.").setTitle("정말로 카메라를 삭제하시겠습니까?")
                        .setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) { }
                        })
                        .setNeutralButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cameraAPI.deleteCamera(user_id).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Intent intent = new Intent(getActivity(), Main.class);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                            }
                        }).show();
            }
        });

        return view;
    }

    //원 상태로 복구
    public void setOrigin() {
        tv_camera_num.setVisibility(View.VISIBLE);
        tv_camera_name.setVisibility(View.VISIBLE);
        ed_camera_num.setText("");
        ed_camera_name.setText("");
        ed_camera_num.setVisibility(View.GONE);
        ed_camera_name.setVisibility(View.GONE);
        btn=false;
    }
}