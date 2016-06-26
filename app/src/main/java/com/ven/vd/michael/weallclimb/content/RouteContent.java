package com.ven.vd.michael.weallclimb.content;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ven.vd.michael.weallclimb.RouteListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Michael on 21-6-2016.
 */
public class RouteContent implements ApiResult {
    public static List<Route> ROUTES = new ArrayList<>();

    public static Map<String, Route> ROUTE_MAP = new HashMap<>();

    public static int COUNT = 0;

    public static RouteListActivity currentActivity;

    public RouteContent(RouteListActivity activity){
        currentActivity = activity;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(currentActivity);
        Log.v("WAC", "sharedPref");
        Boolean ownRoutes = sharedPref.getBoolean("show_own_routes", true);
        String apiKey = sharedPref.getString("api_key", "");
        Log.v("WAC", ownRoutes.toString());
        Log.v("WAC", apiKey);

        String url = "routes";

        if(apiKey != null && !apiKey.isEmpty()){
            if(ownRoutes){
                url = "users/"+apiKey+"/routes";
            }
        }

        ApiCall.getInstance().get(url, this);
    }

    public void apiResult(JSONArray output) {
        ROUTES.clear();
        ROUTE_MAP.clear();
        int arrayLength = output.length();
        for (int i = 0; i<arrayLength; i++) {
            try {
                Log.v("WAC", output.getJSONObject(i).toString());
                addItem(createRouteItem(output.getJSONObject(i)));
            }catch (JSONException e) {
                Log.v("WAC", "Error: " + e.toString());
                Log.e("JSONException", "Error: " + e.toString());
            }
        }
        COUNT = arrayLength;
        currentActivity.routesLoaded();
    }

    public Route createRouteItem(JSONObject jsonObject){
        String name = null;
        String grade = null;
        String climber = null;
        String id = null;
        String rope = null;
        Boolean leadClimbed = null;
        Boolean outdoor = null;
        String color = null;
        Location location = null;

        try {
            name = jsonObject.getString("Name");
            grade = jsonObject.getJSONObject("Grade").getString("France");
            climber = jsonObject.getString("Climber");
            id = jsonObject.getString("_id");
            rope = jsonObject.getString("Rope");
            leadClimbed = jsonObject.getBoolean("LeadClimbed");
            outdoor = jsonObject.getBoolean("Outdoor");
            color = jsonObject.getString("Color");
            if(jsonObject.has("Location"))
                location = createLocationItem(jsonObject.getJSONObject("Location"));
        } catch (JSONException e) {
            Log.v("WAC", "Error: " + e.toString());
            Log.e("JSONException", "Error: " + e.toString());
        }

        return new Route(name, grade, climber, id, rope, location, leadClimbed, outdoor, color);
    }

    public Location createLocationItem(JSONObject jsonObject) {
        Double latitude = null;
        Double longitude = null;
        Double accuracy = null;

        try {
            latitude = jsonObject.getDouble("Latitude");
            longitude = jsonObject.getDouble("Longitude");
            accuracy = jsonObject.getDouble("Accuracy");
        } catch (JSONException e) {
            Log.v("WAC", "Error: " + e.toString());
            Log.e("JSONException", "Error: " + e.toString());
        }

        return new Location(latitude, longitude, accuracy);
    }

    public void addItem(Route item){
        ROUTES.add(item);
        ROUTE_MAP.put(item.id, item);
    }

    public static class Route{
        public final String name;
        public final String grade;
        public final String climber;
        public final String id;
        public final String rope;
        public final Location location;
        public final Boolean leadClimbed;
        public final Boolean outdoor;
        public final String color;

        public Route(String name, String grade, String climber, String id, String rope, Location location, Boolean leadClimbed, Boolean outdoor, String color) {
            this.name = name;
            this.grade = grade;
            this.climber = climber;
            this.id = id;
            this.rope = rope;
            this.location = location;
            this.leadClimbed = leadClimbed;
            this.outdoor = outdoor;
            this.color = color;
        }
    }

    public static class Location{
        public final Double latitude;
        public final Double longitude;
        public final Double accuracy;

        public Location(Double latitude, Double longitude, Double accuracy) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.accuracy = accuracy;
        }
    }
}
