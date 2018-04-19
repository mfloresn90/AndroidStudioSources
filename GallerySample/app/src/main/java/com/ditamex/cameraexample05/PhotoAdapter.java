package com.ditamex.cameraexample05;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by renecruz on 26/03/18.
 */

public class PhotoAdapter extends BaseAdapter {

    private int columnWidth;
    private Context context;
    private ArrayList<File> photoList;


    public PhotoAdapter(Context context){
        this.context = context;
        photoList = photoReader(new File(Environment.getExternalStorageDirectory(), "DCIM"));
    }

    public PhotoAdapter(Context context, int columnWidth) {
        this.context = context;
        this.columnWidth = columnWidth;
        photoList = photoReader(new File(Environment.getExternalStorageDirectory(), "DCIM")); //photoReader(Environment.getExternalStorageDirectory());
    }

    ArrayList<File> photoReader(File root) {
        ArrayList<File> fileList = new ArrayList<>();
        File[] files = root.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                fileList.addAll(photoReader(files[i]));
            } else {
                if (files[i].getName().endsWith(".jpg")) {
                    fileList.add(files[i]);
                    Log.d("PHOTO", files[i].toString());

                }
            }
        }

        return fileList;
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public Object getItem(int position) {
        return photoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        Bitmap thumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(getItem(position).toString()), columnWidth, columnWidth);
        imageView.setImageBitmap(thumbImage);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(columnWidth - 10,columnWidth));
        return imageView;
    }
}
