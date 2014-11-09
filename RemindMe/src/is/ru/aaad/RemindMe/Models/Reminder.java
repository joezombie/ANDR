package is.ru.aaad.RemindMe.Models;

import java.util.UUID;

/**
 * Created by Johannes Gunnar Heidarsson on 9.11.2014.
 */
public class Reminder {
    private String name;
    private String uuid;
    private String text;
    private String LocationUUID;
    private boolean active;
    private boolean repeat;
    private Mode mode;
    private String geofenceId;

    public enum Mode{
        ARRIVING, LEAVING, ARRIVING_LEAVING, NONE;
    }

    public Reminder() {
        this.uuid = UUID.randomUUID().toString();
    }

    public Reminder(String name, String text, String locationUUID, boolean active, boolean repeat, Mode mode) {
        this.name = name;
        this.uuid = UUID.randomUUID().toString();
        this.text = text;
        LocationUUID = locationUUID;
        this.active = active;
        this.repeat = repeat;
        this.mode = mode;
    }

    public Reminder(String uuid, String name, String text, String locationUUID, boolean active, boolean repeat, Mode mode, String geofenceId) {
        this.name = name;
        this.uuid = uuid;
        this.text = text;
        LocationUUID = locationUUID;
        this.active = active;
        this.repeat = repeat;
        this.mode = mode;
        this.geofenceId = geofenceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUUID() {
        return uuid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLocationUUID() {
        return LocationUUID;
    }

    public void setLocationUUID(String locationUUID) {
        LocationUUID = locationUUID;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getGeofenceId() {
        return geofenceId;
    }

    public void setGeofenceId(String geofenceId) {
        this.geofenceId = geofenceId;
    }

    @Override
    public String toString() {
        return name;
    }
}
