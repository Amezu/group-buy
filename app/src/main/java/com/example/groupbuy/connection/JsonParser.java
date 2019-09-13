package com.example.groupbuy.connection;

import android.util.Log;

import com.example.groupbuy.friends.User;
import com.example.groupbuy.groups.Group;
import com.example.groupbuy.party.Party;
import com.example.groupbuy.party.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
//String partyName, String owner, String id, String ownerId
public class JsonParser {
    public static List<Party> parsePartyList(JSONObject s) {
        List<Party> parties = new ArrayList<>();
        try {
            JSONArray array = s.getJSONArray("groupList");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                parties.add(new Party(object.getString("groupName"), object.getString("username"),object.getString("groupId"),object.getString("groupAdmin")));
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
                products.add(new Product(object.getString("productId"),
                        object.getString("productName"),
                        object.getString("userName"),
                        object.getDouble("price"),
                        object.getString("productImage"),
                        object.getString("description"),
                        object.getBoolean("bought"),
                        object.getInt("numVotes"),
                        object.getBoolean("hasCurrentUserVoted")));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
    public static List<User> parsePeopleList(JSONObject s) {
            List<User> users = new ArrayList<>();
        try {
            JSONArray array = s.getJSONArray("users");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                users.add(new User(object.getString("userId"), object.getString("username")));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
    public static List<User> parseAllPeopleList(JSONObject s) {
        List<User> users = new ArrayList<>();
        try {
            JSONArray array = s.getJSONArray("users");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                users.add(new User(object.getString("id"), object.getString("username")));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public static List<User> parseFriendList(JSONObject s) {
        List<User> users = new ArrayList<>();
        try {
            JSONArray array = s.getJSONArray("friends");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                users.add(new User(object.getString("friendId"), object.getString("friendUsername")));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return users;
    }


    public static List<Group> parseGroupList(JSONObject s) {
        List<Group> parties = new ArrayList<>();
        try {
            JSONArray array = s.getJSONArray("userPermanentGroups");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                parties.add(new Group(object.getString("name"), object.getString("id"), object.getString("owner")));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return parties;
    }

}
