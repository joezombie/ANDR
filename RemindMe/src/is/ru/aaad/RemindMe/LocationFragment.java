package is.ru.aaad.RemindMe;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import is.ru.aaad.RemindMe.Helpers.LocationUtils;
import is.ru.aaad.RemindMe.Models.Location;

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

        EditText name = (EditText) getView().findViewById(R.id.location_name);
        name.setText(location.getName());

        EditText radius = (EditText) getView().findViewById(R.id.location_radius);
        radius.setText(location.getRadius().toString());

    }

}