package com.example.groupbuy.party;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.groupbuy.R;
import com.example.groupbuy.connection.Callback;
import com.example.groupbuy.connection.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProductActivity extends AppCompatActivity {

    Party party;
    Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        Intent intent = getIntent();
        party = (Party) intent.getSerializableExtra("party");
        product = (Product) intent.getSerializableExtra("product");

        TextView nameView = findViewById(R.id.nameEdit);
        TextView priceView = findViewById(R.id.priceEdit);

        nameView.setText(product.toString());
        priceView.setText(String.valueOf(product.getPrice()));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(party.toString());
        actionBar.setSubtitle("Edit product");
        actionBar.show();
    }

    public void editProduct(View view) {
        TextView nameView = findViewById(R.id.nameEdit);
        TextView priceView = findViewById(R.id.priceEdit);
        String name = nameView.getText().toString();
        String price = priceView.getText().toString();
        Map body = new HashMap();
        body.put("name", name);
        body.put("price", price);

        Toast.makeText(this, name + " " + price, Toast.LENGTH_SHORT).show();

        new HttpRequest(getApplication()).editProduct(body, product.getId(), new Callback(){
            @Override
            public void success(JSONObject response) throws JSONException {
                finish();
            }
        });
    }
}
