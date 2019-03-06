package com.interfeis.forumluthfi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.interfeis.forumluthfi.MainActivityFragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    ActionBarDrawerToggle left_menu_toggle;

    boolean clickExit = false;

    SharedPreferences data_app;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        left_menu_toggle.syncState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        openAppFragment( new MainFragment() );

        data_app = getSharedPreferences("DATA_APP", MODE_PRIVATE);

        final DrawerLayout main_act_layout = (DrawerLayout) findViewById(R.id.layout_main_activity);

        NavigationView left_menu = (NavigationView) findViewById(R.id.nv_left_menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        left_menu_toggle = new ActionBarDrawerToggle( MainActivity.this,
                main_act_layout,
                (R.string.open),
                (R.string.close)
        );

        main_act_layout.addDrawerListener(left_menu_toggle);

        left_menu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if(menuItem.getItemId() == R.id.left_menu_home){

                    openAppFragment(new MainFragment());

                    Toast.makeText(getApplicationContext(), getString(R.string.home), Toast.LENGTH_SHORT).show();
                    main_act_layout.closeDrawers();

                }else if(menuItem.getItemId() == R.id.left_menu_logout){

                    removeSharedData();

                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);

                    finish();
                }

                return false;
            }
        });

    }

    public void openAppFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frm_main_activity, fragment)
                .commit();
    }

    public void removeSharedData(){
        getSharedPreferences("DATA_APP", MODE_PRIVATE).edit().clear().commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        left_menu_toggle.onOptionsItemSelected(item);

        return super.onOptionsItemSelected(item);
    }
}
