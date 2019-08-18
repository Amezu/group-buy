package com.example.groupbuy.group;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.groupbuy.R;

public class ProductListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] productName;
    private final String[] subtitle;
    private final Boolean[] bought;
    private final Boolean[] clickable;

    public ProductListAdapter(Activity context, String[] productName, String[] subtitle, Boolean[] bought, Boolean[] clickable) {
        super(context, R.layout.product_row, productName);
        // TODO Auto-generated constructor stub  

        this.context = context;
        this.productName = productName;
        this.subtitle = subtitle;
        this.bought = bought;
        this.clickable = clickable;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.product_row, parent, false);

        TextView titleText = rowView.findViewById(R.id.title);
        TextView subtitleText = rowView.findViewById(R.id.subtitle);
        CheckBox checkBox = rowView.findViewById(R.id.checkbox);

        titleText.setText(productName[position]);
        subtitleText.setText(subtitle[position]);
        checkBox.setChecked(bought[position]);
        checkBox.setEnabled(clickable[position]);

        return rowView;
    }
}
