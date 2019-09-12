package com.example.groupbuy.party;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.groupbuy.MainActivity;
import com.example.groupbuy.R;
import com.example.groupbuy.connection.HttpRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PartyFragment extends Fragment {

    private List<Product> products;

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadPeoplePart();
        loadProductsPart();
    }

    private void loadProductsPart() {
        products = new ArrayList<>();
        products.add(new Product("nachos", "Mina", 13.11, true, 1, true));
        products.add(new Product("coca-cola 2l", "Mark", 2.21, false, 3, true));
        products.add(new Product("whisky 3l", "Louis", 5.79, false, 3, false));
        products.add(new Product("whisky 3l", "Olivia", 2.8, false, 4, true));

        ListAdapter productsAdapter = new ProductListAdapter(getActivity(), products);

        ListView productsView = getView().findViewById(R.id.list);
        productsView.setAdapter(productsAdapter);

        registerForContextMenu(productsView);

//        TODO: Use RecyclerView to animate removing etc.

        FloatingActionButton fab = getView().findViewById(R.id.fab);
        fab.setOnClickListener(view -> openAddProductActivity());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.list) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            String obj = lv.getItemAtPosition(acmi.position).toString();

            menu.setHeaderTitle(obj);
//            TODO: if(user is creator of item)
            menu.add("Edit");
//            TODO: if(user is creator of item or admin)
            menu.add("Remove");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getTitle() == "Edit") {
            openEditProductActivity(products.get(info.position));
        } else if (item.getTitle() == "Remove") {
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                if (which == DialogInterface.BUTTON_POSITIVE) {
//                    TODO: Remove product from DB

                    products.remove(info.position);
                    ListAdapter productsAdapter = new ProductListAdapter(getActivity(), products);
                    ListView productsView = getView().findViewById(R.id.list);
                    productsView.setAdapter(productsAdapter);
                }
            };
            AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
            ab.setMessage("Are you sure to delete?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        } else return false;

        return true;
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
        intent.putExtra("partyName", getArguments().getString("partyName", ""));
        startActivity(intent);
    }

    private void openEditProductActivity(Product product) {
        Intent intent = new Intent(getActivity(), EditProductActivity.class);
        intent.putExtra("partyName", getArguments().getString("partyName", ""));
        intent.putExtra("productName", product.getName());
        intent.putExtra("productPrice", product.getPrice());
        startActivity(intent);
    }

    private void openInvitePersonActivity() {
        Intent intent = new Intent(getActivity(), AddPersonActivity.class);
        intent.putExtra("partyName", getArguments().getString("partyName", ""));
        startActivity(intent);
    }

    private void openInvitedPeopleFragment() {
        MainActivity activity = (MainActivity) getActivity();
        activity.openPeopleFragment(getArguments().getString("partyName", ""));
    }
}
