package com.example.groupbuy;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.groupbuy.connection.HttpRequestDebug;
import com.example.groupbuy.party.PartyFragment;
import com.example.groupbuy.party.PartyListFragment;
import com.example.groupbuy.party.PeopleFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final SparseArray<Fragment> FRAGMENT_BY_ID = new SparseArray<>();

    static {
        FRAGMENT_BY_ID.put(R.id.navigation_parties, new PartyListFragment());
//        FRAGMENT_BY_ID.put(R.id.navigation_profile, new ProfileFragment());
    }

    long time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(this);

        loadFragment(new PartyListFragment());
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("GroupBuy");
            actionBar.show();

            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.navigation_logout)
            return logout();
        else
            return loadFragment(FRAGMENT_BY_ID.get(menuItem.getItemId()));
    }

    boolean logout() {
        new HttpRequestDebug(this).logOut();
        return true;
    }

    @Override
    public void onBackPressed() {
        long now = System.currentTimeMillis();

        if (now - time < 1000) {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        } else {
            loadFragment(new PartyListFragment());

            Toast.makeText(this, "Press once again to exit!", Toast.LENGTH_SHORT).show();
            time = now;
        }
    }

    public void openPartyFragment(String party) {
        loadFragment(PartyFragment.newInstance(party));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(party);
        actionBar.show();
    }

    public void openPeopleFragment(String party) {
        loadFragment(PeopleFragment.newInstance(party));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(party);
        actionBar.show();
    }
}
