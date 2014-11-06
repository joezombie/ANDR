package is.ru.aaad.RemindMe;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.List;

/**
 * Created by Johannes Gunnar Heidarsson on 15.10.2014.
 */
public class MainActivity extends FragmentActivity
        implements LocationsFragment.OnLocationSelectedListener, ViewPager.OnPageChangeListener{

    private FragmentPagerAdapter fragmentPagerAdapter;
    private LocationsStore locationsStore = new LocationsStore();
    private boolean isLarge;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOnPageChangeListener(this);

        fragmentPagerAdapter = new mainPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(fragmentPagerAdapter);

        isLarge = (getSupportFragmentManager().findFragmentById(R.id.location_fragment) != null);

    }

    public static class mainPagerAdapter extends FragmentPagerAdapter{
        private int NUM_ITEMS = 2;
        Context context;

        public mainPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return RemindersFragment.newInstance(0, "Reminders");
                case 1:
                    return LocationsFragment.newInstance(0, "Locations");
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

    @Override
    public void OnLocationSelected(int position) {
        LocationFragment locationFragment = (LocationFragment) getSupportFragmentManager().findFragmentById(R.id.location_fragment);
        //LocationFragment locationFragment = null;


        if(locationFragment == null){
            Log.d("LocationsFragment", "locationFragment does equal null");
            Intent intent = new Intent(this, LocationActivity.class);
            intent.putExtra("location_position", position);
            startActivity(intent);

        } else {
            Log.d("LocationsFragment", "locationFragment does not equal null");
            locationFragment.changeLocation(locationsStore.getLocationByPosition(position));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(isLarge) {
            switch (position) {
                case 0:
                    findViewById(R.id.location_fragment).setVisibility(View.GONE);
                    findViewById(R.id.reminder_fragment).setVisibility(View.VISIBLE);
                    break;
                case 1:
                    findViewById(R.id.reminder_fragment).setVisibility(View.GONE);
                    findViewById(R.id.location_fragment).setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

