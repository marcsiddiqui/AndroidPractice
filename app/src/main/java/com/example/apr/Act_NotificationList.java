package com.example.apr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class Act_NotificationList extends AppCompatActivity {

    ListView notificationList_;
    Button btnReadAllNotification;
    ArrayList<Notification> li = new ArrayList<>();
    DatabaseConfiguration databaseConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_notification_list);

        notificationList_ = (ListView) findViewById(R.id.notificationList_);
        btnReadAllNotification = (Button) findViewById(R.id.btnReadAllNotification);
        databaseConfiguration = new DatabaseConfiguration(this);

        GridLoad();

        btnReadAllNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseConfiguration.ReadAllNotificaitons();
                GridLoad();
            }
        });
    }

    public void GridLoad(){
        li = databaseConfiguration.GetAllUnReadNotifications();

        ArrayList<HashMap<String, String>> liData = new ArrayList<>();
        for (Notification nt : li){
            liData.add(GenericFunctions.ConvertClassIntoHashMap(nt));
        }

        String from[] = { "Title", "Description" };
        int to[] = { R.id.notificationTitle, R.id.notificationDescription };

        CS_Adapter cs_adapter = new CS_Adapter(this, liData, R.layout.activity_act_notification_template, from, to);
        notificationList_.setAdapter(cs_adapter);
    }
}