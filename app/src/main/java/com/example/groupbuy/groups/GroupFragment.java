package com.example.groupbuy.groups;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.groupbuy.R;
import com.example.groupbuy.connection.Callback;
import com.example.groupbuy.connection.HttpRequest;
import com.example.groupbuy.connection.JsonParser;
import com.example.groupbuy.friends.User;
import com.example.groupbuy.party.Party;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GroupFragment extends Fragment {
    private List<User> people;
    public GroupFragment() {
    }

    public static GroupFragment newInstance(Group group) {
        GroupFragment GroupFragment = new GroupFragment();

        Bundle args = new Bundle();
        args.putSerializable("group", group);
        GroupFragment.setArguments(args);

        return GroupFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_with_fab, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadPeopleList();

        FloatingActionButton fab = getView().findViewById(R.id.fab);
        fab.setOnClickListener(view -> openAddPersonActivity());
    }

    private void loadPeopleList() {
        Group group = (Group) getArguments().getSerializable("group");
        new HttpRequest(getActivity()).loadPeopleGroup(group.getGroupId(), new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {
                loadPeopleList(response);
                ListAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, people);
                ListView view = getView().findViewById(R.id.list);
                view.setAdapter(adapter);
            }
        });

    }

    private void loadPeopleList(JSONObject jsonObject) {
        people = JsonParser.parsePeopleList(jsonObject);
    }


    private void openAddPersonActivity() {
        Intent intent = new Intent(getActivity(), AddPersonActivity.class);
        intent.putExtra("group", getArguments().getSerializable("group"));
        startActivity(intent);
    }
    @Override
    public void onResume() {
        super.onResume();
        loadPeopleList();
    }
}
