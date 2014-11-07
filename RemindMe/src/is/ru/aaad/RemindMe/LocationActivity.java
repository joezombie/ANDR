package is.ru.aaad.RemindMe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * Created by Johannes Gunnar Heidarsson on 6.11.2014.
 */
public class LocationActivity extends FragmentActivity {
    LocationsStore locationsStore = LocationsStore.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);

        LocationFragment locationFragment = (LocationFragment) getSupportFragmentManager().findFragmentById(R.id.location_fragment);
        int position = getIntent().getIntExtra("location_position", -1);
        Log.d("Location","Position: " + position);
        locationFragment.changeLocation(locationsStore.getLocationByPosition(position));


    }
}