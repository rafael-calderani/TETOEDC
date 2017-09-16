package br.com.calderani.rafael.tetoedc;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import br.com.calderani.rafael.tetoedc.api.GPSLocationTracker;
import br.com.calderani.rafael.tetoedc.model.Community;

public class CommunityMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private Community community;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.setHasOptionsMenu(true);
        mapFragment.setMenuVisibility(true);

        mapFragment.getMapAsync(this);
        Intent i = getIntent();
        if (i != null) {
            community = i.getParcelableExtra("COMMUNITY");
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        /*
        // Marker for Device Current Location
        GPSLocationTracker gps = new GPSLocationTracker(this);
        LatLng deviceLocation = new LatLng(gps.getLatitude(), gps.getLongitude());

        map.addMarker(new MarkerOptions()
                .position(deviceLocation)
                .title("You are here."));
        */

        // Marker for Community
        LatLng communityLocation = new LatLng(
                Double.parseDouble(community.getLatitude()),
                Double.parseDouble(community.getLongitude()));

        map.addMarker(new MarkerOptions()
                .position(communityLocation)
                .title(community.getName())
                .snippet(community.getZone()));

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(communityLocation, 17));
    }
}
