package com.example.groupbuy.party;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.groupbuy.MainActivity;
import com.example.groupbuy.R;
import com.example.groupbuy.connection.Callback;
import com.example.groupbuy.connection.HttpRequest;
import com.example.groupbuy.connection.JsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PartyFragment extends Fragment {

    private List<Product> products;
    public PartyFragment() {
    }

    public static PartyFragment newInstance(Party party) {
        PartyFragment partyFragment = new PartyFragment();

        Bundle args = new Bundle();
        args.putSerializable("party", party);
        partyFragment.setArguments(args);

        return partyFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_party, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadPeoplePart();
        loadProductsPart();
    }

    private void loadProductsPart() {

        Party party = (Party) getArguments().getSerializable("party");
        new HttpRequest(getActivity()).loadProductList(party.id , new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {
                loadProductList(response);
                ListAdapter productListAdapter = new ProductListAdapter(getActivity(), products);

                ListView productListView = getView().findViewById(R.id.list);
                productListView.setAdapter(productListAdapter);

//        TODO: Use RecyclerView to animate removing etc.

                FloatingActionButton fab = getView().findViewById(R.id.fab);
                fab.setOnClickListener(view -> openAddProductActivity());
            }
        });

    }

    private void loadProductList(JSONObject jsonObject) {
        products = JsonParser.parseProductList(jsonObject);
    }

    private void loadPeoplePart() {
        String[] people = {"Ashely", "Devin", "Ivan", "Gavin", "Lev", "Damon", "Lillian", "Kyra", "Forrest", "Owen", "Hayden", "Nash", "Dieter", "Holly", "Victor", "Aline", "Dominic", "Jennifer", "Logan"};
        String peopleShort;
        switch (people.length) {
            case 0:
                peopleShort = "No people invited to party";
                break;
            case 1:
                peopleShort = people[0];
                break;
            case 2:
                peopleShort = people[0] + ", " + people[1];
                break;
            case 3:
                peopleShort = people[0] + ", " + people[1] + ", " + people[2];
                break;
            default:
                peopleShort = String.format("%s, %s, %s and %d more people", people[0], people[1], people[2], people.length - 3);
                break;
        }

        TextView peopleShortView = getView().findViewById(R.id.peopleShortText);
        peopleShortView.setText(peopleShort);

        ImageView invitePerson = getView().findViewById(R.id.addPeopleButton);
        invitePerson.setOnClickListener(v -> openInvitePersonActivity());

        View peopleFrame = getView().findViewById(R.id.peopleFrame);
        peopleFrame.setOnClickListener(v -> openInvitedPeopleFragment());
    }

    private void openAddProductActivity() {
        Intent intent = new Intent(getActivity(), AddProductActivity.class);
        intent.putExtra("partyName", getArguments().getSerializable("party"));
        startActivity(intent);
    }

    private void openInvitePersonActivity() {
        Intent intent = new Intent(getActivity(), AddPersonActivity.class);
        intent.putExtra("partyName", getArguments().getSerializable("party"));
        startActivity(intent);
    }

    private void openInvitedPeopleFragment() {
        MainActivity activity = (MainActivity) getActivity();
        Party party = (Party) getArguments().getSerializable("party");
        activity.openPeopleFragment((Party) getArguments().getSerializable("party"));
    }
}
