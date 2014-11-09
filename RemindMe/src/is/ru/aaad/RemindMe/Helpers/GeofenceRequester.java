package is.ru.aaad.RemindMe.Helpers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationClient.*;
import is.ru.aaad.RemindMe.Data.LocationsStore;
import is.ru.aaad.RemindMe.GeofenceService;
import is.ru.aaad.RemindMe.Models.Location;
import is.ru.aaad.RemindMe.Models.Reminder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Johannes Gunnar Heidarsson on 9.11.2014.
 */
public class GeofenceRequester
        implements
        ConnectionCallbacks,
        OnConnectionFailedListener,
        OnAddGeofencesResultListener{

    Context context;
    LocationClient locationClient;
    List<Geofence> geofences;
    LocationsStore locationsStore;

    public GeofenceRequester(Context context){
        this.context = context;
        locationClient = new LocationClient(context, this, this);
        geofences = new ArrayList<Geofence>();
        locationsStore = LocationsStore.getInstance();
    }

    public void request(Reminder reminder){
        Location location = locationsStore.getLocationByUUID(reminder.getLocationUUID());
        Geofence.Builder builder = new Geofence.Builder();

        Geofence geofence = builder
                .setCircularRegion(location.getLatitude(), location.getLongitude(), location.getRadius().floatValue())
                .setRequestId(reminder.getUUID())
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .build();

        geofences.add(geofence);

        locationClient.connect();
    }


    @Override
    public void onConnected(Bundle bundle) {
        if(geofences.size() > 0) {
            Intent intent = new Intent(context, GeofenceService.class);
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            locationClient.addGeofences(geofences, pendingIntent, this);
        }
    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onAddGeofencesResult(int i, String[] strings) {

    }
}
