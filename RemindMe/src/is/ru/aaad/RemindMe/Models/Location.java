package is.ru.aaad.RemindMe.Models;

/**
 * Created by Johannes Gunnar Heidarsson on 6.11.2014.
 */
public class Location {
    private String name;
    private double latitude;
    private double longitude;

    public Location(String name) {
        this.name = name;
        this.latitude = 0;
        this.longitude = 0;
    }

    public Location(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
