package shirin.tahmasebi.mscfinalproject.organization;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import shirin.tahmasebi.mscfinalproject.MainFragmentActivity;
import shirin.tahmasebi.mscfinalproject.R;

public class MapsActivity extends MainFragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_maps;
    }

    @Override
    protected int getActivityHelpHint() {
        return R.string.helpHint_activity_map;
    }

    @Override
    protected int getActivityTitleResourceId() {
        return R.string.title_activity_map;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        double lng = getIntent().getDoubleExtra("Lng", 0.0);
        double lat = getIntent().getDoubleExtra("Lat", 0.0);
        String title = getIntent().getStringExtra("Title");
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(lng, lat);
        googleMap.addMarker(new MarkerOptions().position(sydney).title(title));
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}