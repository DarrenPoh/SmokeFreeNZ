package com.darren.darren.smokewise;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

public class Drawer extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    public ListView mDrawerList;
    //private ArrayAdapter<String> mAdapter;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    Home home;

    protected DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.darren.darren.smokewise.R.layout.activity_drawer);

        mDrawerList = (ListView)findViewById(com.darren.darren.smokewise.R.id.navList);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(com.darren.darren.smokewise.R.id.navigation_drawer);
        mTitle = getTitle();
        //addDrawerItems();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                com.darren.darren.smokewise.R.id.navigation_drawer,
                (DrawerLayout) findViewById(com.darren.darren.smokewise.R.id.drawer_layout));

        mDrawer = (DrawerLayout) findViewById(com.darren.darren.smokewise.R.id.drawer_layout);

    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(com.darren.darren.smokewise.R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(com.darren.darren.smokewise.R.string.title_activity_life_benefits);
                startActivity(new Intent(Drawer.this, LifeBenefits.class));

                break;
            case 2:
                mTitle = getString(com.darren.darren.smokewise.R.string.title_activity_motivation);
                startActivity(new Intent(Drawer.this, Motivation.class));
                break;
            case 3:
                mTitle = getString(com.darren.darren.smokewise.R.string.title_Facebook);
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/SmokeFree-NZ-796120033855044/"));
                    startActivity(intent);

                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/SmokeFree-NZ-796120033855044/")));

                }
                break;
            case 4:
                mTitle = getString(com.darren.darren.smokewise.R.string.title_activity_extra_help);
                startActivity(new Intent(Drawer.this, ExtraHelp.class));

                break;

            case 5:
                mTitle = getString(com.darren.darren.smokewise.R.string.title_WatchVideo);
                startActivity(new Intent(Drawer.this, WatchVideo.class));

                break;

            case 6:
                mTitle = getString(com.darren.darren.smokewise.R.string.title_activity_account);
                startActivity(new Intent(Drawer.this, Account.class));
                finish();
                break;
        }

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(com.darren.darren.smokewise.R.menu.drawer, menu);
            restoreActionBar();
            mDrawerLayout = (DrawerLayout)findViewById(com.darren.darren.smokewise.R.id.drawer_layout);
            mActivityTitle = getTitle().toString();
            return true;


        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.darren.darren.smokewise.R.id.action_settings) {
            startActivity(new Intent(this, Account.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(com.darren.darren.smokewise.R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Drawer) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

/*    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }*/

}
