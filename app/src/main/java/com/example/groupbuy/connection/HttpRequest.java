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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private Context context;
    private String TAG;

    private HttpRequest(Context activity) {
        context = activity;
        TAG = context.getClass().getName();
    }

    private void sendRequest(final Map data, final String sessionId, final String path, int method, final Callback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://ec2-3-120-128-193.eu-central-1.compute.amazonaws.com:8080/";
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
        ){
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
                    session.saveSession(body, response.getString("id"));
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
                    session.saveSession(body, response.getString("id"));
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            }

            @Override
            public void error() {
                Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show();
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
            }

            @Override
            public void error() {
                Toast.makeText(context, "Logout failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadGroupList(Callback callback) {
        Session session = Session.getInstance(context);
        sendRequest(new HashMap(), session.getID(), "users/groups", Request.Method.GET, callback);
    }

    public void addGroup(Map body) {
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
                //TODO wypisz błąd na ekran
            }
        });
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
                //TODO wypisz błąd na ekran
            }
        });
    }

    public void deleteGroup(String group_id) {
        Session session = Session.getInstance(context);
        sendRequest(new HashMap(), session.getID(), "users/groups/delete/" + group_id, Request.Method.DELETE, new Callback() {
            @Override
            public void success(JSONObject response) throws JSONException {
                if (response.get("status").equals(true)) {

                }
            }

            @Override
            public void error() {
                //TODO wypisz błąd na ekran
            }
        });
    }

}