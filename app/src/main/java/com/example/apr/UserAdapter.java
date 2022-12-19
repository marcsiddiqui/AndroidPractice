package com.example.apr;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class UserAdapter extends SimpleAdapter {

    Context context;
    ArrayList<HashMap<String, String>> data;
    LayoutInflater layoutInflater;
    ArrayList<User> Users;
    DatabaseConfiguration databaseConfiguration;

    public UserAdapter(Context context, ArrayList<HashMap<String, String>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);

        this.context = context;
        this.data = data;
        this.layoutInflater.from(context);
        databaseConfiguration = new DatabaseConfiguration(context);
        this.Users = databaseConfiguration.GetAllUsers();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=super.getView(position, convertView, parent);


        ImageView imgProfileImg = (ImageView) view.findViewById(R.id.imgProfileImg);

        User user = Users.get(position);

        if (user != null && user.ProfileImage != null){
            try{
                imgProfileImg.setImageBitmap(BitmapFactory.decodeByteArray(user.ProfileImage,0,user.ProfileImage.length));
            }
            catch (Exception e) {
                imgProfileImg.setImageResource(R.drawable.profile);
            }
        }
        else {
            imgProfileImg.setImageResource(R.drawable.profile);
        }

        return view;
    }
}
