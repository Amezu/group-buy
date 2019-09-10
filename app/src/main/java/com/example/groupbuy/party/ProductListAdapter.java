package com.example.groupbuy.party;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.graphics.drawable.DrawableCompat;

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
        CheckBox checkbox = rowView.findViewById(R.id.checkbox);
        ImageView thumbUp = rowView.findViewById(R.id.thumbUp);
        TextView thumbsUpCount = rowView.findViewById(R.id.thumbsUpCount);

        Product product = products[position];
        String title = String.format("%s (%.2f\u200E$)", product.getName(), product.getPrice());
        Drawable like = thumbUp.getDrawable();
        like.mutate();

        titleText.setText(title);
        subtitleText.setText(product.getUser());
        checkbox.setChecked(product.isBought());
        checkbox.setEnabled(product.isMine());
        DrawableCompat.setTint(DrawableCompat.wrap(like), product.isLiked() ? context.getColor(R.color.colorAccent) : Color.GRAY);
        thumbsUpCount.setText(String.valueOf(product.getThumbsUpCount()));

        return rowView;
    }
}
