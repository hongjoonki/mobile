package com.example.hong.alchul;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.EventListener;

public class MyFragment2 extends Fragment {
    View view;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        view= inflater.inflate(R.layout.fragment_part2, container,false );

        /*Calendar beginTime = Calendar.getInstance();
        beginTime.set(2018, 11, 10, 7,30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2018,11,16,11,40);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "soccer")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "seoultech")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
        startActivity(intent);*/



        return view;

    }
}

