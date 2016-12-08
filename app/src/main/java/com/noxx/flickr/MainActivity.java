package com.noxx.flickr;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import com.crashlytics.android.Crashlytics;
import com.mattprecious.telescope.EmailLens;
import com.mattprecious.telescope.TelescopeLayout;

import io.fabric.sdk.android.Fabric;
import java.util.List;

import static com.noxx.flickr.R.id.b1;
import static com.noxx.flickr.R.id.search_layout;
import static com.noxx.flickr.R.id.url_list;


public class MainActivity extends AppCompatActivity implements FlickrResponseListner, OnRowDeletedListner {

    public static final String NUMBER_OF_PHOTOS_WANTED = "number of photos wanted";
    public static final String PICTURE = "picture";
    public static final String DEF_VALUE = "5";
    private FlickrService flickrService;
    boolean bound = false;
    private AdapterList adapterList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private SharedPreferences settings;
    PicturePersistenceManager savePicture;

    public static final String ACTIVITY_VIEW_TYPE = "activityViewType";
    public static final String FLICKR_SETTINGS = "FLICKR_SETTINGS";

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
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_list);

        savePicture = new PicturePersistenceManager(this);
        // Restore preferences
        settings = getPreferences(MODE_PRIVATE);


        initDrawer();
        initSpinner();
        initListView();
        initSearch();
        searchLayoutVisibility();

        TelescopeLayout telescopeLayout = (TelescopeLayout) findViewById(R.id.telescope);
        telescopeLayout.setLens(new EmailLens(this, "Bug", "noxx_51@hotmail.com"));
    }

    private void searchLayoutVisibility() {
        final LinearLayout linearLayout = (LinearLayout) findViewById(search_layout);
        final PicturePersistenceManager picturePersistenceManager = new PicturePersistenceManager(this);

        final Button buttonSearch = (Button) findViewById(b1);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.VISIBLE);
                /*SharedPreferences settings = getSharedPreferences(FLICKR_SETTINGS,MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(ACTIVITY_VIEW_TYPE, "search");
                editor.commit();*/

                adapterList.setOnRowDeletedListner(null);


            }
        });
        Button historicButton = (Button) findViewById(R.id.b2);
        historicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.GONE);
                adapterList.setMyList(picturePersistenceManager.getAll());
                /*SharedPreferences settings = getSharedPreferences(FLICKR_SETTINGS,MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();

                editor.putString(ACTIVITY_VIEW_TYPE, "historic");
                editor.commit();
                if (adapterList!=null){
                    adapterList.setMyList(picturePersistenceManager.getAll());
                    adapterList.notifyDataSetChanged();
                }*/

                adapterList.setOnRowDeletedListner(MainActivity.this);


            }
        });
    }

    private void initDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initSearch() {
        FloatingActionButton searchButton = (FloatingActionButton) findViewById(R.id.fab2);
        final EditText textField = (EditText) findViewById(R.id.text_field);

        searchButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                 //Toast.makeText(MainActivity.this,textField.getText().toString(),Toast.LENGTH_LONG).show();
                 String myQuery = textField.getText().toString();
                 flickrService.getPhotos(myQuery,settings.getString(NUMBER_OF_PHOTOS_WANTED, DEF_VALUE));
                 }
              }
        );
    }

    private void initListView() {
        ListView listView = (ListView) findViewById(R.id.list);
        adapterList = new AdapterList(this);
        listView.setAdapter(adapterList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ShowBigCellActivity.class);
                intent.putExtra(PICTURE, adapterList.getItem(position));
                startActivity(intent);
                if(savePicture.getPictureByUrl(adapterList.getItem(position).getUrl()) == null) {
                    savePicture.save(adapterList.getItem(position));
                }

            }
        });
    }

    private void initSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.number_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(NUMBER_OF_PHOTOS_WANTED, adapter.getItem(position).toString());
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String saved = settings.getString(NUMBER_OF_PHOTOS_WANTED, DEF_VALUE);
        spinner.setSelection(adapter.getPosition(saved));
    }

    @Override
    protected void onPostCreate (Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged (Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
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

    @Override
    protected void onDestroy() {
        TelescopeLayout.cleanUp(this);
        super.onDestroy();
    }

    @Override
    public void onRowDeleted(Picture picture) {
        PicturePersistenceManager picturePersistenceManager = new PicturePersistenceManager(this);
        picturePersistenceManager.delete(picture);
    }
}


