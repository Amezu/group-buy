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
import com.example.groupbuy.connection.Callback;
import com.example.groupbuy.connection.HttpRequest;
import com.example.groupbuy.connection.HttpRequestDebug;
import com.example.groupbuy.connection.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
        new HttpRequest(getApplication()).loadPeopleList(new Callback(){
            @Override
            public void success(JSONObject response) throws JSONException {
                preparePeopleList(response, search);
            }
        });
    }

    private void preparePeopleList(JSONObject response, final String search){
        List<User> allPeople = JsonParser.parseAllPeopleList(response);
        User[] people = search.isEmpty() ? new User[]{} : allPeople.stream().filter(name -> name.toString().toLowerCase().contains(search.toLowerCase())).toArray(User[]::new);
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

        new HttpRequest(this).getUserId(name, new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {
                sendRequestToAdd(response.getString("id"));
            }
        });

    }

    private void sendRequestToAdd(String id){
        new HttpRequest(this).addFriend(id, new Callback() {
            @Override
            public void success(JSONObject response2) throws JSONException {
                finish();
            }
        });
    }
}
