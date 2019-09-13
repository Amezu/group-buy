package com.example.groupbuy.groups;

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
import com.example.groupbuy.connection.JsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GroupListFragment extends Fragment {

    private List<Group> groups;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_with_fab, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createGroupList();
    }

    private void createGroupList() {
        new HttpRequest(getActivity()).loadGroupList(new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {
                loadGroupList(response);

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
            Group group = (Group) lv.getItemAtPosition(acmi.position);

            menu.setHeaderTitle(group.toString());
            if(group.getOwner().equals("true")) {
                menu.add("Rename");
                menu.add("Delete");
            }
            else{
                menu.add("Leave");
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        TextView view = (TextView) info.targetView;
        HttpRequest httpRequest = new HttpRequest(getActivity());
        Toast.makeText(getActivity(), item.getTitle() + " " + view.getText(), Toast.LENGTH_SHORT).show();
        if (item.getTitle() == "Rename") {

        } else if (item.getTitle() == "Delete") {
            httpRequest.deleteGroup(groups.get(info.position).getGroupId(), new Callback(){
                @Override
                public void success(JSONObject response) throws JSONException {
                    createGroupList();
                }
            });
        } else if (item.getTitle() == "Leave") {
            //httpRequest.removeUserFromGroup();
        } else return false;

        return true;
    }

    private void loadGroupList(JSONObject jsonObject) {
        groups = JsonParser.parseGroupList(jsonObject);
        ListAdapter groupsAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1, groups
                );
        ListView groupsView = getView().findViewById(R.id.list);

        groupsView.setAdapter(groupsAdapter);
        registerForContextMenu(groupsView);
        groupsView.setOnItemClickListener(
                (parent, view, position, id) -> {
                    Group group = (Group) parent.getItemAtPosition(position);
                    openGroupFragment(group);
                }
        );
    }

    private void openAddGroupActivity() {
        Intent intent = new Intent(getActivity(), AddGroupActivity.class);
        startActivity(intent);
    }

    private void openGroupFragment(Group group) {
        MainActivity activity = (MainActivity) getActivity();
        activity.openGroupFragment(group);
    }

    @Override
    public String toString() {
        return "Groups";
    }

    @Override
    public void onResume() {
        super.onResume();
        createGroupList();
    }
}
