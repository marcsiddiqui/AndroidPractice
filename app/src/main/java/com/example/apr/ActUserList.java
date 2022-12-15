package com.example.apr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class ActUserList extends AppCompatActivity {

    ListView userList_;
    ArrayList<User> li = new ArrayList<>();
    DatabaseConfiguration databaseConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_user_list);

        userList_ = (ListView) findViewById(R.id.userList_);
        databaseConfiguration = new DatabaseConfiguration(this);

        li = databaseConfiguration.GetAllUsers();

        ArrayList<HashMap<String, String>> liData = new ArrayList<>();
        for (User ur : li){
            liData.add(GenericFunctions.ConvertClassIntoHashMap(ur));
        }

        String from[] = { "FirstName", "LastName", "Username", "Email" };
        int to[] = { R.id.user_FirstName, R.id.user_LastName, R.id.user_Username, R.id.user_Email };

        UserAdapter cs_adapter = new UserAdapter(this, liData, R.layout.activity_act_user_list_template,from, to);
        userList_.setAdapter(cs_adapter);

        userList_.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Explicit
                Intent obj = new Intent(getApplicationContext(), EditUser.class);
                obj.putExtra("UserEditId", li.get(position).Id);
                startActivity(obj);
            }
        });
    }
}