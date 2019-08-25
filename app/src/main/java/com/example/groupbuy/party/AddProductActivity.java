package com.example.groupbuy.party;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupbuy.R;

public class AddProductActivity extends AppCompatActivity {
    String group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        Intent intent = getIntent();
        group = intent.getStringExtra("groupName");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(group);
        actionBar.show();
    }

    public void addProduct(android.view.View view) {
        TextView nameView = (TextView) findViewById(R.id.productNameEdit);
        String name = nameView.getText().toString();
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

//        TODO: send adding request

        finish();
    }
}
