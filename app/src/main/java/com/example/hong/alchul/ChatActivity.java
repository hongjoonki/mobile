package com.example.hong.alchul;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    ArrayList<ChatVO> list = new ArrayList<>();
    RecyclerView rv;
    Button btn;
    EditText edt;
    int[] imageID = {R.drawable.profile1, R.drawable.profile2, R.drawable.profile3};

    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

/* 텍스트뷰만 있을때만 쓸수있어
ArrayAdapter<ChatVO> adapter = new ArrayAdapter<ChatVO>(getApplicationContext(), R.layout.talklist, list);*/

        rv = findViewById(R.id.message_recycler_view);
        edt = findViewById(R.id.send_txt);
        btn = findViewById(R.id.send_btn);

        String code = getIntent().getStringExtra("StoreCode");

// Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("chatting"+code);


//로그인한 아이디
        id = getIntent().getStringExtra("UserName");
//lv.setAdapter(adapter);

//list.add(new ChatVO(R.drawable.profile3, "찡찡이", "안녕", "오후 4:42"));

        final ChatAdapter adapter = new ChatAdapter(getApplicationContext(), R.layout.chat_item, list, id);
        //((RecyclerView) findViewById(R.id.message_recycler_view)).setAdapter(adapter);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "내용을 입력하세요.", Toast.LENGTH_LONG).show();
                } else {
                    Date today = new Date();
                    SimpleDateFormat timeNow = new SimpleDateFormat("a K:mm");

                    StringBuffer sb = new StringBuffer(edt.getText().toString());
                    if (sb.length() >= 15) {
                        for (int i = 1; i <= sb.length() / 15; i++) {
                            sb.insert(15 * i, "\n");
                        }
                    }

                    //list.add(new ChatVO(R.drawable.profile1, id, sb.toString(), timeNow.format(today)));
                    //adapter.notifyDataSetChanged();

                    myRef.push().setValue(new ChatVO(R.drawable.profile1, id, sb.toString(), timeNow.format(today)));
                    edt.setText("");

                }
            }
        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                ChatVO value = dataSnapshot.getValue(ChatVO.class); // 괄호 안 : 꺼낼 자료 형태
                list.add(value);
                String userId = value.getId();
                Log.i("test", userId + id);



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

    }

}

