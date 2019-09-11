package com.example.groupbuy.connection;

import android.util.Log;

import com.example.groupbuy.party.Party;
import com.example.groupbuy.party.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    public static List<Party> parsePartyList(JSONObject s) {
        List<Party> parties = new ArrayList<>();
        try {
            JSONArray array = s.getJSONArray("groupList");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                parties.add(new Party(object.getString("groupName"), object.getString("username"), object.getString("groupId"), object.getString("userId")));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return parties;
    }

    public static List<Product> parseProductList(JSONObject jsonObject) {
        List<Product> products = new ArrayList<>();
        Log.i("xD", " Response: " + jsonObject.toString());
        try {
            JSONArray array = jsonObject.getJSONArray("products");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                products.add(new Product(object.getString("productName"), object.getString("userName"), object.getDouble("price"), object.getBoolean("bought"), object.getInt("numVotes") , object.getBoolean("hasCurrentUserVoted")));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}
