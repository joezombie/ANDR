package is.ru.aaad.RemindMe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;
import is.ru.aaad.RemindMe.Data.LocationsStore;
import is.ru.aaad.RemindMe.Data.RemindersStore;
import is.ru.aaad.RemindMe.Helpers.GeofenceRequester;
import is.ru.aaad.RemindMe.Models.Location;
import is.ru.aaad.RemindMe.Models.Reminder;

/**
 * Created by Johannes Gunnar Heidarsson on 9.11.2014.
 */
public class ReminderActivity extends FragmentActivity {
    private RemindersStore remindersStore;
    private LocationsStore locationsStore;
    private ReminderFragment reminderFragment;
    private Reminder reminder;
    private GeofenceRequester geofenceRequester;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder);

        remindersStore = RemindersStore.getInstance();
        locationsStore = LocationsStore.getInstance();
        geofenceRequester = new GeofenceRequester(this);

        String uuid = getIntent().getStringExtra("reminder_uuid");

        reminder = remindersStore.get(uuid);

        reminderFragment = (ReminderFragment) getSupportFragmentManager().findFragmentById(R.id.reminder_fragment);

        reminderFragment.setReminder(reminder);
    }

    public void saveReminder(View v){
        EditText name = (EditText) findViewById(R.id.reminder_name);
        reminder.setName(name.getText().toString());

        EditText text = (EditText) findViewById(R.id.reminder_text);
        reminder.setText(text.getText().toString());

        ToggleButton active = (ToggleButton) findViewById(R.id.reminder_active);
        reminder.setActive(active.isChecked());

        ToggleButton repeat = (ToggleButton) findViewById(R.id.reminder_repeat);
        reminder.setRepeat(repeat.isChecked());

        ToggleButton arriving = (ToggleButton) findViewById(R.id.reminder_arriving);
        ToggleButton leaving = (ToggleButton) findViewById(R.id.reminder_leaving);

        if(arriving.isChecked()){
            if(leaving.isChecked()){
                reminder.setMode(Reminder.Mode.ARRIVING_LEAVING);
            }else {
                reminder.setMode(Reminder.Mode.ARRIVING);
            }
        } else if (leaving.isChecked()){
            reminder.setMode(Reminder.Mode.LEAVING);
        } else {
            reminder.setMode(Reminder.Mode.NONE);
        }

        remindersStore.add(reminder);
        geofenceRequester.request(reminder);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void removeReminder(View v){
        remindersStore.remove(reminder);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void cancelReminderEdit(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void selectLocation(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose location").setAdapter(new ArrayAdapter<String>(
                this,
                android.R.layout.select_dialog_singlechoice,
                locationsStore.getNames()
        ), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Location location = locationsStore.getLocationByPosition(which);
                Button button = (Button) findViewById(R.id.select_location);
                button.setText(location.getName());
                reminder.setLocationUUID(location.getUUID());
            }
        }).setCancelable(true).show();
    }


}