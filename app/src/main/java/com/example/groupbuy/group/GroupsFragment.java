package com.example.groupbuy.group;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

import com.example.groupbuy.MainActivity;
import com.example.groupbuy.R;
import com.example.groupbuy.connection.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.groupbuy.connection.HttpRequestDebug;

import java.util.ArrayList;
import java.util.List;

public class GroupsFragment extends Fragment {
    HttpRequestDebug httpRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_with_fab, null);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.list) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            String obj = (String) lv.getItemAtPosition(acmi.position);

            menu.setHeaderTitle(obj);
            menu.add("Rename");
            menu.add("Delete");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        TextView view = (TextView) info.targetView;

        Toast.makeText(getActivity(), item.getTitle() + " " + view.getText(), Toast.LENGTH_SHORT).show();
        if (item.getTitle() == "Rename") {
        }
        else if (item.getTitle() == "Delete") {
        }
        else {
            return false;
        }
        return true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        httpRequest = new HttpRequestDebug(getActivity());
        httpRequest.loadGroupList(new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {
                createGroupList(response);
            }
        });
    }

    public void createGroupList(JSONObject s) {
        List<String> groups = new ArrayList<>();
        try {
            JSONArray array = s.getJSONArray("groupList");
            for(int i=0; i<array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                groups.add(object.getString("groupName"));
            }
        }
        catch(JSONException e) {
            throw new RuntimeException(e);
        }

        ListAdapter groupListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, groups);
        ListView groupListView = getView().findViewById(R.id.list);
        groupListView.setAdapter(groupListAdapter);
        registerForContextMenu(groupListView);

        groupListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String group = String.valueOf(parent.getItemAtPosition(position));
                        openProductsList(group);
                    }
                }
        );

        FloatingActionButton fab = getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddGroupActivity();
            }
        });
    }

    public void openAddGroupActivity() {
        Intent intent = new Intent(getActivity(), AddGroupActivity.class);
        startActivity(intent);
    }

    public void openProductsList(String group) {
        MainActivity activity = (MainActivity) getActivity();
        activity.openProductsFragment(group);
    }
}
