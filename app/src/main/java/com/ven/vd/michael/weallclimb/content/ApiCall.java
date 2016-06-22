package com.ven.vd.michael.weallclimb.content;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Michael on 22-6-2016.
 */
public class ApiCall {
    private static ApiCall instance = null;
    private Context context;

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

    public void get(String route){
        String url = new StringBuilder()
                .append(Settings.API_URL)
                .append(route)
                .toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.v("WAC", "top");
                    Log.v("WAC", "Response is: "+ response.substring(0,500));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("WAC", "error");
                }
            }
        );

        Volley.newRequestQueue(context).add(stringRequest);
    }
}
