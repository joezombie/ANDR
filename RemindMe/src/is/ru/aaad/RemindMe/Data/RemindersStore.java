package is.ru.aaad.RemindMe.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import is.ru.aaad.RemindMe.Models.Reminder;

import java.util.*;

/**
 * Created by Johannes Gunnar Heidarsson on 9.11.2014.
 */
public class RemindersStore {
    private static RemindersStore remindersStore = null;
    private HashMap<String, Reminder> reminders;
    //private List<Reminder> reminders;
    //private List<String> names;
    private Context context;
    private SharedPreferences sharedPreferences;

    private RemindersStore(){

    }

    public static RemindersStore getInstance(){
        if(remindersStore == null){
            RemindersStore.remindersStore = new RemindersStore();
        }
        return RemindersStore.remindersStore;
    }

    public void setContext(Context context){
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("RemindersStore", Context.MODE_PRIVATE);
        readRemindersFromSharedPrefs();
    }

    private void readRemindersFromSharedPrefs(){
        if(reminders == null){
            reminders = new HashMap<String, Reminder>();
        } else {
            reminders.clear();
        }

        Set<String> remindersUUIDs = sharedPreferences.getStringSet("reminders_uuids", new HashSet<String>());

        for(String uuid : remindersUUIDs){
            Reminder r = getReminderFromSharedPrefs(uuid);
            if(r != null){
                reminders.put(uuid, r);
            }
        }
    }

    private void saveReminderToSharedPrefs(Reminder r){
        Set<String> reminderUUIDs = sharedPreferences.getStringSet("reminders_uuids", new HashSet<String>());

        reminderUUIDs.add(r.getUUID());

        sharedPreferences.edit()
                .putStringSet("reminders_uuids", reminderUUIDs)
                .putString(r.getUUID() + "_name", r.getName())
                .putString(r.getUUID() + "_uuid", r.getUUID())
                .putString(r.getUUID() + "_text", r.getText())
                .putString(r.getUUID() + "_location_uuid", r.getLocationUUID())
                .putString(r.getUUID() + "_mode", r.getMode().toString())
                .putBoolean(r.getUUID() + "_active", r.isActive())
                .putBoolean(r.getUUID() + "_repeat", r.isRepeat())
                .putString(r.getUUID() + "_geofence_id", r.getGeofenceId())
                .apply();
    }

    private Reminder getReminderFromSharedPrefs(String uuid){
        String name = sharedPreferences.getString(uuid + "_name", null);
        if(name != null){
            return new Reminder(
                    uuid,
                    name,
                    sharedPreferences.getString(uuid + "_text", null),
                    sharedPreferences.getString(uuid + "_location_uuid", null),
                    sharedPreferences.getBoolean(uuid + "_active", false),
                    sharedPreferences.getBoolean(uuid + "_repeat", false),
                    Reminder.Mode.valueOf(sharedPreferences.getString(uuid + "_mode", null)),
                    sharedPreferences.getString(uuid + "_geofence_id", null)
            );
        } else {
            Log.d("RemindersStore", "Could not find Reminder with UUID: " + uuid);
            return null;
        }
    }

    private void removeReminderFromSharedPrefs(String uuid){
        Set<String> reminderUUIDs = sharedPreferences.getStringSet("reminders_uuids", new HashSet<String>());
        reminderUUIDs.remove(uuid);

        sharedPreferences.edit()
                .putStringSet("reminders_uuids", reminderUUIDs)
                .remove(uuid + "_name")
                .remove(uuid + "_uuid")
                .remove(uuid + "_text")
                .remove(uuid + "_location_uuid")
                .remove(uuid + "_mode")
                .remove(uuid + "_active")
                .remove(uuid + "_repeat")
                .remove(uuid + "_geofence_id")
                .apply();
    }

    public void add(Reminder reminder){
        reminders.put(reminder.getUUID(), reminder);

        saveReminderToSharedPrefs(reminder);
    }


    public List<Reminder> getAll() {
        return new ArrayList<Reminder>(reminders.values());
    }

    public Reminder get(String uuid){
        Reminder result = reminders.get(uuid);
        if(result == null){
            result = getReminderFromSharedPrefs(uuid);
        }
        return result;
    }

    public void remove(Reminder reminder){
        removeReminderFromSharedPrefs(reminder.getUUID());
        reminders.remove(reminder.getUUID());
    }
}
