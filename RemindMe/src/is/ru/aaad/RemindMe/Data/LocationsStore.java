package is.ru.aaad.RemindMe.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import is.ru.aaad.RemindMe.Models.Location;

import java.util.*;

/**
 * Created by Johannes Gunnar Heidarsson on 6.11.2014.
 */
public class LocationsStore {
    private static LocationsStore locationsStore = null;
    private HashMap<String, Location> locationHashMap;
    private List<Location> locations;
    private List<String> names;
    private Context context;
    private SharedPreferences sharedPreferences;

    protected LocationsStore(){}

    public static LocationsStore getInstance(){
        if(locationsStore == null){
            locationsStore = new LocationsStore();
        }
        return locationsStore;
    }

    public void setContext(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("LocationsStore", Context.MODE_PRIVATE);
        readLocationsFromSharedPrefs();
    }


    public void updateNames(){
        names.clear();
        for(Location l: locations){
            names.add(l.getName());
        }
    }

    public List<Location> getAll(){
        return locations;
    }

    public List<String> getNames(){
        return names;
    }

    public void add(Location location){
        Log.d("LocationsStore", "Adding location: " + location.toString());

        saveLocationToSharedPrefs(location);

        locationHashMap.put(location.getUUID(), location);
        locations.add(location);
        names.add(location.getName());
    }

    public void updateLocation(Location location){
        Log.d("LocationsStore", "Updating location: " + location.toString());
        int position = getPosition(location);
        if(position < 0){
            locations.set(position, location);
            locationHashMap.put(location.getUUID(), location);
            updateNames();
        } else {
            add(location);
        }
    }

    public void removeLocation(Location location){
        Set<String> locationUUIDs = sharedPreferences.getStringSet("locations_uuids", new HashSet<String>());
        locationUUIDs.remove(location.getUUID());

        sharedPreferences.edit()
                .putStringSet("locations_uuids", locationUUIDs)
                .remove(location.getUUID() + "_latitude")
                .remove(location.getUUID() + "_longitude")
                .remove(location.getUUID() + "_radius")
                .remove(location.getUUID() + "_name")
                .apply();

        readLocationsFromSharedPrefs();
    }

    private void saveLocationToSharedPrefs(Location l){

        Set<String> locationUUIDs = sharedPreferences.getStringSet("locations_uuids", new HashSet<String>());

        locationUUIDs.add(l.getUUID());

        sharedPreferences.edit()
                .putStringSet("locations_uuids", locationUUIDs)
                .putLong(l.getUUID() + "_latitude", Double.doubleToRawLongBits(l.getLatitude()))
                .putLong(l.getUUID() + "_longitude", Double.doubleToLongBits(l.getLongitude()))
                .putLong(l.getUUID() + "_radius", Double.doubleToRawLongBits(l.getRadius()))
                .putString(l.getUUID() + "_name", l.getName())
                .apply();

    }

    private void readLocationsFromSharedPrefs(){
        if(locations == null){
            locations = new ArrayList<Location>();
        } else {
            locations.clear();
        }

        if(names == null){
            names = new ArrayList<String>();
        } else {
            names.clear();
        }

        if(locationHashMap == null){
            locationHashMap = new HashMap<String, Location>();
        } else {
            locationHashMap.clear();
        }

        Set<String> locationUUIDs = sharedPreferences.getStringSet("locations_uuids", new HashSet<String>());

        for(String uuid: locationUUIDs){
            Log.d("LocationsStore", "Reading UUID: " + uuid);
            String name = sharedPreferences.getString(uuid + "_name", null);
            if(name != null) {
                Location location = new Location(
                        uuid,
                        name,
                        Double.longBitsToDouble(sharedPreferences.getLong(uuid + "_latitude", 0)),
                        Double.longBitsToDouble(sharedPreferences.getLong(uuid + "_longitude", 0)),
                        Double.longBitsToDouble(sharedPreferences.getLong(uuid + "_radius", 0))
                );
                locationHashMap.put(uuid, location);
                locations.add(location);
                names.add(name);
            } else {
                Log.d("LocationsStore", "Could not find Location with UUDID: " + uuid);
            }
        }
    }

    public int getPosition(Location location){
        return locations.indexOf(location);
    }

    public Location getLocationByUUID(String uuid){
        return locationHashMap.get(uuid);
    }

    public Location getLocationByPosition(int position){
        return locations.get(position);
    }


}
