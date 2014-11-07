package is.ru.aaad.RemindMe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import is.ru.aaad.RemindMe.Helpers.ErrorDialogFragment;
import is.ru.aaad.RemindMe.Helpers.LocationUtils;
import is.ru.aaad.RemindMe.Helpers.MainPagerAdapter;
import is.ru.aaad.RemindMe.Models.Location;

/**
 * Created by Johannes Gunnar Heidarsson on 15.10.2014.
 */
public class MainActivity extends FragmentActivity implements
        LocationListener,
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        LocationsFragment.OnLocationSelectedListener,
        ViewPager.OnPageChangeListener{

    private MainPagerAdapter mainPagerAdapter;
    private LocationsStore locationsStore = LocationsStore.getInstance();
    private boolean isLarge;

    private LocationRequest locationRequest;
    private LocationClient locationClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOnPageChangeListener(this);

        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(mainPagerAdapter);

        isLarge = (getSupportFragmentManager().findFragmentById(R.id.location_fragment) != null);

        locationRequest = LocationRequest.create();

        locationRequest.setInterval(LocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(LocationUtils.FAST_INTERVAL_CEILING_IN_MILLISECONDS);

        locationClient = new LocationClient(this, this, this);

    }

    @Override
    protected void onStop() {
        locationClient.disconnect();
        super.onStop();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationClient.connect();
    }

    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // Choose what to do based on the request code
        switch (requestCode) {

            // If the request code matches the code sent in onConnectionFailed
            case LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST :

                switch (resultCode) {
                    // If Google Play services resolved the problem
                    case Activity.RESULT_OK:
                        Log.d(LocationUtils.APPTAG, getString(R.string.resolved));
                        break;
                    // If any other result was returned by Google Play services
                    default:
                        // Log the result
                        Log.d(LocationUtils.APPTAG, getString(R.string.no_resolution));
                        break;
                }
            default:
                // Report that this Activity received an unknown requestCode
                Log.d(LocationUtils.APPTAG,
                        getString(R.string.unknown_activity_request_code, requestCode));

                break;
        }
    }

    private boolean servicesConnected(){
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        boolean result;
        if(ConnectionResult.SUCCESS == resultCode){
            Log.d(LocationUtils.APPTAG, "Google Play services is available");
            result = true;
        } else {
            result = false;
            showErrorDialog(resultCode);
        }
        return result;
    }

    public void getLocation(View v) {
        if (servicesConnected()) {

            // Get the current location
            android.location.Location currentLocation = locationClient.getLastLocation();
            // TODO Display the current location in the UI

        }
    }

    public void addNewLocation(View view){
        if(servicesConnected()) {
            android.location.Location currentLocation = locationClient.getLastLocation();
            if(currentLocation == null){
                // TODO
                Log.d(LocationUtils.APPTAG, "Could not get current location");
            } else {
                Location newLocation = new Location("Location Name", currentLocation.getLatitude(), currentLocation.getLongitude());
                locationsStore.add(newLocation);
                ArrayAdapter adapter = (ArrayAdapter) mainPagerAdapter.getLocationsFragment().getListAdapter();
                adapter.notifyDataSetChanged();
                OnLocationSelected(locationsStore.getPosition(newLocation));
            }
        }
    }

    @Override
    public void OnLocationSelected(int position) {
        LocationFragment locationFragment = (LocationFragment) getSupportFragmentManager().findFragmentById(R.id.location_fragment);
        //LocationFragment locationFragment = null;


        if(locationFragment == null){
            Log.d("LocationsFragment", "locationFragment does equal null");
            Intent intent = new Intent(this, LocationActivity.class);
            intent.putExtra("location_position", position);
            startActivity(intent);

        } else {
            Log.d("LocationsFragment", "locationFragment does not equal null");
            locationFragment.changeLocation(locationsStore.getLocationByPosition(position));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(isLarge) {
            switch (position) {
                case 0:
                    findViewById(R.id.location_fragment).setVisibility(View.GONE);
                    findViewById(R.id.reminder_fragment).setVisibility(View.VISIBLE);
                    break;
                case 1:
                    findViewById(R.id.reminder_fragment).setVisibility(View.GONE);
                    findViewById(R.id.location_fragment).setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    // Location services
    @Override
    public void onConnected(Bundle bundle) {
        Log.d(LocationUtils.APPTAG, "Location services connected");
    }

    @Override
    public void onDisconnected() {
        Log.d(LocationUtils.APPTAG, "Location services disconnected");
    }

    @Override
    public void onLocationChanged(android.location.Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if(connectionResult.hasResolution()){
            try{
               connectionResult.startResolutionForResult(this, LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e){
                e.printStackTrace();
            }
        } else {
            showErrorDialog(connectionResult.getErrorCode());
        }
    }

    private void showErrorDialog(int errorCode){
        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(errorCode, this, LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);
        if(errorDialog != null){
            ErrorDialogFragment errorFragment = new ErrorDialogFragment();
            errorFragment.setDialog(errorDialog);
            errorFragment.show(getSupportFragmentManager(), LocationUtils.APPTAG);
        }
    }
}

