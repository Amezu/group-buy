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

import androidx.core.graphics.drawable.DrawableCompat;

import com.example.groupbuy.R;
import com.example.groupbuy.connection.Callback;
import com.example.groupbuy.connection.HttpRequest;
import com.example.groupbuy.connection.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;
import java.util.List;

public class ProductListAdapter extends ArrayAdapter<Product> {
    private final Activity context;
    private final List<Product> products;
    private Comparator<Product> comparator;

    public ProductListAdapter(Activity context, List<Product> products) {
        super(context, R.layout.product_row, products);
        Session session = Session.getInstance(context);
        this.context = context;
        this.products = products;

        comparator = (p1, p2) -> Boolean.compare(p2.isMine(session.getUsername()), p1.isMine(session.getUsername()));
        comparator = comparator.thenComparing((p1, p2) -> Boolean.compare(p1.isBought(), p2.isBought()));
        comparator = comparator.thenComparing((p1, p2) -> Integer.compare(p2.getThumbsUpCount(), p1.getThumbsUpCount()));

        products.sort(comparator);
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.product_row, parent, false);
        Session session = Session.getInstance(context);
        TextView titleText = rowView.findViewById(R.id.title);
        TextView subtitleText = rowView.findViewById(R.id.subtitle);
        CheckBox checkbox = rowView.findViewById(R.id.checkbox);
        TextView thumbsUpCount = rowView.findViewById(R.id.thumbsUpCount);
        ImageView thumbUpImage = rowView.findViewById(R.id.thumbUp);

        Product product = products.get(position);
        String title = String.format("%s (%.2f\u200E$)", product.getName(), product.getPrice());

        titleText.setText(title);
        subtitleText.setText(product.getUser());
        checkbox.setChecked(product.isBought());
        checkbox.setEnabled(product.isMine(session.getUsername()));
        changeThumbUpColor(thumbUpImage, product);
        thumbsUpCount.setText(String.valueOf(product.getThumbsUpCount()));

        thumbUpImage.setOnClickListener(v -> {
            if (!product.isMine(session.getUsername())) {
                HttpRequest httpRequest = new HttpRequest(context);
                String id = product.getId();
                if (product.isLiked()) {
                    httpRequest.unvoteForProduct(id, new Callback() {
                        @Override
                        public void success(JSONObject response) throws JSONException {
                            checkForChange(product);
                        }
                    });
                } else {
                    httpRequest.voteForProduct(id, new Callback() {
                        @Override
                        public void success(JSONObject response) throws JSONException {
                            checkForChange(product);
                        }
                    });
                }
            }
        });
        checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> changeBought(product));

        return rowView;
    }

    private void changeBought(Product product) {
        new HttpRequest(context).changeProductBoughtStatus(product, new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {
                checkForChange(product);
            }
        });
    }

    private void checkForChange(Product product){
        new HttpRequest(context).loadProduct(product.getId(), new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {
                JSONObject obj = response.getJSONObject("product");
                product.changeStatus(obj.getBoolean("bought"));
                product.setThumbsUpCount(obj.getInt("numVotes"));
                product.setLiked(obj.getBoolean("hasCurrentUserVoted"));

                notifyDataSetChanged();
            }
        });
    }



    private void changeThumbUpColor(ImageView view, Product product) {
        final int disabledColor = Color.LTGRAY;
        final int notLikedColor = Color.GRAY;
        final int likedColor = context.getColor(R.color.colorAccent);
        Drawable drawable = view.getDrawable().mutate();
        int color = isThumbUpDisabled(product) ? disabledColor : product.isLiked() ? likedColor : notLikedColor;

        DrawableCompat.setTint(DrawableCompat.wrap(drawable), color);
    }

    private boolean isThumbUpDisabled(Product product) {
        Session session = Session.getInstance(context);
        return product.isBought() || product.isMine(session.getUsername());
    }

    @Override
    public void notifyDataSetChanged() {
        products.sort(comparator);
        super.notifyDataSetChanged();
    }
}
