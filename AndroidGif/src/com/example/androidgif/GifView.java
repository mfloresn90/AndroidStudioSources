package com.example.androidgif;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

public class GifView extends View {
	
	//Set true to use decodeStream
	//Set false to use decodeByteArray
    private static final boolean DECODE_STREAM = true;
	
	private InputStream gifInputStream;
	private Movie gifMovie;
	private int movieWidth, movieHeight;
	private long movieDuration;
	private long mMovieStart;
	
	public GifView(Context context) {
		super(context);
		init(context, null);
	}

	public GifView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public GifView(Context context, AttributeSet attrs, 
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}
	
	private void init(final Context context, AttributeSet attrs){
		setFocusable(true);
		
		if(attrs == null){
			Toast.makeText(getContext(), 
					"gifResource: null", 
					Toast.LENGTH_LONG).show();
			
			gifMovie = null;
			movieWidth = 0;
			movieHeight = 0;
			movieDuration = 0;
		}else{
			
			int gifResource = attrs.getAttributeResourceValue(
					"http://schemas.android.com/apk/res/android", 
					"src", 
					0);
			
			if(gifResource == 0){
				Toast.makeText(getContext(), 
						"gifResource: 0", 
						Toast.LENGTH_LONG).show();
				
				gifMovie = null;
				movieWidth = 0;
				movieHeight = 0;
				movieDuration = 0;
			}else{
				Toast.makeText(getContext(), 
						"gifResource: " + gifResource, 
						Toast.LENGTH_LONG).show();
				
				gifInputStream = context.getResources().openRawResource(gifResource);
				
				if(DECODE_STREAM){
					gifMovie = Movie.decodeStream(gifInputStream);
				}else{
					byte[] array = streamToBytes(gifInputStream);
					gifMovie = Movie.decodeByteArray(array, 0, array.length);
				}

				movieWidth = gifMovie.width();
				movieHeight = gifMovie.height();
				movieDuration = gifMovie.duration();
			}	
		}
	}
	
	private static byte[] streamToBytes(InputStream is) {
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = is.read(buffer)) >= 0) {
                os.write(buffer, 0, len);
            }
        } catch (java.io.IOException e) {
        }
        return os.toByteArray();
    }

	@Override
	protected void onMeasure(int widthMeasureSpec, 
			int heightMeasureSpec) {
		setMeasuredDimension(movieWidth, movieHeight);
	}
	
	public int getMovieWidth(){
		return movieWidth;
	}
	
	public int getMovieHeight(){
		return movieHeight;
	}
	
	public long getMovieDuration(){
		return movieDuration;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		long now = android.os.SystemClock.uptimeMillis();
        if (mMovieStart == 0) {   // first time
            mMovieStart = now;
        }
        
        if (gifMovie != null) {

            int dur = gifMovie.duration();
            if (dur == 0) {
                dur = 1000;
            }

            int relTime = (int)((now - mMovieStart) % dur);
            
            gifMovie.setTime(relTime);

            gifMovie.draw(canvas, 0, 0);
            invalidate();
            
        }
        
	}

}
