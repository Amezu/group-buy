package com.example.groupbuy.group;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupbuy.R;
import com.example.groupbuy.connection.HttpRequestDebug;
import com.example.groupbuy.connection.Session;

import java.util.HashMap;
import java.util.Map;

public class AddGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
    }

    public void addGroup(android.view.View view) {
        TextView nameView = (TextView) findViewById(R.id.groupNameEdit);
        String name = nameView.getText().toString();
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        Session session = Session.getInstance(getApplicationContext());
        HttpRequestDebug httpRequest = new HttpRequestDebug(this);
        httpRequest.addGroup(createHashMap(name));

//        TODO: send adding request

    }
    private Map createHashMap(String groupName){
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("groupName", groupName);
        return data;
    }
}
