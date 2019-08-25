package com.example.groupbuy.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.groupbuy.R;
import com.example.groupbuy.connection.Callback;
import com.example.groupbuy.connection.HttpRequestDebug;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance(String username) {
        ProfileFragment fragment = new ProfileFragment();

        Bundle args = new Bundle();
        args.putString("username", username);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new HttpRequestDebug(getActivity()).loadFriendsList(new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {

            }
        });

        String[] username = {"Ola", "Kinga"};
        Double[] debt = {5.0, -13.5};

        ListAdapter friendsAdapter = new FriendsListAdapter(getActivity(), username, debt);
        ListView friendsView = getView().findViewById(R.id.friendsList);
        friendsView.setAdapter(friendsAdapter);
    }
}
