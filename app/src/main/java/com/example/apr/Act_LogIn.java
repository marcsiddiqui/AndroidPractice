package com.example.apr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Act_LogIn extends AppCompatActivity {

    EditText txt_Username, txt_Password;
    TextView error_username, error_password;
    Button btnLogin;
    DatabaseConfiguration databaseConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_log_in);
        txt_Username = (EditText) findViewById(R.id.txt_Username);
        txt_Password = (EditText) findViewById(R.id.txt_Password);
        error_username = (TextView) findViewById(R.id.error_username);
        error_password = (TextView) findViewById(R.id.error_password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        databaseConfiguration = new DatabaseConfiguration(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernametxt = txt_Username.getText().toString();
                String passwordtxt = txt_Password.getText().toString();
                if (usernametxt.equals("") || usernametxt == null){
                    error_username.setText("Username is required!");
                }
                else {
                    error_username.setText("");
                }
                if (passwordtxt.equals("") || passwordtxt == null){
                    error_password.setText("Password is required!");
                }
                else {
                    error_password.setText("");
                }

                if (usernametxt.equals("") || usernametxt == null || passwordtxt.equals("") || passwordtxt == null) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields first!", Toast.LENGTH_SHORT).show();
                }
                else {
                    User user = databaseConfiguration.Login(usernametxt, passwordtxt);
                    if (user != null){

                        SessionInformation.LoggedInUser = user;

                        // Explicit
                        Intent obj = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(obj);

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void GoToSignUpPage(View view) {
        // Explicit
        Intent obj = new Intent(getApplicationContext(), Act_SignUp.class);
        startActivity(obj);
    }
}