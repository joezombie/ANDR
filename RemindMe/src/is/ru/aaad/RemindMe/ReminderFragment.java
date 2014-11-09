package is.ru.aaad.RemindMe;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import is.ru.aaad.RemindMe.Models.Reminder;

/**
 * Created by Johannes Gunnar Heidarsson on 6.11.2014.
 */
public class ReminderFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reminder_detail, container, false);
    }

    public void setReminder(Reminder reminder){
        EditText name = (EditText) getView().findViewById(R.id.reminder_name);
        name.setText(reminder.getName());

        EditText text = (EditText) getView().findViewById(R.id.reminder_text);
        text.setText(reminder.getText());

        ToggleButton active = (ToggleButton) getView().findViewById(R.id.reminder_active);
        active.setChecked(reminder.isActive());

        ToggleButton repeat = (ToggleButton) getView().findViewById(R.id.reminder_repeat);
        repeat.setChecked(reminder.isRepeat());

        ToggleButton arriving = (ToggleButton) getView().findViewById(R.id.reminder_arriving);
        ToggleButton leaving = (ToggleButton) getView().findViewById(R.id.reminder_leaving);

        switch (reminder.getMode()){
            case ARRIVING:
                arriving.setChecked(true);
                leaving.setChecked(false);
                break;
            case LEAVING:
                leaving.setChecked(true);
                arriving.setChecked(false);
                break;
            case ARRIVING_LEAVING:
                arriving.setChecked(true);
                leaving.setChecked(true);
                break;
            case NONE:
                arriving.setChecked(false);
                leaving.setChecked(false);
                break;
        }
    }
}