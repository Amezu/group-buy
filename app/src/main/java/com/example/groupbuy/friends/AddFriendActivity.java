package com.example.groupbuy.friends;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.groupbuy.R;
import com.example.groupbuy.connection.HttpRequestDebug;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AddFriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        searchPersonByName();
        addFriendWhenItemClicked();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Friends");
        actionBar.setSubtitle("Add friend");
        actionBar.show();
    }

    private void loadPeopleList(final String search) {
        String[] allPeople = {"Ashely", "Devin", "Ivan", "Gavin", "Lev", "Damon", "Lillian", "Kyra", "Forrest", "Owen", "Hayden", "Nash", "Dieter", "Holly", "Victor", "Aline", "Dominic", "Jennifer", "Logan"};
        String[] people = search.isEmpty() ? new String[]{} : Arrays.stream(allPeople).filter(name -> name.toLowerCase().contains(search.toLowerCase())).toArray(String[]::new);
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, people);
        ListView view = findViewById(R.id.list);
        view.setAdapter(adapter);
    }

    private void addFriendWhenItemClicked() {
        ListView peopleListView = findViewById(R.id.list);
        peopleListView.setOnItemClickListener((parent, view, position, id) -> {
            String name = String.valueOf(parent.getItemAtPosition(position));
            addPerson(name);
        });
    }

    private void searchPersonByName() {
        EditText nameEdit = findViewById(R.id.edit);
        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                loadPeopleList(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private void addPerson(String name) {
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        new HttpRequestDebug(this).addPersonToParty(createHashMap(name));
        finish();
    }

    private Map createHashMap(String login) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("login", login);
        return data;
    }
}
