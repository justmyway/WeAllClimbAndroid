package com.ven.vd.michael.weallclimb.content;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Michael on 22-6-2016.
 */
public class ApiCall {
    private static ApiCall instance = null;
    private Context context;
    private ApiResult apiDelegate;

    protected ApiCall(){
    }

    public static ApiCall getInstance(){
        if (instance == null){
            instance = new ApiCall();
        }
        return instance;
    }

    public void setContext(Context newContext){
        context = newContext;
    }

    public void get(String route, final ApiResult apiResult){
        apiDelegate = apiResult;

        String url = new StringBuilder()
                .append(Settings.API_URL)
                .append(route)
                .toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.v("WAC", "API callback");
                    Log.v("WAC", "Response is: "+ response);
                    try {
                        JSONArray json = new JSONArray(response);
                        apiDelegate.apiResult(json);
                    }catch (JSONException e) {
                        Log.e("JSONException", "Error: " + e.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("WAC", "error");
                }
            }
        );

        Volley.newRequestQueue(context).add(stringRequest);
    }
}
