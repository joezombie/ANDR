package is.ru.aaad.RemindMe.Helpers;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by Johannes Gunnar Heidarsson on 7.11.2014.
 */
public class ErrorDialogFragment extends DialogFragment {
    private Dialog dialog;

    public ErrorDialogFragment(){
        super();
        dialog = null;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return dialog;
    }
}