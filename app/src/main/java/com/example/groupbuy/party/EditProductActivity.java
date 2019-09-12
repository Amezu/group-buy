package com.example.groupbuy.party;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.groupbuy.R;

public class EditProductActivity extends AppCompatActivity {

    String party;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        Intent intent = getIntent();
        party = intent.getStringExtra("partyName");
        String name = intent.getStringExtra("productName");
        double price = intent.getDoubleExtra("productPrice", 0);

        TextView nameView = findViewById(R.id.nameEdit);
        TextView priceView = findViewById(R.id.priceEdit);

        nameView.setText(name);
        priceView.setText(String.valueOf(price));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(party);
        actionBar.setSubtitle("Edit product");
        actionBar.show();
    }

    public void editProduct(View view) {
        TextView nameView = findViewById(R.id.nameEdit);
        TextView priceView = findViewById(R.id.priceEdit);

        String name = nameView.getText().toString();
        Double price = Double.valueOf(priceView.getText().toString());

        Toast.makeText(this, name + " " + price, Toast.LENGTH_SHORT).show();

//        TODO: Send request

        finish();
    }
}
