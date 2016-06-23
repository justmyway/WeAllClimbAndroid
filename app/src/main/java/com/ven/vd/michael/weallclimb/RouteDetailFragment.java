package com.ven.vd.michael.weallclimb;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ven.vd.michael.weallclimb.content.RouteContent;
import com.ven.vd.michael.weallclimb.dummy.DummyContent;

/**
 * A fragment representing a single Route detail screen.
 * This fragment is either contained in a {@link RouteListActivity}
 * in two-pane mode (on tablets) or a {@link RouteDetailActivity}
 * on handsets.
 */
public class RouteDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private RouteContent.Route mRoute;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RouteDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mRoute = RouteContent.ROUTE_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mRoute.name);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.route_detail, container, false);

        // Show the route content as text in a TextView.
        if (mRoute != null) {
            StringBuilder content = new StringBuilder();
            content.append("Naam: "+mRoute.name);
            newLine(content);newLine(content);
            content.append("Gradatie: "+mRoute.grade);
            newLine(content);newLine(content);
            if(mRoute.outdoor) {
                if(mRoute.leadClimbed) {
                    content.append("Buiten voorgeklommen.");
                }else{
                    content.append("Binnen getoproped.");
                }
            }else{
                content.append("Binnen geklommen route:");
                newLine(content);
                if(mRoute.leadClimbed != null){
                    if(mRoute.leadClimbed){
                        content.append("    Voorgeklommen: ja");
                    }else{
                        content.append("    Voorgeklommen: nee");
                    }
                    newLine(content);
                }
                if(mRoute.rope != null && !mRoute.rope.isEmpty()){
                    content.append("    Touw: "+mRoute.rope);
                    newLine(content);
                }
                if(mRoute.color != null && !mRoute.rope.isEmpty()){
                    content.append("    Kleur: "+mRoute.color);
                    newLine(content);
                }
                newLine(content);
            }
            newLine(content);newLine(content);
            ((TextView) rootView.findViewById(R.id.route_detail)).setText(content.toString());
//            ((TextView) rootView.findViewById(R.id.route_detail)).setText(mRoute.name);
        }

        return rootView;
    }

    public StringBuilder newLine(StringBuilder sb){
        return sb.append(System.getProperty("line.separator"));
    }
}
