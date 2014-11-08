package is.ru.aaad.RemindMe;

import is.ru.aaad.RemindMe.Models.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johannes Gunnar Heidarsson on 6.11.2014.
 */
public class LocationsStore {
    private static LocationsStore locationsStore = null;
    private List<Location> locations;
    private List<String> names;

    protected LocationsStore(){
        locations = new ArrayList<Location>();
        locations.add(new Location("Kringlan", 64.132100, -21.895355, 30 ));
        locations.add(new Location("Smáralindin", 64.102995, -21.882890, 20));
        locations.add(new Location("HR", 64.123277, -21.924934, 10));
        names = new ArrayList<String>();
        updateNames();
    }

    public static LocationsStore getInstance(){
        if(locationsStore == null){
            locationsStore = new LocationsStore();
        }
        return locationsStore;
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
        locations.add(location);
        names.add(location.getName());
    }

    public int getPosition(Location location){
        return locations.indexOf(location);
    }

    public Location getLocationByPosition(int position){
        return locations.get(position);
    }
}
