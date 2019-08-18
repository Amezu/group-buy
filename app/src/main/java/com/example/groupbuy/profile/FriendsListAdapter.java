package com.example.groupbuy.profile;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupbuy.R;

public class FriendsListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] username;
    private final Double[] debt;

    public FriendsListAdapter(Activity context, String[] username, Double[] debt) {
        super(context, R.layout.friend_row, username);

        this.context = context;
        this.username = username;
        this.debt = debt;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.friend_row, parent, false);

        TextView usernameText = rowView.findViewById(R.id.friend_username);
        TextView debtText = rowView.findViewById(R.id.friend_debt);
        ImageView editDebtIcon = rowView.findViewById(R.id.friend_editDebt);

        usernameText.setText(username[position]);
        debtText.setText(debt[position] + "zł");

        editDebtIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditDebtWindow(position);
            }
        });

        return rowView;
    }

    public void openEditDebtWindow(int position) {
        Toast.makeText(context, position, Toast.LENGTH_SHORT).show();
    }
}
