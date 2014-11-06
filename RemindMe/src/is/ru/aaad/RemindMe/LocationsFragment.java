package is.ru.aaad.RemindMe;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Johannes Gunnar Heidarsson on 6.11.2014.
 */
public class LocationsFragment extends ListFragment {
    private OnLocationSelectedListener locationSelectedListener;
    private LocationsStore locationsStore;
    private String title;
    private int page;

    public interface OnLocationSelectedListener{
        public void OnLocationSelected(int position);
    }

    public static LocationsFragment newInstance(int page, String title){
        LocationsFragment locationsFragment = new LocationsFragment();
        Bundle args = new Bundle();
        args.putInt("pageId", page);
        args.putString("pageTitle", title);
        locationsFragment.setArguments(args);
        return locationsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        page = getArguments().getInt("pageId", 0);
        title = getArguments().getString("pageTitle");

        locationsStore = new LocationsStore();

        setListAdapter(
                new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_list_item_activated_1,
                    locationsStore.getNames()
                )
        );
    }

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.locations, container, false);
        //TextView pageTitle = (TextView) view.findViewById(R.id.title);
        //pageTitle.setText(page + " : " + title);
        return view;
    }*/

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
        /*
        LocationFragment locationFragment = (LocationFragment) getFragmentManager().findFragmentById(R.id.location_fragment);
        //LocationFragment locationFragment = null;


        if(locationFragment == null){
            Log.d("LocationsFragment", "locationFragment does equals null");
            Intent intent = new Intent(getActivity(), LocationActivity.class);
            intent.putExtra("location_position", position);
            startActivity(intent);

        } else {
            Log.d("LocationsFragment", "locationFragment does not equal null");
            locationFragment.changeLocation(locationsStore.getLocationByPosition(position));
        }
        */

    }

    @Override
    public void onStart() {
        super.onStart();
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
}