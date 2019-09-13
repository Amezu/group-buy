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

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.groupbuy.MainActivity;
import com.example.groupbuy.R;
import com.example.groupbuy.connection.HttpRequest;
import com.example.groupbuy.connection.Callback;
import com.example.groupbuy.connection.JsonParser;
import com.example.groupbuy.connection.Session;
import com.example.groupbuy.friends.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
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
        registerForContextMenu(productListView);

//        TODO: Use RecyclerView to animate removing etc.

                FloatingActionButton fab = getView().findViewById(R.id.fab);
                fab.setOnClickListener(view -> openAddProductActivity(party));
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
                fab.setOnClickListener(view -> openAddProductActivity(party));
            }
        });
        FloatingActionButton fab = getView().findViewById(R.id.fab);
        fab.setOnClickListener(view -> openAddProductActivity(party));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.list) {
            Party party = (Party) getArguments().getSerializable("party");
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Product product = (Product) lv.getItemAtPosition(acmi.position);
            Session session = Session.getInstance(getContext());
            menu.setHeaderTitle(product.toString());
            if(product.getUser().equals(session.getUsername()))
                menu.add("Edit");
            if(product.getUser().equals(session.getUsername()) || party.ownerId.equals(session.getUserID()))
                menu.add("Remove");
        }
    }
//
    @Override
    public boolean onContextItemSelected (MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Party party = (Party) getArguments().getSerializable("party");
        if (item.getTitle() == "Edit") {
            openEditProductActivity(products.get(info.position));
        } else if (item.getTitle() == "Remove") {
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    new HttpRequest(getActivity()).deleteProduct(party.id, products.get(info.position).getId(), new Callback(){
                        @Override
                        public void success(JSONObject response) throws JSONException {
                            products.remove(info.position);
                            ListAdapter productsAdapter = new ProductListAdapter(getActivity(), products);
                            ListView productsView = getView().findViewById(R.id.list);
                            productsView.setAdapter(productsAdapter);
                        }
                    });
                }
            };
            AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
            ab.setMessage("Are you sure to delete?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        } else return false;

        return true;
    }

    private void formatPeoplePart() {
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

    private void openAddProductActivity(Party party) {
        Intent intent = new Intent(getActivity(), AddProductActivity.class);
        intent.putExtra("party", getArguments().getSerializable("party"));
        startActivity(intent);
    }

    private void openEditProductActivity(Product product) {
        Intent intent = new Intent(getActivity(), EditProductActivity.class);
        intent.putExtra("party", getArguments().getSerializable("party"));
        intent.putExtra("product", product);
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

    @Override
    public void onResume() {
        super.onResume();
        loadPeoplePart();
        loadProductsPart();
    }
}
