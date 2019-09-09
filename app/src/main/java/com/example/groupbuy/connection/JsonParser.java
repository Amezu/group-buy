package com.example.groupbuy.connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    public static List<String> parsePartyList(JSONObject s) {
        List<String> parties = new ArrayList<>();
        try {
            JSONArray array = s.getJSONArray("groupList");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                parties.add(object.getString("groupName"));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return parties;
    }

    public static List<String> parseGroupList(JSONObject s) {
        List<String> parties = new ArrayList<>();
        try {
            JSONArray array = s.getJSONArray("list");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                parties.add(object.getString("name"));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return parties;
    }
}
