package com.example.hong.alchul;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

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
    TextView mTextView;
    String aaa, bbb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_part1, container,false );
        context = container.getContext();
        final Button btn_start = (Button)view.findViewById(R.id.start);
        final Button btn_end = (Button)view.findViewById(R.id.end);

        mTextView = (TextView)view.findViewById(R.id.textView);


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNow=System.currentTimeMillis();
                mDate=new Date(mNow);
                aaa = mFormat.format(mDate);
                btn_end.setEnabled(true);
                btn_start.setEnabled(false);
                Toast.makeText(context, aaa+"에"+"출근하였습니다.", Toast.LENGTH_SHORT).show();

            }
        });

        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEnd=System.currentTimeMillis();
                mEndDate=new Date(mNow);
                bbb = mFormat.format(mDate);
                btn_end.setEnabled(false);
                btn_start.setEnabled(true);


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
                                Log.d("test", "debug5");
                            }
                        } catch (JSONException e) {
                            Log.d("test", "debug6");
                            e.printStackTrace();
                        }
                    }
                };
                MyFragment1_request m = new MyFragment1_request("sim3329", aaa,bbb, responseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(m);


                Toast.makeText(context, bbb+"퇴근하였습니다.", Toast.LENGTH_SHORT).show();

            }
        });







        return view;


    }
}
