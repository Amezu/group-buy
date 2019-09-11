package com.example.groupbuy.party;


import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.groupbuy.MainActivity;
import com.example.groupbuy.R;
import com.example.groupbuy.connection.Callback;
import com.example.groupbuy.connection.HttpRequest;
import com.example.groupbuy.connection.HttpRequestDebug;
import com.example.groupbuy.connection.JsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PartyListFragment extends Fragment {
    private List<Party> partyList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_with_fab, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadParties();
    }

    private void loadParties(){
        new HttpRequest(getActivity()).loadPartyList(new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {
                loadPartyList(response);

                FloatingActionButton fab = getView().findViewById(R.id.fab);
                fab.setOnClickListener(view -> openAddGroupActivity());
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.list) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Party obj = (Party) lv.getItemAtPosition(acmi.position);

            menu.setHeaderTitle(obj.partyName);
            menu.add("Rename");
            menu.add("Delete");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        TextView view = (TextView) info.targetView;
        int id = info.position;
        Toast.makeText(getActivity(), item.getTitle() + " " + view.getText(), Toast.LENGTH_SHORT).show();
        if (item.getTitle() == "Rename") {
//            Intent intent = new Intent(getActivity(), RenamePartyActivity.class);
//            intent.putExtra("partyId", partyList.get(id).id);
//            intent.putExtra("partyName", partyList.get(id).partyName);
//            startActivity(intent);
//            loadParties();
        } else if (item.getTitle() == "Delete") {
            new HttpRequest(getActivity()).deleteParty(partyList.get(id).id);
            loadParties();
        } else return false;

        return true;
    }



    private void loadPartyList(JSONObject jsonObject) {
        partyList = JsonParser.parsePartyList(jsonObject);
        ListAdapter partiesAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1, partyList);
        ListView partiesView = getView().findViewById(R.id.list);

        partiesView.setAdapter(partiesAdapter);
        registerForContextMenu(partiesView);
        partiesView.setOnItemClickListener((parent, view, position, id) -> {
            Party party = partyList.get(position);
            openPartyFragment(party);
        });
    }

    private void openAddGroupActivity() {
        Intent intent = new Intent(getActivity(), AddPartyActivity.class);
        startActivity(intent);
    }

    private void openPartyFragment(Party party) {
        MainActivity activity = (MainActivity) getActivity();
        activity.openPartyFragment(party);
    }

    @Override
    public String toString() {
        return "Parties";
    }


}
