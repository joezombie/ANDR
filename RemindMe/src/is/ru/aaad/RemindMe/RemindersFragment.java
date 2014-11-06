package is.ru.aaad.RemindMe;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.w3c.dom.Text;

/**
 * Created by Johannes Gunnar Heidarsson on 6.11.2014.
 */
public class RemindersFragment extends Fragment {
    private String title;
    private int page;

    public static RemindersFragment newInstance(int page, String title){
        RemindersFragment remindersFragment = new RemindersFragment();
        Bundle args = new Bundle();
        args.putInt("pageId", page);
        args.putString("pageTitle", title);
        remindersFragment.setArguments(args);
        return remindersFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("pageId", 0);
        title = getArguments().getString("pageTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reminders, container, false);
        TextView pageTitle = (TextView) view.findViewById(R.id.title);
        pageTitle.setText(page + " : " + title);
        return view;
    }
}