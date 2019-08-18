package com.example.groupbuy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.groupbuy.connection.HttpRequestDebug;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        TextView mTextMessage = findViewById(R.id.message);
        Button loginButton = findViewById(R.id.btLogin);
        Button registerButton = findViewById(R.id.btRegister);

        openMainActivityIfActiveSessionExists();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void openMainActivityIfActiveSessionExists() {
        HttpRequestDebug httpRequest = new HttpRequestDebug(this);
        httpRequest.openMainActivityIfActiveSessionExists();
    }
}