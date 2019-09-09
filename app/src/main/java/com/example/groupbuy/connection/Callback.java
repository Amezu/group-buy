package com.example.groupbuy.connection;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class Callback {

    abstract public void success(JSONObject response) throws JSONException;

    public void error() {
    }
}
