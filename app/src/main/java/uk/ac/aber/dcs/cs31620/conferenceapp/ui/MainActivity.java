package uk.ac.aber.dcs.cs31620.conferenceapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import uk.ac.aber.dcs.cs31620.conferenceapp.R;
import uk.ac.aber.dcs.cs31620.conferenceapp.model.DataService;
import uk.ac.aber.dcs.cs31620.conferenceapp.ui.events.EventsFragment;
import uk.ac.aber.dcs.cs31620.conferenceapp.ui.favourites.FavouritesFragment;
import uk.ac.aber.dcs.cs31620.conferenceapp.ui.permissions.PermissionsActivity;
import uk.ac.aber.dcs.cs31620.conferenceapp.ui.speakers.SpeakersFragment;

/**
 * This is the MainActivity it contains a pager which owns the tab fragments.
 * Including the EventsTab, FavouritesTab, and SpeakersTab.
 * <p>
 * This Activity is the home activity which shows when the app starts up, the "entry point".
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager m_pager;
    private static final int EVENT_TAB = 0;
    private static final int FAVOURITES_TAB = 1;
    private static final int SPEAKER_TAB = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ensure we initialize framework aspects
        DataService.initializeDataService(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        m_pager = findViewById(R.id.pager);
        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        m_pager.setAdapter(pagerAdapter);
        m_pager.setOffscreenPageLimit(3);

        final BottomNavigationView bottomNavView = findViewById(R.id.bottomNavigation);
        bottomNavView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_events:
                                m_pager.setCurrentItem(EVENT_TAB);
                                break;
                            case R.id.nav_favourites:
                                m_pager.setCurrentItem(FAVOURITES_TAB);
                                break;
                            case R.id.nav_speakers:
                                m_pager.setCurrentItem(SPEAKER_TAB);
                                break;
                            default:
                                return false;
                        }
                        return true;
                    }
                });

        m_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case EVENT_TAB:
                        bottomNavView.setSelectedItemId(R.id.nav_events);
                        break;
                    case FAVOURITES_TAB:
                        bottomNavView.setSelectedItemId(R.id.nav_favourites);
                        break;
                    case SPEAKER_TAB:
                        bottomNavView.setSelectedItemId(R.id.nav_speakers);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.nav_open_drawer,
                R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_permissions:
                                Intent intent = new Intent(getApplicationContext(),
                                        PermissionsActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_events:
                                m_pager.setCurrentItem(EVENT_TAB);
                                bottomNavView.setSelectedItemId(R.id.nav_events);
                                break;
                            case R.id.nav_favourites:
                                m_pager.setCurrentItem(FAVOURITES_TAB);
                                bottomNavView.setSelectedItemId(R.id.nav_favourites);
                                break;
                            case R.id.nav_speakers:
                                m_pager.setCurrentItem(SPEAKER_TAB);
                                bottomNavView.setSelectedItemId(R.id.nav_speakers);
                                break;
                        }
                        drawer.closeDrawers();
                        return true;
                    }
                });
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case EVENT_TAB:
                    return new EventsFragment();
                case FAVOURITES_TAB:
                    return new FavouritesFragment();
                case SPEAKER_TAB:
                    return new SpeakersFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case EVENT_TAB:
                    return getText(R.string.events_tab);
                case FAVOURITES_TAB:
                    return getText(R.string.favourites_tab);
                case SPEAKER_TAB:
                    return getText(R.string.speakers_tab);
            }
            return null;
        }
    }

    /**
     * Ensure that preferences are saved, these are actually just the favourites.
     */
    @Override
    protected void onPause() {
        super.onPause();
        DataService.getDataService().savePreferences();
    }

    /**
     * Ensure that the preferences are loaded back in, these are actually just the favourites.
     */
    @Override
    protected void onResume() {
        super.onResume();
        DataService.getDataService().loadPreferences();
    }
}