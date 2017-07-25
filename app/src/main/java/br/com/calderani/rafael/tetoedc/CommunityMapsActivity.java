package br.com.calderani.rafael.tetoedc;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

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
        mapFragment.getMapAsync(this);
        Intent i = getIntent();
        if (i != null) {
            community = i.getParcelableExtra("COMMUNITY");
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng location = new LatLng(
                Double.parseDouble(community.getLatitude()),
                Double.parseDouble(community.getLongitude()));
        map.addMarker(new MarkerOptions()
                .position(location)
                .title(community.getName())
                .snippet(community.getZone()));

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 17));
    }
}
