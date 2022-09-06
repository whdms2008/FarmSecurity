package com.example.retrofitex;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private List<Member> data;
    private Context context;
    private DataService dataService;

    MemberAdapter(List<Member> data, Context context, DataService dataService) {
        this.data = data;
        this.context = context;
        this.dataService = dataService;
    }

    @Override
    public MemberAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new MemberAdapter.ViewHolder(inflater.inflate(R.layout.member_info, parent, false));
    }

    @Override
    public int getItemCount() {
        return data.size() ;
    }

    @Override
    public void onBindViewHolder(final MemberAdapter.ViewHolder holder, final int position) {
        holder.info_id.setText(String.valueOf(data.get(position).getId()));
        holder.info_name.setText(data.get(position).getName());
        holder.info_age.setText(String.valueOf(data.get(position).getAge()));
        holder.info_address.setText(data.get(position).getAddress());
        holder.info_created.setText(dateParser(data.get(position).getCreatedAt()));

        holder.info_update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                holder.update_id.setText(String.valueOf(data.get(position).getId()));
                holder.update_name.setText(data.get(position).getName());
                holder.update_age.setText(String.valueOf(data.get(position).getAge()));
                holder.update_address.setText(data.get(position).getAddress());
                holder.update_created.setText(data.get(position).getCreatedAt().toString());

                holder.info_layout.setVisibility(View.GONE);
                holder.update_layout.setVisibility(View.VISIBLE);
            }
        });

        holder.info_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dataService.delete.deleteOne(data.get(position).getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        data.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "아이템 삭제 완료", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }
        });

        holder.update_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap();
                map.put("name", holder.update_name.getText().toString());
                map.put("age", holder.update_age.getText().toString());
                map.put("address", holder.update_address.getText().toString());
                dataService.update.updateOne(data.get(position).getId(), map).enqueue(new Callback<Member>() {
                    @Override
                    public void onResponse(Call<Member> call, Response<Member> response) {
                        data.set(position, response.body());
                        notifyDataSetChanged();
                        Toast.makeText(context, "아이템 수정 완료", Toast.LENGTH_SHORT).show();
                        holder.info_layout.setVisibility(View.VISIBLE);
                        holder.update_layout.setVisibility(View.GONE);
                    }
                    @Override
                    public void onFailure(Call<Member> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    public String dateParser(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd hh:mm");
        return simpleDateFormat.format(date);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout info_layout, update_layout;
        TextView info_id, info_name, info_age, info_address, info_created, update_id, update_created;
        Button info_update, info_delete, update_btn;
        EditText update_name, update_age, update_address;

        ViewHolder(View itemView) {
            super(itemView);
            // 뷰 영역
            info_layout = itemView.findViewById(R.id.info_layout);
            info_id = itemView.findViewById(R.id.info_id);
            info_name = itemView.findViewById(R.id.info_name);
            info_age = itemView.findViewById(R.id.info_age);
            info_address = itemView.findViewById(R.id.info_address);
            info_created = itemView.findViewById(R.id.info_created);
            info_update = itemView.findViewById(R.id.info_update);
            info_delete = itemView.findViewById(R.id.info_delete);

            // 수정 영역
            update_layout = itemView.findViewById(R.id.update_layout);
            update_id = itemView.findViewById(R.id.update_id);
            update_name = itemView.findViewById(R.id.update_name);
            update_age = itemView.findViewById(R.id.update_age);
            update_address = itemView.findViewById(R.id.update_address);
            update_created = itemView.findViewById(R.id.update_created);
            update_btn = itemView.findViewById(R.id.update_btn);
        }
    }
}