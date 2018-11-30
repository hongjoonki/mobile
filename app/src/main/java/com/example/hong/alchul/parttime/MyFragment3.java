package com.example.hong.alchul.parttime;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hong.alchul.ChatActivity;
//import com.example.hong.alchul.ChattingActivity;
import com.example.hong.alchul.ChatVO;
import com.example.hong.alchul.NoticeActivity;
import com.example.hong.alchul.NoticeAdapter;
import com.example.hong.alchul.NoticeVO;
import com.example.hong.alchul.R;
import com.example.hong.alchul.manager.UserAdapter;
import com.example.hong.alchul.manager.UserItem;
import com.example.hong.alchul.manager.user_calendar;
import com.example.hong.alchul.request.listRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MyFragment3 extends Fragment {
    View view;
    private Context context;

    private ArrayList<UserItem> data = null;
    String userId;
    String userName;
    String userPhoneNum;
    String userStat;
    String storeCode;
    ListView listView;

    String title = null;
    String content = null;

    ArrayList<NoticeVO> listVO = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_part3, container, false);
        context = container.getContext();
        userId = getArguments().getString("UserId");
        userName = getArguments().getString("UserName");
        userPhoneNum = getArguments().getString("UserPhoneNum");
        userStat = getArguments().getString("UserStat");
        storeCode = getArguments().getString("StoreCode");
        title = getArguments().getString("title");
        content = getArguments().getString("content");

        // firebase 객체 생성
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("notice"+storeCode);

        listView = (ListView) view.findViewById(R.id.List);
        data = new ArrayList<>();

        final LinearLayout edit = (LinearLayout)view.findViewById(R.id.edit);
        final LinearLayout chat = (LinearLayout) view.findViewById(R.id.chat);

        edit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(context, NoticeActivity.class);
                intent.putExtra("UserId", userId);
                intent.putExtra("UserName", userName);
                intent.putExtra("UserPhoneNum", userPhoneNum);
                intent.putExtra("UserStat", userStat);
                intent.putExtra("StoreCode", storeCode);
                startActivity(intent);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("UserId", userId);
                intent.putExtra("UserName", userName);
                intent.putExtra("UserPhoneNum", userPhoneNum);
                intent.putExtra("UserStat", userStat);
                intent.putExtra("StoreCode", storeCode);
                startActivity(intent);
            }
        });

        final NoticeAdapter adapter = new NoticeAdapter(context, R.layout.notice_item, listVO, userId);
        listView.setAdapter(adapter);

        if (title != null || content != null) {
            if (content.equals("")) {
                Toast.makeText(context, "내용을 입력하세요.", Toast.LENGTH_LONG).show();
            } else {
                Date today = new Date();
                SimpleDateFormat timeNow = new SimpleDateFormat("a K:mm");

                StringBuffer sb = new StringBuffer(content);
                if (sb.length() >= 15) {
                    for (int i = 1; i <= sb.length() / 15; i++) {
                        sb.insert(15 * i, "\n");
                    }
                }

                //list.add(new ChatVO(R.drawable.profile1, id, sb.toString(), timeNow.format(today)));
                //adapter.notifyDataSetChanged();

                myRef.push().setValue(new NoticeVO(userId, content, title, timeNow.format(today)));
                content="";
                title="";
            }
        }

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                NoticeVO value = dataSnapshot.getValue(NoticeVO.class); // 괄호 안 : 꺼낼 자료 형태
                listVO.add(value);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

}
