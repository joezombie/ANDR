package is.ru.aaad.RemindMe;



import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import is.ru.aaad.RemindMe.Data.LocationsStore;

/**
 * Created by Johannes Gunnar Heidarsson on 6.11.2014.
 */
public class LocationsFragment extends ListFragment {
    private OnLocationSelectedListener locationSelectedListener;
    private LocationsStore locationsStore;


    public interface OnLocationSelectedListener{
        public void OnLocationSelected(int position);
    }

    public static LocationsFragment newInstance(){
        return new LocationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationsStore = LocationsStore.getInstance();

        setListAdapter(
                new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_list_item_activated_1,
                    locationsStore.getNames()
                )
        );
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.locations, container, false);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        try{
            locationSelectedListener = (OnLocationSelectedListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " Activity does not implement OnLocationSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        locationSelectedListener.OnLocationSelected(position);
        getListView().setItemChecked(position, true);

    }

    @Override
    public void onStart() {
        super.onStart();
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
}