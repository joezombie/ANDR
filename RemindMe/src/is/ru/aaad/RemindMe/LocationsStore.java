package is.ru.aaad.RemindMe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johannes Gunnar Heidarsson on 6.11.2014.
 */
public class LocationsStore {
    private List<Location> locations;

    public LocationsStore() {
        locations = new ArrayList<Location>();
        locations.add(new Location("Kringlan", 64.132100, -21.895355 ));
        locations.add(new Location("Smáralindin", 64.102995, -21.882890));
        locations.add(new Location("HR", 64.123277, -21.924934));
    }

    public List<Location> getAll(){
        return locations;
    }

    public List<String> getNames(){
        ArrayList<String> names = new ArrayList<String>();

        for(Location l : locations){
            names.add(l.getName());
        }

        return names;
    }

    public void add(Location location){
        locations.add(location);
    }

    public Location getLocationByPosition(int position){
        return locations.get(position);
    }
}
