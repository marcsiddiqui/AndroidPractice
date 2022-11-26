package com.example.apr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txtWelCome;
    DatabaseConfiguration databaseConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseConfiguration = new DatabaseConfiguration(this);
        databaseConfiguration.ExecuteQuery();

        if (SessionInformation.LoggedInUser == null){
            // Explicit
            Intent obj = new Intent(getApplicationContext(), Act_LogIn.class);
            startActivity(obj);
        }

        txtWelCome = (TextView) findViewById(R.id.txtWelCome);
        if (SessionInformation.LoggedInUser != null){
            String userType = "";
            if (SessionInformation.LoggedInUser.UserType == 1){
                // Explicit
                Intent obj = new Intent(getApplicationContext(), Act_AdminDashboard.class);
                startActivity(obj);
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