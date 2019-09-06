package com.example.groupbuy.party;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
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

        loadPeopleList();
        ListView peopleListView = findViewById(R.id.list);
        peopleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = String.valueOf(parent.getItemAtPosition(position));
                addPerson(name);
            }
        });
    }

    private void loadPeopleList() {
        String[] people = {"Ashely", "Devin", "Ivan", "Gavin", "Lev", "Damon", "Lillian", "Kyra", "Forrest", "Owen", "Hayden", "Nash", "Dieter", "Holly", "Victor", "Aline", "Dominic", "Jennifer", "Logan"};
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, people);
        ListView view = findViewById(R.id.list);
        view.setAdapter(adapter);
    }

    private void addPerson(String name) {
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        new HttpRequestDebug(this).addPersonToParty(createHashMap(name));
        finish();
    }

    private Map createHashMap(String login){
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("login", login);
        return data;
    }
}
