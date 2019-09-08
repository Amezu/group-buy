package com.example.groupbuy.party;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupbuy.R;
import com.example.groupbuy.connection.HttpRequestDebug;

import java.util.HashMap;
import java.util.Map;

public class AddPartyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_party);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Parties");
        actionBar.setSubtitle("Create new party");
        actionBar.show();
    }

    public void addParty(android.view.View view) {
        TextView nameView = findViewById(R.id.partyNameEdit);
        String name = nameView.getText().toString();
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        new HttpRequestDebug(this).addParty(createHashMap(name));
    }

    private Map createHashMap(String groupName){
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("groupName", groupName);
        return data;
    }
}
