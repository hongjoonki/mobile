package com.example.hong.alchul;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MyFragment1 extends Fragment {
    View view;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_part1, container,false );
        context = container.getContext();
        Button btn_start = (Button)view.findViewById(R.id.start);
        Button btn_end = (Button)view.findViewById(R.id.end);


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "출근하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "퇴근하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });


        return view;


    }
}
