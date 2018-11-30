package com.example.hong.alchul.parttime;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

//import com.example.hong.alchul.ChattingActivity;
import com.example.hong.alchul.LoginActivity;
import com.example.hong.alchul.R;
import com.example.hong.alchul.RegisterActivity;
import com.example.hong.alchul.manager.manager_frag1;
import com.example.hong.alchul.parttime.phone_list;
import com.example.hong.alchul.parttime.MyFragment1;
import com.example.hong.alchul.parttime.MyFragment2;
import com.example.hong.alchul.parttime.MyFragment3;

public class partime_home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    Bundle bundle = new Bundle(7);
    //fragment로 정보넘기기위해 bundle사용
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partime_home);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View nav_header_view = navigationView.inflateHeaderView(R.layout.nav_header);



        TextView header_id = (TextView) nav_header_view.findViewById(R.id.loginname);


        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        Intent intent = getIntent();   //로그인유저의 정보를 받아온다.
        final String userId = intent.getStringExtra("UserId");
        String userName = intent.getStringExtra("UserName");
        String userPhoneNum = intent.getStringExtra("UserPhoneNum");
        String userStat = intent.getStringExtra("UserStat");
        String storeCode = intent.getStringExtra("StoreCode");
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");


        String message = "회원정보: " + userStat + "\n안녕하십니까 " + userId + "님";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();        //intent 받아오기

        LinearLayout button1 = (LinearLayout)findViewById(R.id.button1);
        LinearLayout button2 = (LinearLayout)findViewById(R.id.button2);
        LinearLayout button3 = (LinearLayout)findViewById(R.id.button3);           //버튼 인식

        header_id.setText(userName);


        if (title != null || content != null) {
            bundle.putString("UserId", userId);
            bundle.putString("UserName", userName);
            bundle.putString("UserPhoneNum", userPhoneNum);
            bundle.putString("UserStat", userStat);
            bundle.putString("StoreCode", storeCode);
            bundle.putString("title", title);
            bundle.putString("content", content);
            MyFragment3 fragment3 = new MyFragment3();
            fragment3.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment3).commit();

        } else {
            //fragment에 정보넘길것들.

            bundle.putString("UserId", userId);         //bundle에 정보를 추가한다.
            bundle.putString("UserName", userName);
            bundle.putString("UserPhoneNum", userPhoneNum);
            bundle.putString("UserStat", userStat);
            bundle.putString("StoreCode", storeCode);         //번들에 값 추기

            MyFragment1 fragment1 = new MyFragment1();
            fragment1.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment1).commit();
        }






















        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                MyFragment1 fragment1= new MyFragment1();
                fragment1.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment1).commit();

            }
        });         //fragment1로 넘어가는 이벤트리스터

        button2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                MyFragment2 fragment2 = new MyFragment2();
                fragment2.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment2).commit();

            }
        });          //fragment2로 넘어가는 이벤트리스터

        button3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                MyFragment3 fragment3 = new MyFragment3();
                fragment3.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment3).commit();

            }
        });      //fragment3로 넘어가는 이벤트리스터

    }

    @SuppressWarnings("statementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        Log.i("test", "sfsfsd");
        int id = item.getItemId();

        if(id==R.id.contact){
            phone_list list_phone = new phone_list();
            list_phone.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, list_phone).commit();



        }else if(id==R.id.setting){

        }else if(id==R.id.logout){
            AlertDialog.Builder builder = new AlertDialog.Builder(partime_home.this);
            builder.setMessage("로그아웃 하시겠습니까?")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(partime_home.this, LoginActivity.class);
                            partime_home.this.startActivity(intent);
                        }
                    }).setNegativeButton("취소",null)
                    .create()
                    .show();

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

