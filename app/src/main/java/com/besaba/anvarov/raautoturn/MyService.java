package com.besaba.anvarov.raautoturn;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

public class MyService extends Service {
	String capOn, capOff, capSw;
	private static final int NOTIFY_ID = 66;
	NotificationManager mNM;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		capOn = getString(R.string.capOn);
		capOff = getString(R.string.capOff);
		capSw = getString(R.string.txtSwitch);
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		sendNotif();
		return super.onStartCommand(intent, flags, startId);
	}

	public void onDestroy() {
		mNM.cancel(NOTIFY_ID);
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	void sendNotif() {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		//builder.setContentTitle("Автоповорот экрана").setOngoing(true).setAutoCancel(true);
		builder.setContentTitle(capSw).setOngoing(true).setAutoCancel(true);

		if (android.provider.Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION,
				0) == 0) {
			//builder.setContentText("ВЫКЛ.");
			builder.setContentText(capOff);
			builder.setSmallIcon(R.drawable.ic_stat_device_screen_lock_rotation);
		} else {
			//builder.setContentText("ВКЛ.");
			builder.setContentText(capOn);
			builder.setSmallIcon(R.drawable.ic_stat_device_screen_rotation);
		}

		Intent intent = new Intent(this, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pendingIntent);

		Notification notification = builder.build();
		notification.flags |= Notification.FLAG_ONGOING_EVENT;
		notification.flags |= Notification.FLAG_NO_CLEAR;
		mNM.notify(NOTIFY_ID, notification);
	}

}
