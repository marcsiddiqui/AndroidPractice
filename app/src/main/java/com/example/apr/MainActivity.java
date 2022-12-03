package com.example.apr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtWelCome;
    DatabaseConfiguration databaseConfiguration;
    Button btnNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        GenericFunctions.SendEmail(getApplicationContext());

        databaseConfiguration = new DatabaseConfiguration(this);
        databaseConfiguration.ExecuteQuery();

        if (SessionInformation.LoggedInUser == null){
            // Explicit
            Intent obj = new Intent(getApplicationContext(), Act_LogIn.class);
            startActivity(obj);
        }

        // test commit

        txtWelCome = (TextView) findViewById(R.id.txtWelCome);
        if (SessionInformation.LoggedInUser != null){
            String userType = "";
            if (SessionInformation.LoggedInUser.UserType == 1){

                btnNotifications = (Button) findViewById(R.id.btnNotifications);

                ArrayList<Notification> notifications = databaseConfiguration.GetAllUnReadNotifications();

                int notifcationCount = 0;

                if (notifications != null && notifications.size() > 0){
                    notifcationCount = notifications.size();
                }

                for (Notification notification : notifications){
                    Toast.makeText(this, notifcationCount + " notifications", Toast.LENGTH_SHORT).show();
                }



                btnNotifications.setText(notifcationCount + " new notifications");



            }
            else if (SessionInformation.LoggedInUser.UserType == 2){
                // Explicit
                Intent obj = new Intent(getApplicationContext(), ActUserDashboard.class);
                startActivity(obj);
            }
        }
    }

    public void LogOut(View view) {
        SessionInformation.LoggedInUser = null;
        // Explicit
        Intent obj = new Intent(getApplicationContext(), Act_LogIn.class);
        startActivity(obj);
    }

    public void GoToUserList(View view) {
        // Explicit
        Intent obj = new Intent(getApplicationContext(), ActUserList.class);
        startActivity(obj);
    }
}