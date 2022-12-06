package com.example.apr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class CS_Adapter extends SimpleAdapter {

    Context context;
    ArrayList<HashMap<String, String>> data;
    LayoutInflater layoutInflater;

    public CS_Adapter(Context context, ArrayList<HashMap<String, String>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);

        this.context = context;
        this.data = data;
        this.layoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=super.getView(position, convertView, parent);

//        ImageView imageView = (ImageView) view.findViewById(R.id.liImg);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, data.get(position).get("Heading"), Toast.LENGTH_SHORT).show();
//            }
//        });

//        TextView textView = (TextView) view.findViewById(R.id.markAsReadText);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, data.get(position).get("Title"), Toast.LENGTH_SHORT).show();
//            }
//        });

        return view;
    }
}
