package com.example.groupbuy.groups;

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
import com.example.groupbuy.connection.HttpRequestDebug;
import com.example.groupbuy.connection.JsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

public class GroupListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_with_fab, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new HttpRequestDebug(getActivity()).loadGroupList(new Callback() {
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
        } else if (item.getTitle() == "Delete") {
        } else return false;

        return true;
    }

    private void loadGroupList(JSONObject jsonObject) {
        ListAdapter groupsAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                JsonParser.parseGroupList(jsonObject));
        ListView groupsView = getView().findViewById(R.id.list);

        groupsView.setAdapter(groupsAdapter);
        registerForContextMenu(groupsView);
        groupsView.setOnItemClickListener(
                (parent, view, position, id) -> {
                    String group = String.valueOf(parent.getItemAtPosition(position));
                    openGroupFragment(group);
                }
        );
    }

    private void openAddGroupActivity() {
//        Intent intent = new Intent(getActivity(), AddGroupActivity.class);
//        startActivity(intent);
    }

    private void openGroupFragment(String group) {
        MainActivity activity = (MainActivity) getActivity();
        activity.openGroupFragment(group);
    }

    @Override
    public String toString() {
        return "Groups";
    }
}
