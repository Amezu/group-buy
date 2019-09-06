package com.example.groupbuy.party;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupbuy.R;
import com.example.groupbuy.connection.HttpRequestDebug;

import java.util.HashMap;
import java.util.Map;

public class AddPersonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
    }

    public void addPerson(View view) {
        TextView nameView = (TextView) findViewById(R.id.personEdit);
        String name = nameView.getText().toString();
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        new HttpRequestDebug(this).addPersonToParty(createHashMap(name));
    }

    private Map createHashMap(String login){
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("login", login);
        return data;
    }
}
