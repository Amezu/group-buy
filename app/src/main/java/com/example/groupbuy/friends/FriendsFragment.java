package com.example.groupbuy.friends;

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
import com.example.groupbuy.party.AddPersonActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FriendsFragment extends Fragment {

    public FriendsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_with_fab, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadFriendsList();

        FloatingActionButton fab = getView().findViewById(R.id.fab);
        fab.setOnClickListener(view -> openAddPersonActivity());
    }

    private void loadFriendsList() {
        String[] people = {"Ashely", "Devin", "Ivan", "Gavin", "Lev", "Damon", "Lillian", "Kyra", "Forrest", "Owen", "Hayden", "Nash", "Dieter", "Holly", "Victor", "Aline", "Dominic", "Jennifer", "Logan"};
        ListAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, people);
        ListView view = getView().findViewById(R.id.list);
        view.setAdapter(adapter);
    }

    private void openAddPersonActivity() {
        Intent intent = new Intent(getActivity(), AddPersonActivity.class);
        intent.putExtra("partyName", getArguments().getString("partyName", ""));
        startActivity(intent);
    }
}
