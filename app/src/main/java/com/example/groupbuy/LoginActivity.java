package com.example.groupbuy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.groupbuy.connection.HttpRequest;
import com.example.groupbuy.connection.HttpRequestDebug;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    HttpRequest httpRequest = new HttpRequest(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map body = getData();
                httpRequest.login(body);
            }
        });
    }

    private Map getData() {
        EditText etLogin = findViewById(R.id.etLogin);
        EditText etPassword = findViewById(R.id.etPassword);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("username", etLogin.getText().toString());
        data.put("password", etPassword.getText().toString());
        return data;
    }
}
