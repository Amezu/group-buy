package com.example.groupbuy.party;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.groupbuy.R;

public class ProductListAdapter extends ArrayAdapter<Product> {
    private final Activity context;
    private final Product[] products;



    public ProductListAdapter(Activity context, Product[] products) {
        super(context, R.layout.product_row, products);

        this.context = context;
        this.products = products;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.product_row, parent, false);

        TextView titleText = rowView.findViewById(R.id.title);
        TextView subtitleText = rowView.findViewById(R.id.subtitle);
        CheckBox checkBox = rowView.findViewById(R.id.checkbox);

        Product product = products[position];
        String title = String.format("%s (%.2f\u200E$)", product.getName(), product.getPrice());

        titleText.setText(title);
        subtitleText.setText(product.getUser());
        checkBox.setChecked(product.isBought());
        checkBox.setEnabled(product.isMine());

        return rowView;
    }
}
