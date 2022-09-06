package com.example.farmsecurity.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.farmsecurity.API.LogAPI;
import com.example.farmsecurity.R;
import com.example.farmsecurity.Response.LogResponse;
import com.example.farmsecurity.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogCheckFragment extends Fragment {
    private RetrofitClient retrofitClient;
    private LogAPI logAPI;
    List<LogResponse> members;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.log_check, null);
        final RecyclerView member_list = (RecyclerView) view.findViewById(R.id.member_list);

        retrofitClient= RetrofitClient.getInstance();
        logAPI = RetrofitClient.getRetrofitInterface3();

        String LogId = this.getArguments().getString("id");

        member_list.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        logAPI.selectLogList(LogId).enqueue(new Callback<List<LogResponse>>() {
            @Override
            public void onResponse(Call<List<LogResponse>> call, Response<List<LogResponse>> response) {
                if(response.isSuccessful() && response.body()!=null) {
                    members = response.body();
                    setAdapter(member_list);
                }
            }
            @Override
            public void onFailure(Call<List<LogResponse>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return view;
    }

    void setAdapter(RecyclerView member_list){
        member_list.setAdapter(new MemberAdapter(members, this.getContext(), logAPI, retrofitClient));
    }

}