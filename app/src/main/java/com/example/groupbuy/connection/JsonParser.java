package com.example.groupbuy.connection;

import com.example.groupbuy.party.Party;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
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
}
