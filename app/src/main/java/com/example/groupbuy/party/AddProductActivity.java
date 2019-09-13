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
import com.example.groupbuy.connection.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {

    Party party;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        Intent intent = getIntent();
        party = (Party) intent.getSerializableExtra("party");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(party.toString());
        actionBar.setSubtitle("Add product");
        actionBar.show();
    }

    public void addProduct(View view) {
        TextView nameView = findViewById(R.id.nameEdit);
        TextView priceView = findViewById(R.id.priceEdit);
        String name = nameView.getText().toString();
        String priceString = priceView.getText().toString();
        Double price = priceString.isEmpty() ? null : Double.valueOf(priceString);
        Session session = Session.getInstance(this);
        Map body = new HashMap();
        body.put("name", name);
        body.put("price", priceString);
        body.put("bought", "false");
        body.put("buyer", session.getUserID());

        Toast.makeText(this, name + " " + price, Toast.LENGTH_SHORT).show();
        new HttpRequest(getApplication()).addProduct(body, party.id, new Callback(){
            @Override
            public void success(JSONObject response) throws JSONException {
                finish();
            }
        });


    }
}
