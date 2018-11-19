package com.example.hong.alchul;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hong.alchul.request.MyFragment1_request;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyFragment1 extends Fragment {
    View view;
    private Context context;
    long mNow, mEnd;
    Date mDate, mEndDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat mFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    String startwork, endwork, startday;
    Button btn_start, btn_end;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_part1, container,false );                     //fragment화면 설정
        context = container.getContext();
        btn_start = (Button)view.findViewById(R.id.start);
        btn_end = (Button)view.findViewById(R.id.end);
        String userId = getArguments().getString("UserId");
        String userName = getArguments().getString("UserName");
        String userPhoneNum = getArguments().getString("UserPhoneNum");
        String userStat = getArguments().getString("UserStat");
        String storeCode = getArguments().getString("StoreCode");


        TextView textView1, textView2, textView3, textView4;

        textView1 = (TextView)view.findViewById(R.id.textView1);
        textView2 = (TextView)view.findViewById(R.id.textView2);
        textView3 = (TextView)view.findViewById(R.id.textView3);
        textView4 = (TextView)view.findViewById(R.id.textView4);

        textView1.setText("이름:   "+ userName);
        textView2.setText("직종:   "+ userStat);
        textView3.setText("전화번호:   "+ userPhoneNum);
        textView4.setText("지점:   "+ storeCode);


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNow=System.currentTimeMillis();
                mDate=new Date(mNow);
                startwork = mFormat.format(mDate);
                startday  = mFormat1.format(mDate);
                btn_end.setEnabled(true);
                btn_start.setEnabled(false);
                Toast.makeText(context, startwork+"에"+"출근하였습니다.", Toast.LENGTH_SHORT).show();

            }           //출근 버튼눌렀을 때.
        });

        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEnd=System.currentTimeMillis();
                mEndDate=new Date(mNow);
                endwork = mFormat.format(mDate);
                btn_end.setEnabled(false);
                btn_start.setEnabled(true);


                String userId = getArguments().getString("UserId");


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                            }
                            else {

                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                };
                MyFragment1_request m = new MyFragment1_request(userId, startwork, endwork, startday, responseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(m);



                Toast.makeText(context, endwork+"퇴근하였습니다.", Toast.LENGTH_SHORT).show();      //퇴근버튼눌렀을때 디비저장.변수 startwork랑 endwork에 시간 저장.

            }
        });







        return view;


    }
}
