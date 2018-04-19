package com.ditamex.cameraexample05;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class FullPhotoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_photo);


        Intent i  = getIntent();
        int position = i.getExtras().getInt("id");
        PhotoAdapter photoAdapter = new PhotoAdapter(this);

        ImageView photoFullView = findViewById(R.id.photoFullView);
        //photoFullView.setImageResource(photoAdapter.photos[position]);
        photoFullView.setImageURI(Uri.parse(photoAdapter.getItem(position).toString()));

    }
}
