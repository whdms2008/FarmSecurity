package com.example.retrofitex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    DataService dataService = new DataService();
    List<Member> members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView member_list = findViewById(R.id.member_list);
        final EditText et_name = findViewById(R.id.et_name);
        final EditText et_age = findViewById(R.id.et_age);
        final EditText et_address = findViewById(R.id.et_address);
        member_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        dataService.select.selectAll().enqueue(new Callback<List<Member>>() {
            @Override
            public void onResponse(Call<List<Member>> call, Response<List<Member>> response) {
                members = response.body();
                setAdapter(member_list);
            }
            @Override
            public void onFailure(Call<List<Member>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        Button btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap();
                map.put("name", et_name.getText().toString());
                map.put("age", et_age.getText().toString());
                map.put("address", et_address.getText().toString());
                dataService.insert.insertOne(map).enqueue(new Callback<Member>() {
                    @Override
                    public void onResponse(Call<Member> call, Response<Member> response) {
                        members.add(response.body());
                        setAdapter(member_list);
                        Toast.makeText(MainActivity.this, "유저 등록 완료", Toast.LENGTH_SHORT).show();
                        et_name.setText("");
                        et_age.setText("");
                        et_address.setText("");
                    }

                    @Override
                    public void onFailure(Call<Member> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    void setAdapter(RecyclerView member_list){
        member_list.setAdapter(new MemberAdapter(members, this, dataService));
    }
}