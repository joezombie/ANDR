package is.ru.aaad.RemindMe;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import is.ru.aaad.RemindMe.Data.RemindersStore;
import is.ru.aaad.RemindMe.Models.Reminder;

import java.util.List;


/**
 * Created by Johannes Gunnar Heidarsson on 9.11.2014.
 */
public class GeofenceService extends IntentService {
    private RemindersStore remindersStore;
    public GeofenceService(){
        super("GeofenceService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(remindersStore == null){
            remindersStore = RemindersStore.getInstance();
            remindersStore.setContext(this);
        }

        int transition = LocationClient.getGeofenceTransition(intent);

        switch (transition){
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                break;
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                break;
        }

        List<Geofence> geofences = LocationClient.getTriggeringGeofences(intent);

        for(Geofence g: geofences){
            Log.d("GeofenceService", "Geofence: " + g.toString());
            Reminder reminder = remindersStore.get(g.getRequestId());
            if(reminder != null){
                Log.d("GeofenceService", "Reminder: " + reminder.toString());
            }
        }

    }
}
