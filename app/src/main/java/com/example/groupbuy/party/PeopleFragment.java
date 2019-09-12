package com.example.groupbuy.party;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PeopleFragment extends Fragment {
    private List<User> people;
    public PeopleFragment() {
    }

    public static PeopleFragment newInstance(Party party) {
        PeopleFragment peopleFragment = new PeopleFragment();

        Bundle args = new Bundle();
        args.putSerializable("party", party);
        peopleFragment.setArguments(args);

        return peopleFragment;
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
        Party party = (Party) getArguments().getSerializable("party");
        new HttpRequest(getActivity()).loadPeopleList(party.id, new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {
                loadPeopleList(response);
                ListAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, people);
                ListView view = getView().findViewById(R.id.list);
                view.setAdapter(adapter);

//        TODO: Use RecyclerView to animate removing etc.

            }
        });

    }

    private void loadPeopleList(JSONObject jsonObject) {
        people = JsonParser.parsePeopleList(jsonObject);
    }

    private void openAddPersonActivity() {
        Intent intent = new Intent(getActivity(), AddPersonActivity.class);
        intent.putExtra("party", getArguments().getSerializable("party"));
        startActivity(intent);
    }
}
