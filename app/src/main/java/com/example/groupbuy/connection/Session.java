package com.example.groupbuy.connection;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class Session {
    private static Session instance;
    private SharedPreferences pref;

    private Session(Context context) {
        // TODO Auto-generated constructor stub
        pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
    }

    public static Session getInstance(final Context context) {
        if (instance == null) {
            instance = new Session(context);
        }
        return instance;
    }

    private void unsetUsername() {
        pref.edit().remove("username").apply();
    }

    public String getUsername() {
        return pref.getString("username", "");
    }

    private void setUsername(String username) {
        pref.edit().putString("username", username).apply();
    }

    private void unsetID() {
        pref.edit().remove("ID").apply();
    }

    public String getID() {
        return pref.getString("ID", "");
    }

    private void setID(String ID) {
        pref.edit().putString("ID", ID).apply();
    }

    public void saveSession(Map data, String ID) {
        this.setUsername(Objects.requireNonNull(data.get("username")).toString());
        this.setID(ID);
    }

    public Map getData() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("username", getUsername());
        data.put("id", getID());
        return data;
    }

    public void clearSession() {
        unsetUsername();
        unsetID();
    }
}
