package com.noxx.flicker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        List<Picture> pictureList = new ArrayList<>();

        pictureList.add(new Picture(Color.MAGENTA,"Donatello", "http://donatello.pic.com", R.drawable.donatello_2));
        pictureList.add(new Picture (Color.BLUE,"Leonardo", "http://leonardo.pic.com", R.drawable.leonardo_2));
        pictureList.add(new Picture (Color.RED,"Raphael", "http://Raphael.pic.com", R.drawable.raphael_sc3a9rie_tv_2003_61));
        pictureList.add(new Picture (Color.YELLOW,"Michelangelo", "http://Michelangelo.pic.com", R.drawable.mikey_5));



        ListView listView = (ListView) findViewById(R.id.list);

        AdapterList myList = new AdapterList(this, pictureList);

        listView.setAdapter(myList);

        myList.setMyList(pictureList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {
                Intent intent = new Intent(ListActivity.this,ShowBigCellActivity.class);
                startActivity(intent);
            }
        });


    }

}
