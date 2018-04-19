package com.ditamex.cameraexample05;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {
    private int numCols = 4;
    private int columnWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        GridView gridView = findViewById(R.id.photoView);
        columnWidth = size.x/numCols;
        gridView.setColumnWidth(columnWidth);
        gridView.setNumColumns(numCols);
        gridView.setAdapter(new PhotoAdapter(this, columnWidth));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(),FullPhotoActivity.class);
                i.putExtra("id", position);
                startActivity(i);
            }
        });
    }
}