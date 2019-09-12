package com.example.groupbuy.party;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Contacts;
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
import com.example.groupbuy.friends.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PartyFragment extends Fragment {

    private List<Product> products;
    private List<User> people;
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
        Party party = (Party) getArguments().getSerializable("party");
        new HttpRequest(getActivity()).loadPeopleList(party.id, new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {
                loadPeopleList(response);
                formatPeoplePart();

//        TODO: Use RecyclerView to animate removing etc.

                FloatingActionButton fab = getView().findViewById(R.id.fab);
                fab.setOnClickListener(view -> openAddProductActivity());
            }
        });
    }
    private void formatPeoplePart(){
        String peopleShort;
        switch (people.size()) {
            case 0:
                peopleShort = "No people invited to party";
                break;
            case 1:
                peopleShort = people.get(0).toString();
                break;
            case 2:
                peopleShort = people.get(0).toString() + ", " + people.get(1).toString();
                break;
            case 3:
                peopleShort = people.get(0).toString() + ", " + people.get(1).toString() + ", " + people.get(2).toString();
                break;
            default:
                peopleShort = String.format("%s, %s, %s and %d more people", people.get(0).toString(), people.get(1).toString(), people.get(2).toString(), people.size() - 3);
                break;
        }

        TextView peopleShortView = getView().findViewById(R.id.peopleShortText);
        peopleShortView.setText(peopleShort);

        ImageView invitePerson = getView().findViewById(R.id.addPeopleButton);
        invitePerson.setOnClickListener(v -> openInvitePersonActivity());

        View peopleFrame = getView().findViewById(R.id.peopleFrame);
        peopleFrame.setOnClickListener(v -> openInvitedPeopleFragment());
    }

    private void loadPeopleList(JSONObject jsonObject) {
        people = JsonParser.parsePeopleList(jsonObject);
    }

    private void openAddProductActivity() {
        Intent intent = new Intent(getActivity(), AddProductActivity.class);
        intent.putExtra("party", getArguments().getSerializable("party"));
        startActivity(intent);
    }

    private void openInvitePersonActivity() {
        Intent intent = new Intent(getActivity(), AddPersonActivity.class);
        intent.putExtra("party", getArguments().getSerializable("party"));
        startActivity(intent);
    }

    private void openInvitedPeopleFragment() {
        MainActivity activity = (MainActivity) getActivity();
        Party party = (Party) getArguments().getSerializable("party");
        activity.openPeopleFragment((Party) getArguments().getSerializable("party"));
    }
}
