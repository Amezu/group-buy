package com.example.groupbuy.connection;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.groupbuy.MainActivity;
import com.example.groupbuy.StartActivity;
import com.example.groupbuy.party.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.*;

public class HttpRequest {

    private Context context;
    private String TAG;

    public HttpRequest(Context activity) {
        context = activity;
        TAG = context.getClass().getName();
    }

    private void sendRequest(final Map data, final String sessionId, final String path, int method, final Callback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://ec2-3-121-223-191.eu-central-1.compute.amazonaws.com:8080/";
        JsonObjectRequest JSONObj = new JsonObjectRequest(method, url + path, new JSONObject(data),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i(TAG, path + " Response: " + response.toString());
                            callback.success(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, path + " Error: " + error.toString());
                        callback.error();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json;charset=UTF-8");
                headers.put("Authorization", sessionId);
                return headers;
            }
        };

        Log.i(TAG, path + " Body: " + new String(JSONObj.getBody()));
        JSONObj.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(JSONObj);
    }


    public void register(final Map body) {
        sendRequest(body, "", "public/users/register", Request.Method.POST, new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {
                response.toString();
                if (response.get("status").equals(true)) {
                    Session session = Session.getInstance(context);
                    session.saveSession(body, response.getString("id"), response.getString("userId"));
                    Intent intent = new Intent();
                    intent.setClass(context, MainActivity.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    public void login(final Map body) {
        sendRequest(body, "", "public/users/login", Request.Method.POST, new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {
                if (response.get("status").equals(true)) {
                    Session session = Session.getInstance(context);
                    session.saveSession(body, response.getString("id"), response.getString("userId"));
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            }

            @Override
            public void error() {
                makeText(context, "Connection error", LENGTH_SHORT).show();
            }
        });
    }

    public void openMainActivityIfActiveSessionExists() {
        Session session = Session.getInstance(context);
        Map data = session.getData();
        sendRequest(data, session.getID(), "users/checkSession", Request.Method.GET, new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {
                if (response.get("status").equals(true)) {
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    public void logOut() {
        Session session = Session.getInstance(context);
        Map data = session.getData();
        sendRequest(data, session.getID(), "users/logout", Request.Method.POST, new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {
                Intent intent = new Intent(context, StartActivity.class);
                context.startActivity(intent);
                session.clearSession();
            }

            @Override
            public void error() {
                makeText(context, "Logout failed", LENGTH_SHORT).show();
                Intent intent = new Intent(context, StartActivity.class);
                context.startActivity(intent);
                session.clearSession();
            }
        });
    }

    public void loadGroupList(Callback callback) {
        Session session = Session.getInstance(context);
        sendRequest(new HashMap(), session.getID(), "permanentGroups/", Request.Method.GET, callback);
    }



    public void addGroup(Map body, Callback callback) {
        Session session = Session.getInstance(context);
        sendRequest(body, session.getID(), "permanentGroups/", Request.Method.POST, callback);
    }

    public void renameGroup(String group_id) {
        Session session = Session.getInstance(context);
        sendRequest(new HashMap(), session.getID(), "users/groups/rename/" + group_id, Request.Method.DELETE, new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {
                if (response.get("status").equals(true)) {

                }
            }

            @Override
            public void error() {
                makeText(context, "Operation failed", LENGTH_SHORT).show();
            }
        });
    }


    public void deleteParty(String group_id, Callback callback) {
        Session session = Session.getInstance(context);
        sendRequest(new HashMap(), session.getID(), "groups/" + group_id, Request.Method.DELETE, callback);
    }
    public void loadPartyList(Callback callback) {
        Session session = Session.getInstance(context);
        sendRequest(new HashMap(), session.getID(), "users/" + session.getUserID() + "/groups", Request.Method.GET, callback);
    }

    public void addParty(Map body) {
        Session session = Session.getInstance(context);
        sendRequest(body, session.getID(), "groups/", Request.Method.POST, new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {
                if (response.get("status").equals(true)) {
                    final Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            }

            @Override
            public void error() {
                makeText(context, "Operation failed", LENGTH_SHORT).show();
            }
        });
    }

    public void renameParty(String group_id, Map body) {
        Session session = Session.getInstance(context);
        sendRequest(body, session.getID(), "/groups/" + group_id, Request.Method.PUT, new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {
                if (response.get("status").equals(true)) {

                }
            }

            @Override
            public void error() {
                makeText(context, "Operation failed", LENGTH_SHORT).show();
            }
        });
    }
    public void loadProductList(String groupId, Callback callback) {
        Session session = Session.getInstance(context);
        sendRequest(new HashMap(), session.getID(), "groups/" + groupId + "/product", Request.Method.GET, callback);
    }

    public void loadProduct(String productId, Callback callback) {
        Session session = Session.getInstance(context);
        sendRequest(new HashMap(), session.getID(), "products/" + productId, Request.Method.GET, callback);
    }

    public void voteForProduct(String id, Callback callback) {
        Session session = Session.getInstance(context);
        sendRequest(new HashMap(), session.getID(), "vote/" + id + "/product", Request.Method.POST, callback);
    }

    public void unvoteForProduct(String id, Callback callback) {
        Session session = Session.getInstance(context);
        sendRequest(new HashMap(), session.getID(), "vote/" + id + "/product", Request.Method.DELETE, callback);
    }

    public void getVotesForProduct(String id, Callback callback) {
        Session session = Session.getInstance(context);
        sendRequest(new HashMap(), session.getID(), "vote/" + id + "/product", Request.Method.GET, callback);
    }

    public void changeProductBoughtStatus(Product product, Callback callback) {
        Session session = Session.getInstance(context);
        Map body = new HashMap();
        body.put("bought", !product.isBought());
        sendRequest(body, session.getID(), "products/" + product.getId(), Request.Method.PUT, callback);
    }

    public void loadPeopleList(String groupId, Callback callback) {
        Session session = Session.getInstance(context);
        sendRequest(new HashMap(), session.getID(), "groups/" + groupId + "/user", Request.Method.GET, callback);
    }

    public void loadFriendList(Callback callback) {
        Session session = Session.getInstance(context);
        sendRequest(new HashMap(), session.getID(), "friends/" + session.getUserID() + "/user", Request.Method.GET, callback);
    }

    public void getUserId(String username, Callback callback){
        Session session = Session.getInstance(context);
        sendRequest(new HashMap(), session.getID(), "users/" + username, Request.Method.GET, callback);
    }

    public void addFriend(String id, Callback callback) {
        Session session = Session.getInstance(context);
        sendRequest(new HashMap(), session.getID(), "friends/" + id + "/user", Request.Method.POST, callback);
    }

    public void addPersonToParty(String userId, String id, Callback callback) {
        Session session = Session.getInstance(context);
        sendRequest(new HashMap(), session.getID(), "groups/" + id + "/user/" + userId, Request.Method.POST, callback);
    }

    public void leaveParty(String id, Callback callback) {
        Session session = Session.getInstance(context);
        sendRequest(new HashMap(), session.getID(), "groups/" + id + "/user/" + session.getUserID(), Request.Method.DELETE, callback);
    }

    public void addProduct(Map body, String id, Callback callback) {
        Session session = Session.getInstance(context);
        sendRequest(body, session.getID(), "groups/" + id + "/product", Request.Method.POST, callback);
    }

    public void deleteProduct(String groupId, String productId, Callback callback) {
        Session session = Session.getInstance(context);
        sendRequest(new HashMap(), session.getID(), "groups/" + groupId + "/product/" + productId, Request.Method.DELETE, callback);
    }


    public void editProduct(Map body, String productId, Callback callback) {
        Session session = Session.getInstance(context);
        sendRequest(body, session.getID(), "products/" + productId, Request.Method.PUT, callback);
    }

    public void loadPeopleGroup(String groupId, Callback callback) {
        Session session = Session.getInstance(context);
        sendRequest(new HashMap(), session.getID(), "permanentGroups/" + groupId, Request.Method.GET, callback);
    }

    public void addPersonToGroup(String groupId, String userId, Callback callback) {
        Session session = Session.getInstance(context);
        sendRequest(new HashMap(), session.getID(), "permanentGroups/" + groupId + "/user/" + userId, Request.Method.POST, callback);
    }

    public void deleteGroup(String group_id, Callback callback) {
        Session session = Session.getInstance(context);
        sendRequest(new HashMap(), session.getID(), "permanentGroups/" + group_id, Request.Method.DELETE, callback);
    }

    public void loadPeopleList(Callback callback) {
        Session session = Session.getInstance(context);
        sendRequest(new HashMap(), session.getID(), "users/", Request.Method.GET, callback);
    }
}