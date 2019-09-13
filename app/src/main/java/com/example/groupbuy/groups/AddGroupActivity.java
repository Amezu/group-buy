package com.example.groupbuy.groups;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.groupbuy.R;
import com.example.groupbuy.connection.Callback;
import com.example.groupbuy.connection.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Groups");
        actionBar.setSubtitle("Create new group");
        actionBar.show();
    }

    public void addGroup(android.view.View view) {
        TextView nameView = findViewById(R.id.groupNameEdit);
        String name = nameView.getText().toString();
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        new HttpRequest(this).addGroup(createHashMap(name), new Callback(){
            @Override
            public void success(JSONObject response) throws JSONException {
                finish();
            }
        });

    }

    private Map createHashMap(String groupName) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("name", groupName);
        return data;
    }
}
