package is.ru.aaad.RemindMe.Models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Johannes Gunnar Heidarsson on 6.11.2014.
 */
public class Location {
    private String name;
    private LatLng latLng;
    private Double radius;

    public Location(String name) {
        this.name = name;
        this.latLng = null;
        this.radius = 10.0;
    }

    public Location(String name, double latitude, double longitude, double radius) {
        this.name = name;
        this.latLng = new LatLng(latitude, longitude);
        this.radius = radius;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latLng.latitude;
    }

    public double getLongitude() {
        return latLng.longitude;
    }

    public void setLatLng(LatLng latLng){
        this.latLng = latLng;
    }

    public LatLng getLatLng(){
        return latLng;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }
}
