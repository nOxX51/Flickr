package com.noxx.flickr;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.noxx.flickr.R.id.picture;

public class AdapterList extends BaseAdapter {
    private Context context;
    private List<Picture> myList = new ArrayList<>();
    private SharedPreferences settings;
    private OnRowDeletedListner onRowDeletedListner;

    public void setOnRowDeletedListner(OnRowDeletedListner onRowDeletedListner) {
        this.onRowDeletedListner = onRowDeletedListner;
    }

    public void setMyList(List<Picture> myList) {
        this.myList = myList;
        notifyDataSetChanged();
    }

    public AdapterList(Context context, List myList){
        this.context = context;
        this.myList = myList;
    }

    public AdapterList(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Picture getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.cell_layout, parent,false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.title_list);
        textView.setText(myList.get(position).getTitle());
        //textView.setTextColor(myList.get(position).getColor());

        TextView urlView = (TextView) convertView.findViewById(R.id.url_list);
        urlView.setText(myList.get(position).getUrl());
        //urlView.setTextColor(myList.get(position).getColor());

        ImageView imageView = (ImageView) convertView.findViewById(picture);
        Picasso.with(context).load(myList.get(position).getUrl()).fit().centerInside().into(imageView);

        FloatingActionButton button = (FloatingActionButton) convertView.findViewById(R.id.fab);
        button.setFocusable(false);
        button.setFocusableInTouchMode(false);
        button.setBackgroundTintList(ColorStateList.valueOf(myList.get(position).getColor()));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings = context.getSharedPreferences(MainActivity.FLICKR_SETTINGS,Context.MODE_PRIVATE);
                String myActivity = settings.getString(MainActivity.ACTIVITY_VIEW_TYPE,"search");
                if (myActivity.equals("historic")){
                    PicturePersistenceManager picturePersistenceManager = new PicturePersistenceManager(context);

                    //picturePersistenceManager.delete(myList.get(position));
                }
                if (onRowDeletedListner != null) {
                    onRowDeletedListner.onRowDeleted(myList.get(position));
                }
                myList.remove(position);
                notifyDataSetChanged();

            }
        });

        return convertView;
    }

}
