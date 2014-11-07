package is.ru.aaad.RemindMe.Helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import is.ru.aaad.RemindMe.LocationsFragment;
import is.ru.aaad.RemindMe.R;
import is.ru.aaad.RemindMe.RemindersFragment;

/**
 * Created by Johannes Gunnar Heidarsson on 7.11.2014.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    private int NUM_ITEMS = 2;
    Context context;
    RemindersFragment remindersFragment;
    LocationsFragment locationsFragment;


    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        remindersFragment = RemindersFragment.newInstance(0, "Reminders");
        locationsFragment = LocationsFragment.newInstance(0, "Locations");
        this.context = context;
    }

    public LocationsFragment getLocationsFragment(){
        return locationsFragment;
    }

    public RemindersFragment getRemindersFragment(){
        return remindersFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return remindersFragment;
            case 1:
                return locationsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        SpannableStringBuilder sb; // space added before text for convenience

        Drawable icon;

        switch (position){
            case 0:
                sb = new SpannableStringBuilder(" Reminders");
                icon = context.getResources().getDrawable(R.drawable.ic_action_event);
                break;
            case 1:
                sb = new SpannableStringBuilder(" Locations");
                icon = context.getResources().getDrawable(R.drawable.ic_action_place);
                break;
            default:
                return null;
        }

        icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(icon, ImageSpan.ALIGN_BASELINE);
        sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sb;
    }
}
