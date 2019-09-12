package com.example.groupbuy.friends;

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

import com.example.groupbuy.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FriendListFragment extends Fragment {

    public FriendListFragment() {
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
        fab.setOnClickListener(view -> openAddFriendActivity());
    }

    private void loadFriendsList() {
        String[] people = {"Ashely", "Devin", "Ivan", "Gavin", "Lev", "Damon", "Lillian", "Kyra", "Forrest", "Owen", "Hayden", "Nash", "Dieter", "Holly", "Victor", "Aline", "Dominic", "Jennifer", "Logan"};
        ListAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, people);
        ListView view = getView().findViewById(R.id.list);
        view.setAdapter(adapter);
        registerForContextMenu(view);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.list) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            String obj = (String) lv.getItemAtPosition(acmi.position);

            menu.setHeaderTitle(obj);
            menu.add("Delete");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        TextView view = (TextView) info.targetView;

        Toast.makeText(getActivity(), item.getTitle() + " " + view.getText(), Toast.LENGTH_SHORT).show();
        if (item.getTitle() == "Delete") {
//            TODO: Send request
        } else return false;

        return true;
    }

    private void openAddFriendActivity() {
        Intent intent = new Intent(getActivity(), AddFriendActivity.class);
        startActivity(intent);
    }

    @Override
    public String toString() {
        return "Friends";
    }
}
