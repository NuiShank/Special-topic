package com.shank.sebsorlock;

import android.app.Activity;
import android.os.Bundle;

public class PrefsActivity extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		getFragmentManager().beginTransaction()
        .replace(android.R.id.content, new PrefsFragment())
        .commit();
	}
}