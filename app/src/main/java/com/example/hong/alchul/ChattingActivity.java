package com.example.hong.alchul;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChattingActivity extends AppCompatActivity {

    String userId;
    String userName;
    String userPhoneNum;
    String userStat;
    String storeCode;
    EditText editText;
    Button sendButton;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ListView listView;

    ArrayList<String> array = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        listView = (ListView) findViewById(R.id.listView);
        sendButton = (Button) findViewById(R.id.send_btn);
        editText = (EditText)findViewById(R.id.send_txt);

        Intent intent = getIntent();
        userId = intent.getStringExtra("UserId");
        userName = intent.getStringExtra("UserName");
        userPhoneNum = intent.getStringExtra("UserPhoneNum");
        userStat = intent.getStringExtra("UserStat");
        storeCode = intent.getStringExtra("StoreCode");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.chat_item, array);
        listView.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                array.add(userName+" : "+editText.getText().toString());
                adapter.notifyDataSetChanged();
                editText.setText("");
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) { // view:클릭한 뷰 position: id:position이랑 일반적으로 같다
                final int position = i;

                AlertDialog.Builder builder= new AlertDialog.Builder(getApplicationContext());
                //builder에게 옵션주기
                builder.setTitle("삭제하기");
                builder.setMessage("이 메시지를 삭제할까요?");

                //3개 가능/ 메시지, 일어나야하는일
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        array.remove(position);
                        adapter.notifyDataSetChanged();//새로고침
                    }
                });
                //취소
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();

                return false;  //true하면 일반클릭과 롱클릭 둘다 먹고 false하면 롱클릭만 먹는다
            }
        });


    }
}
