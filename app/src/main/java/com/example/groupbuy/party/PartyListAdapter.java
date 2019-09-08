package com.example.groupbuy.party;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.example.groupbuy.R;

public class PartyListAdapter  extends ArrayAdapter<Party> {
    private final Activity context;
    private final Party[] party;

    public PartyListAdapter(Activity context, Party[] party){
        super(context, android.R.layout.simple_list_item_1, party);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.party = party;
    }

}
