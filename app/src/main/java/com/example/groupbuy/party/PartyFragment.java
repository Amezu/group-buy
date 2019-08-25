package com.example.groupbuy.party;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupbuy.R;

import java.util.Locale;

public class PartyFragment extends Fragment {

    class Product {
        String name;
        boolean bought = false;

        Product(String name) {
            this.name = name;
        }

        void changeStatus() {
            bought = !bought;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public PartyFragment() {
    }

    public static PartyFragment newInstance(String partyName) {
        PartyFragment partyFragment = new PartyFragment();

        Bundle args = new Bundle();
        args.putString("partyName", partyName);
        partyFragment.setArguments(args);

        return partyFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_party, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadPeoplePart();
        loadProductsPart();
    }

    private void loadProductsPart() {
        String[] title = {"soki - 2l", "wódka - 3l", "chipsy - 2 paczki"};
        String[] subtitle = {"(0 propozycji)", "(1 propozycja)", "Ola, cheetosy"};
        Boolean[] bought = {false, false, true};
        Boolean[] clickable = {true, true, false};

        ListAdapter productListAdapter = new ProductListAdapter(getActivity(), title, subtitle, bought, clickable);
//                new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_multiple_choice, products);
//        TODO: Lista customizowana, checkbox z 3 stanami
//        https://stackoverflow.com/questions/3965484/custom-checkbox-image-android
//        https://developer.android.com/guide/topics/resources/drawable resource.html#StateList
//        https://www.codeproject.com/Articles/1006843/An-Efficient-Way-to-Make-Button-Color-Change-on-An
//        https://stackoverflow.com/questions/18395075/change-the-state-of-button-to-pressed
//        https://stackoverflow.com/questions/12702045/disable-checkbox-after-checked-android

        ListView productListView = getView().findViewById(R.id.list);
        productListView.setAdapter(productListAdapter);

        productListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Product product = (Product) parent.getItemAtPosition(position);
                        product.changeStatus();
                        if(product.bought)
                            Toast.makeText(getActivity(), "you marked " + product + " as bought", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        FloatingActionButton fab = getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddProductActivity();
            }
        });
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
                peopleShort = String.format(Locale.US, "%s, %s, %s and %d more people", people[0], people[1], people[2], people.length - 3);
                break;
        }

        TextView peopleShortView = getView().findViewById(R.id.peopleShortText);
        peopleShortView.setText(peopleShort);

        ImageView invitePerson = getView().findViewById(R.id.addPeopleButton);
        invitePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void openAddProductActivity() {
        Intent intent = new Intent(getActivity(), AddProductActivity.class);
        intent.putExtra("partyName", getArguments().getString("partyName", ""));
        startActivity(intent);
    }

    public void openInvitePersonActivity() {
//        Intent intent = new Intent(getActivity(), AddProductActivity.class);
//        intent.putExtra("partyName", getArguments().getString("partyName", ""));
//        startActivity(intent);
    }
}
