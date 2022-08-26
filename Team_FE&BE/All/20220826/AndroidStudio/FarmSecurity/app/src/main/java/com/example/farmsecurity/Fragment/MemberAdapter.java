package com.example.farmsecurity.Fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmsecurity.API.LogAPI;
import com.example.farmsecurity.R;
import com.example.farmsecurity.Response.LogResponse;
import com.example.farmsecurity.RetrofitClient;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private List<LogResponse> data;
    private Context context;
    private LogAPI logAPI;
    private RetrofitClient retrofitClient;



    MemberAdapter(List<LogResponse> data, Context context, LogAPI logAPI, RetrofitClient retrofitClient) {
        this.data = data;
        this.context = context;
        this.logAPI = logAPI;
        this.retrofitClient = retrofitClient;
    }

    @Override
    public MemberAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new MemberAdapter.ViewHolder(inflater.inflate(R.layout.log_info, parent, false));
    }

    @Override
    public int getItemCount() {
        Log.d("size", String.valueOf(data.size()));
        return data.size() ;
    }

    @Override
    public void onBindViewHolder(final MemberAdapter.ViewHolder holder, final int position) {
//        holder.info_Num.setText(data.get(position).GetNum());
        holder.info_CameraName.setText(data.get(position).GetCameraName());
        //holder.info_CameraNum.setText(String.valueOf(data.get(position).GetCameraNum()));
        holder.info_Level.setText(data.get(position).GetLevel());
        //holder.info_Link.setText(data.get(position).GetLink());
        holder.info_Time.setText(data.get(position).GetTime());
    }

//    public String dateParser(Date date){
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd hh:mm");
//        return simpleDateFormat.format(date);
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout info_layout, update_layout;
        TextView info_Num, info_CameraName, info_CameraNum, info_CreateAt, info_Level, info_Link, info_Time;
        Button info_update, info_delete, update_btn;
        EditText update_name, update_age, update_address;

        ViewHolder(View itemView) {
            super(itemView);
            // 뷰 영역
            info_layout = itemView.findViewById(R.id.info_layout);
            //info_Num = itemView.findViewById(R.id.info_Num);
            info_CameraName = itemView.findViewById(R.id.info_CameraName);
            //info_CameraNum = itemView.findViewById(R.id.info_CameraNum);
            info_Level = itemView.findViewById(R.id.info_Level);
            //info_Link = itemView.findViewById(R.id.info_Link);
            info_Time = itemView.findViewById(R.id.info_Time);
            //info_Link = itemView.findViewById(R.id.info_Link);
            //info_Time = itemView.findViewById(R.id.info_Time);
        }
    }
}