package com.example.apr;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.prefs.BackingStoreException;

public class UserAdapter extends BaseAdapter {

    Context context;
    ArrayList<User> users;

    public UserAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.activity_act_user_list_template, null);

        ImageView imgProfileImg = (ImageView) convertView.findViewById(R.id.imgProfileImg);
        TextView user_Email = (TextView) convertView.findViewById(R.id.user_Email);
        TextView user_Username = (TextView) convertView.findViewById(R.id.user_Username);
        TextView user_LastName = (TextView) convertView.findViewById(R.id.user_LastName);
        TextView user_FirstName = (TextView) convertView.findViewById(R.id.user_FirstName);

        User user = users.get(position);

        user_Username.setText(user.Username);
        user_LastName.setText(user.LastName);
        user_FirstName.setText(user.FirstName);
        user_Email.setText(user.Email);

        imgProfileImg.setImageBitmap(BitmapFactory.decodeByteArray(user.ProfileImage,0,user.ProfileImage.length));

        return convertView;
    }
}
