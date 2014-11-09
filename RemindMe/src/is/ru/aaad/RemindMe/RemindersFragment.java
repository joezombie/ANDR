package is.ru.aaad.RemindMe;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import is.ru.aaad.RemindMe.Data.RemindersStore;
import is.ru.aaad.RemindMe.Models.Reminder;
import org.w3c.dom.Text;

/**
 * Created by Johannes Gunnar Heidarsson on 6.11.2014.
 */
public class RemindersFragment extends ListFragment {
    private RemindersStore remindersStore;
    private OnReminderSelectedListener reminderSelectedListener;

    public interface OnReminderSelectedListener{
        public void OnReminderSelected(Reminder reminder);
    }

    public static RemindersFragment newInstance(){
        return new RemindersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        remindersStore = RemindersStore.getInstance();

        setListAdapter(
                new ArrayAdapter<Reminder>(
                        getActivity(),
                        android.R.layout.simple_list_item_activated_1,
                        remindersStore.getAll()
                )
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reminders, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if(reminderSelectedListener != null){
            reminderSelectedListener.OnReminderSelected(
                    (Reminder) l.getItemAtPosition(position)
            );
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            reminderSelectedListener = (OnReminderSelectedListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " Activity does not implement OnReminderSelectedListener");
        }
    }

    public void setReminderSelectedListener(OnReminderSelectedListener reminderSelectedListener) {
        this.reminderSelectedListener = reminderSelectedListener;
    }
}