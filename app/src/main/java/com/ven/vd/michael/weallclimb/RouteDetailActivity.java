package com.ven.vd.michael.weallclimb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.ven.vd.michael.weallclimb.content.RouteContent;

/**
 * An activity representing a single Route detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RouteListActivity}.
 */
public class RouteDetailActivity extends AppCompatActivity {

    RouteContent.Route currentRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        Log.v("WAC", "Item id");
        Log.v("WAC", RouteDetailFragment.ARG_ITEM_ID);
        Log.v("WAC", getIntent().getStringExtra(RouteDetailFragment.ARG_ITEM_ID));

        currentRoute = RouteContent.ROUTE_MAP.get(getIntent().getStringExtra(RouteDetailFragment.ARG_ITEM_ID));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if(currentRoute.location != null){
            Log.v("WAC", currentRoute.location.latitude.toString());
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?daddr="+currentRoute.location.longitude.toString()+","+currentRoute.location.latitude.toString()));
                    startActivity(intent);
                }
            });
        }else{
            fab.hide();
        }

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(RouteDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(RouteDetailFragment.ARG_ITEM_ID));
            RouteDetailFragment fragment = new RouteDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.route_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, RouteListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
