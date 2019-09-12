package com.example.groupbuy.party;

import android.content.Intent;
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
import com.example.groupbuy.friends.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddPersonActivity extends AppCompatActivity {
    Party party;
    User[] peopleToAdd;
    private List<User> friends = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        Intent intent = getIntent();
        party = (Party) intent.getSerializableExtra("party");
        loadPeopleList("");
        addPersonWhenItemClicked();
        searchPersonByName();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(party.toString());
        actionBar.setSubtitle("Invite guest");
        actionBar.show();
    }

    private void loadPeopleList(final String search) {
        HttpRequest httpRequest = new HttpRequest(this.getBaseContext());
        httpRequest.loadFriendList(party.id, new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {
                friends = prepareFriendList(response);

//        TODO: Use RecyclerView to animate removing etc.
            }
        });
        httpRequest.loadPeopleList(party.id, new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {
                friends.remove(preparePeopleList(response));

//        TODO: Use RecyclerView to animate removing etc.
                peopleToAdd = friends.stream().filter(name -> name.toString().toLowerCase().contains(search.toLowerCase())).toArray(User[]::new);
                displayPeopleList();
            }
        });

    }

    private void displayPeopleList() {
        if(0 < peopleToAdd.length){
            ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, peopleToAdd);
            ListView view = findViewById(R.id.list);
            view.setAdapter(adapter);
        }
    }

    private List<User> preparePeopleList(JSONObject jsonObject) {
        return JsonParser.parsePeopleList(jsonObject);
    }

    private List<User> prepareFriendList(JSONObject jsonObject) {
        return JsonParser.parseFriendList(jsonObject);
    }

    private void addPersonWhenItemClicked() {
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
