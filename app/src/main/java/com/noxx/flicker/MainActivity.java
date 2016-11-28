package com.noxx.flicker;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

        private FlickrService flickrService;
        boolean bound=false;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this,FlickrService.class);
        bindService(intent, Connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(Connection);
            bound=false;
        }
    }

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list);


            ListView listView = (ListView) findViewById(R.id.list);


            final AdapterList adapterList = new AdapterList(this);
            listView.setAdapter(adapterList);



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView,
                                        View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this,ShowBigCellActivity.class);
                    startActivity(intent);
                }
            });

            FloatingActionButton searchButton = (FloatingActionButton) findViewById(R.id.fab2);
            final EditText textField = (EditText) findViewById(R.id.text_field);

            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,textField.getText().toString(),Toast.LENGTH_LONG).show();

                    adapterList.setMyList(flickrService.getPhotos());

                }
            });


        }

    private ServiceConnection Connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            FlickrService.ServiceBinder binder = (FlickrService.ServiceBinder) service;
            flickrService=binder.getService();
            bound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound=false;
        }
    };

}


