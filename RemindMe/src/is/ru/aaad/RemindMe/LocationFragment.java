package is.ru.aaad.RemindMe;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Johannes Gunnar Heidarsson on 6.11.2014.
 */
public class LocationFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.location_detail, container, false);
        return view;
    }

    public void changeLocation(Location location){
        Log.d("LocationFragment", "changeLocation() called");
        TextView name = (TextView) getView().findViewById(R.id.location_name);
        name.setText(location.getName());

        TextView latitude = (TextView) getView().findViewById(R.id.location_latitude);
        latitude.setText(Double.toString(location.getLatitude()));

        TextView longitude = (TextView) getView().findViewById(R.id.location_longitude);
        longitude.setText(Double.toString(location.getLongitude()));

    }
}