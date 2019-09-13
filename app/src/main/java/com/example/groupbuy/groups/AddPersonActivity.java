package com.example.groupbuy.groups;

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

    private Group group;
    private User[] peopleToAdd;
    private List<User> friends = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        loadPeopleList("");
        addPersonWhenItemClicked();
        searchPersonByName();

        Intent intent = getIntent();
        group = (Group) intent.getSerializableExtra("group");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(group.toString());
        actionBar.setSubtitle("Add person to group");
        actionBar.show();
    }

    private void loadPeopleList(final String search) {
        HttpRequest httpRequest = new HttpRequest(this.getBaseContext());
        Intent intent = getIntent();
        group = (Group) intent.getSerializableExtra("group");
        httpRequest.loadFriendList(new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {
                friends = prepareFriendList(response);

//        TODO: Use RecyclerView to animate removing etc.
            }
        });
        httpRequest.loadPeopleGroup(group.getGroupId(), new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {
                friends.removeAll(preparePeopleList(response));
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
            User user = (User)parent.getItemAtPosition(position);
            addPerson(user);
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

    private void addPerson(User user) {
        Intent intent = getIntent();
        group = (Group) intent.getSerializableExtra("group");
        Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show();
        new HttpRequest(this).addPersonToGroup(group.getGroupId(), user.getUserId(), new Callback(){
            @Override
            public void success(JSONObject response) throws JSONException {
                finish();
            }
        });
    }

}
