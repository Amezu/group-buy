package com.example.groupbuy;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.groupbuy.connection.HttpRequestDebug;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getName();
    private HttpRequestDebug httpRequest = new HttpRequestDebug(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button btnLogin = findViewById(R.id.btnLogin);
        final Intent intent = new Intent(this, MainActivity.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                final Map data = getData();
                if (validate(data)) {
                    httpRequest.register(data);
                }
            }
        });
    }

    private Map getData() {
        final EditText loginField = findViewById(R.id.etLogin);
        final EditText emailField = findViewById(R.id.etEmail);
        final EditText passwordField = findViewById(R.id.etPassword);
        final EditText rePasswordField = findViewById(R.id.etRePassword);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("username", loginField.getText().toString());
        data.put("email", emailField.getText().toString());
        data.put("phash", passwordField.getText().toString());
        data.put("rePassword", rePasswordField.getText().toString());
        return data;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean validate(Map data) {
        final String email = Objects.requireNonNull(data.get("email")).toString();
        final String username = Objects.requireNonNull(data.get("username")).toString();
        final String password = Objects.requireNonNull(data.get("phash")).toString();
        final String rePassword = Objects.requireNonNull(data.get("rePassword")).toString();

        boolean isAnyFieldEmpty = email.equals("") || username.equals("") || password.equals("");
        boolean arePasswordsMatching = password.equals(rePassword);

        if (isAnyFieldEmpty) {
            Toast.makeText(this, "Fields should not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!arePasswordsMatching) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
