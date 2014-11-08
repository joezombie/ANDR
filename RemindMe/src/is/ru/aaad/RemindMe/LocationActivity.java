package is.ru.aaad.RemindMe;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import is.ru.aaad.RemindMe.Helpers.LocationUtils;
import is.ru.aaad.RemindMe.Models.Location;

/**
 * Created by Johannes Gunnar Heidarsson on 6.11.2014.
 */
public class LocationActivity extends FragmentActivity implements
        GoogleMap.OnMapClickListener{
    private LocationsStore locationsStore = LocationsStore.getInstance();
    private SupportMapFragment mapFragment;
    private Location location;
    private Marker marker = null;
    private Circle circle = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);

        LocationFragment locationFragment = (LocationFragment) getSupportFragmentManager().findFragmentById(R.id.location_fragment);
        int position = getIntent().getIntExtra("location_position", -1);
        Log.d("Location","Position: " + position);

        location = locationsStore.getLocationByPosition(position);
        locationFragment.changeLocation(location);


        mapFragment = SupportMapFragment.newInstance();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.map_frame, mapFragment).commit();

        MapsInitializer.initialize(this);

    }

    @Override
    protected void onStart() {
        super.onStart();


        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                location.getLatLng(),
                18.0f);

        GoogleMap map = mapFragment.getMap();
        map.setOnMapClickListener(this);
        map.setMyLocationEnabled(true);
        moveMapMarker(location.getLatLng());
        map.animateCamera(cameraUpdate);

        //mapFragment.getMap().animateCamera(CameraUpdateFactory.zoomTo(14));
    }

    private void moveMapMarker(LatLng latLng){
        if(marker == null) {
            marker = mapFragment.getMap().addMarker(
                    new MarkerOptions()
                        .position(latLng)
                        .title(location.getName())
            );
        } else {
            marker.setPosition(latLng);
        }

        if(circle != null){
            circle.remove();
        }

        circle = mapFragment.getMap().addCircle(
                new CircleOptions()
                    .center(latLng)
                    .radius(location.getRadius())
                    .strokeWidth(6)
                    .strokeColor(Color.argb(128, 200, 0, 0))
                    .fillColor(Color.argb(64, 200, 0, 0))
        );
    }

    @Override
    public void onMapClick(LatLng latLng) {
        moveMapMarker(latLng);
        location.setLatLng(latLng);
    }

    public void saveLocation(View v){
        Log.d(LocationUtils.APPTAG, "saveLocation called");
    }

    public void cancelLocationEdit(View v){
        Log.d(LocationUtils.APPTAG, "CancelLocationEdit called");
    }
}