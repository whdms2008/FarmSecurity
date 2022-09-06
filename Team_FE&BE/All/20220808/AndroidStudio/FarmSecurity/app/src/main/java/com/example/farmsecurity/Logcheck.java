package com.example.farmsecurity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Logcheck extends Activity {

    int start_Year, start_Month, start_Day;
    int end_Year, end_Month, end_Day;
    TextView start_date;
    TextView end_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_check);

        Button btnReturn = (Button) findViewById(R.id.home);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        //텍스트뷰 2개 연결
        start_date = (TextView) findViewById(R.id.start_date);
        end_date =  (TextView) findViewById(R.id.end_date);

        //현재 날짜와 시간을 가져오기위한 Calendar 인스턴스 선언
        Calendar start = new GregorianCalendar();
        Calendar end = new GregorianCalendar();
        start_Year = start.get(Calendar.YEAR);
        start_Month = start.get(Calendar.MONTH);
        start_Day = start.get(Calendar.DAY_OF_MONTH);

        end_Year = end.get(Calendar.YEAR);
        end_Month = end.get(Calendar.MONTH);
        end_Day = end.get(Calendar.DAY_OF_MONTH);

        UpdateNow();//화면에 텍스트뷰에 업데이트 해줌.
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            //날짜 대화상자 버튼이 눌리면 대화상자를 보여줌
            case R.id.btn_change_start:
                //여기서 리스너도 등록함
                new DatePickerDialog(getApplicationContext(), mDateSetListener, start_Year,
                        start_Month, start_Day).show();
                break;
            case R.id.btn_change_end:
                //여기서 리스너도 등록함
                new DatePickerDialog(getApplicationContext(), mDateSetListener2, end_Year,
                        end_Month, end_Day).show();
                break;
        }
    }

    //시작 날짜 대화상자 리스너 부분
    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            //사용자가 입력한 값을 가져온뒤
            start_Year = year;
            start_Month = monthOfYear;
            start_Day = dayOfMonth;
            //텍스트뷰의 값을 업데이트함

            UpdateNow();
        }
    };

    //끝 날짜 대화상자 리스너 부분
    DatePickerDialog.OnDateSetListener mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            //사용자가 입력한 값을 가져온뒤
            end_Year = year;
            end_Month = monthOfYear;
            end_Day = dayOfMonth;
            //텍스트뷰의 값을 업데이트함

            UpdateNow();
        }
    };

    //텍스트뷰의 값을 업데이트 하는 메소드
    void UpdateNow() {
        start_date.setText(String.format("%d/%d/%d", start_Year, start_Month + 1, start_Day));
        end_date.setText(String.format("%d/%d/%d", end_Year, end_Month + 1, end_Day));
    }
}
