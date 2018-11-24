package com.example.hong.alchul.manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hong.alchul.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<UserItem> data;
    private int layout;

    public UserAdapter(Context context, int layout, ArrayList<UserItem> data){
        this.inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }

    @Override
    public int getCount(){
        return data.size();
    }

    @Override
    public String getItem(int position){
        return data.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = inflater.inflate(layout,parent, false);

        }
        UserItem useritem = data.get(position);

        TextView userName = (TextView) convertView.findViewById(R.id.name);
        userName.setText(useritem.getName());

        TextView userPhone = (TextView) convertView.findViewById(R.id.phone);
        userPhone.setText(useritem.getPhone());


        return convertView;
    }


}
