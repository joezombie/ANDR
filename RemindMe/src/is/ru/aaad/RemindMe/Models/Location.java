package is.ru.aaad.RemindMe.Models;

import com.google.android.gms.maps.model.LatLng;

import java.util.UUID;

/**
 * Created by Johannes Gunnar Heidarsson on 6.11.2014.
 */
public class Location {
    private String uuid;
    private String name;
    private LatLng latLng;
    private Double radius;

    public Location(String name) {
        this.name = name;
        this.latLng = null;
        this.radius = 10.0;
        setUUID();
    }

    public Location(String name, double latitude, double longitude, double radius) {
        this.name = name;
        this.latLng = new LatLng(latitude, longitude);
        this.radius = radius;
        setUUID();
    }

    public Location(String uuid, String name, double latitude, double longitude, double radius) {
        this.uuid = uuid;
        this.name = name;
        this.latLng = new LatLng(latitude, longitude);
        this.radius = radius;

    }

    private void setUUID(){
        this.uuid = UUID.randomUUID().toString();
    }

    public String getUUID() {
        return uuid;
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

    @Override
    public String toString() {
        return "Location{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", latLng=" + latLng +
                ", radius=" + radius +
                '}';
    }
}
