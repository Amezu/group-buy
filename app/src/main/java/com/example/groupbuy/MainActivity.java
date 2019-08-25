package com.example.groupbuy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.groupbuy.connection.HttpRequestDebug;
import com.example.groupbuy.party.PartyListFragment;
import com.example.groupbuy.party.PartyFragment;
import com.example.groupbuy.party.PeopleFragment;
import com.example.groupbuy.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    long time = 0;

    private static final SparseArray<Fragment> FRAGMENT_BY_ID = new SparseArray<>();

    static {
        FRAGMENT_BY_ID.put(R.id.navigation_parties, new PartyListFragment());
//        FRAGMENT_BY_ID.put(R.id.navigation_profile, new ProfileFragment());
    }

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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.navigation_logout)
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
//        TODO: Implement going back when all activities will be changed to fragments
//         https://stackoverflow.com/questions/5448653/how-to-implement-onbackpressed-in-fragments
//         or https://medium.com/@Wingnut/onbackpressed-for-fragments-357b2bf1ce8e

        long now = System.currentTimeMillis();

        if(now - time < 1000) {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        else {
            loadFragment(new PartyListFragment());

            Toast.makeText(this, "Press once again to exit!", Toast.LENGTH_SHORT).show();
            time = now;
        }
    }

    public void openProductsFragment(String groupName) {
        loadFragment(PartyFragment.newInstance(groupName));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(groupName);
        actionBar.show();
    }

    public void openPeopleFragment() {
        loadFragment(new PeopleFragment());
    }
}
