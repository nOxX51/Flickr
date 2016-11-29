package com.noxx.flickr;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements FlickrResponseListner {

    private FlickrService flickrService;
    boolean bound = false;
    private AdapterList adapterList;

    private DrawerLayout DrawerLayout;
    private ActionBarDrawerToggle DrawerToggle;
    private String mTitle;
    private String mDrawerTitle;


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, FlickrService.class);
        bindService(intent, Connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(Connection);
            bound = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        DrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        DrawerToggle = new ActionBarDrawerToggle(this, DrawerLayout, R.string.drawer_open, R.string.drawer_close) {


            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
            }


            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        DrawerLayout.setDrawerListener(DrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        ListView listView = (ListView) findViewById(R.id.list);
        adapterList = new

                AdapterList(this);

        listView.setAdapter(adapterList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ShowBigCellActivity.class);
                intent.putExtra("picture", adapterList.getItem(position));
                startActivity(intent);
            }

        });

        FloatingActionButton searchButton = (FloatingActionButton) findViewById(R.id.fab2);
        final EditText textField = (EditText) findViewById(R.id.text_field);

        searchButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                 //Toast.makeText(MainActivity.this,textField.getText().toString(),Toast.LENGTH_LONG).show();
                 String myQuery = textField.getText().toString();
                 flickrService.getPhotos(myQuery);
                 }
              }

        );



    }

    @Override
    protected void onPostCreate (Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        DrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged (Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        DrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (DrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    private ServiceConnection Connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            FlickrService.ServiceBinder binder = (FlickrService.ServiceBinder) service;
            flickrService = binder.getService();
            flickrService.setFlickrResponseListner(MainActivity.this);
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    @Override
    public void onPhotosReceived (List < Picture > pictureList) {
        adapterList.setMyList(pictureList);
    }
}


