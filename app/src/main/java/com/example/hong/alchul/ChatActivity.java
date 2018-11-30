package com.example.hong.alchul;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

public class ChatActivity extends AppCompatActivity {

    ArrayList<ChatVO> list = new ArrayList<>();
    ListView lv;
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

        lv = findViewById(R.id.List_view);
        edt = findViewById(R.id.send_txt);
        btn = findViewById(R.id.send_btn);

        String code = getIntent().getStringExtra("StoreCode");

// Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("message"+code);


//로그인한 아이디
        id = getIntent().getStringExtra("UserName");
//lv.setAdapter(adapter);

//list.add(new ChatVO(R.drawable.profile3, "찡찡이", "안녕", "오후 4:42"));

        final ChatAdapter adapter = new ChatAdapter(getApplicationContext(), R.layout.chat_item, list, id);
        ((ListView) findViewById(R.id.List_view)).setAdapter(adapter);

        final ChatAdapter2 adapter2 = new ChatAdapter2(getApplicationContext(), R.layout.my_message, list, id);
        ((ListView) findViewById(R.id.List_view)).setAdapter(adapter2);

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


                if (userId.equals(id)) {
                    adapter2.notifyDataSetChanged();
                } else {
                    adapter.notifyDataSetChanged();
                }

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