package com.example.groupbuy.party;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.groupbuy.R;

public class AddProductActivity extends AppCompatActivity {

    String party;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        Intent intent = getIntent();
        party = intent.getStringExtra("partyName");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(party);
        actionBar.setSubtitle("Add product");
        actionBar.show();
    }

    public void addProduct(View view) {
        TextView nameView = findViewById(R.id.nameEdit);
        TextView priceView = findViewById(R.id.priceEdit);

        String name = nameView.getText().toString();
        String priceString = priceView.getText().toString();
        Double price = priceString.isEmpty() ? null : Double.valueOf(priceString);

        Toast.makeText(this, name + " " + price, Toast.LENGTH_SHORT).show();

//        TODO: send adding request

        finish();
    }
}
