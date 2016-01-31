package com.besaba.anvarov.raautoturn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class MainActivity extends Activity implements OnClickListener {

	Button mClose, mKill;
	Switch mRotate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mClose = (Button) findViewById(R.id.btCloseView);
		mClose.setOnClickListener(this);
		mKill = (Button) findViewById(R.id.btKillApp);
		mKill.setOnClickListener(this);
		mRotate = (Switch) findViewById(R.id.swRotate);
		mRotate.setChecked(android.provider.Settings.System.getInt(getContentResolver(),
				Settings.System.ACCELEROMETER_ROTATION, 0) == 1);
		OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					Settings.System.putInt(getContentResolver(),
							Settings.System.ACCELEROMETER_ROTATION, 1);
		        } else {
		        	Settings.System.putInt(getContentResolver(),
							Settings.System.ACCELEROMETER_ROTATION, 0);
		        }
				startService(new Intent(MainActivity.this, MyService.class));
			}
		};
		mRotate.setOnCheckedChangeListener(onCheckedChangeListener);
		startService(new Intent(MainActivity.this, MyService.class));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) return true;
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btCloseView:
			finish();
			break;
		case R.id.btKillApp:
			stopService(new Intent(this, MyService.class));
			finish();
			break;
		}
	}
}
