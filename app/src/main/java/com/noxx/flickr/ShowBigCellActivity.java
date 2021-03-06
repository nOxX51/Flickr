package com.noxx.flickr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ShowBigCellActivity extends AppCompatActivity {


    Picture picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_big_cell);

        TextView title = (TextView) findViewById(R.id.big_title);
        TextView url = (TextView) findViewById(R.id.big_url);
        ImageView image = (ImageView) findViewById(R.id.big_picture);

        picture = (Picture) getIntent().getSerializableExtra(MainActivity.PICTURE);
        title.setText(picture.getTitle());
        url.setText(picture.getUrl());

        Picasso.with(this).load(picture.getUrl()).fit().centerInside().into(image);

    }
}
