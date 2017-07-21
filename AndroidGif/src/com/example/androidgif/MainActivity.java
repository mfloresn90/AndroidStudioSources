package com.example.androidgif;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView textViewInfo;
	GifView gifView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gifView = (GifView)findViewById(R.id.gifview);
		textViewInfo = (TextView)findViewById(R.id.textinfo);

		String stringInfo = "";
		stringInfo += "Duration: " + gifView.getMovieDuration() + "\n";
		stringInfo += "W x H: " 
				+ gifView.getMovieWidth() + " x " 
				+ gifView.getMovieHeight() + "\n";
			
		textViewInfo.setText(stringInfo);

	}

}
