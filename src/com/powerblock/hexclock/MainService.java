package com.powerblock.hexclock;


import java.io.IOException;
import java.util.Date;

import android.app.Service;
import android.app.WallpaperManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.widget.RemoteViews;

public class MainService extends Service {
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		buildUpdate();
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void buildUpdate(){
		String lastUpdated = DateFormat.format("#kkmmss", new Date()).toString();
		RemoteViews view = new RemoteViews(getPackageName(), R.layout.widget_layout);
		view.setTextViewText(R.id.widget_text, lastUpdated);
		ComponentName thisWidget = new ComponentName(this, MainProvider.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		manager.updateAppWidget(thisWidget, view);
		WallpaperManager w = WallpaperManager.getInstance(this);
		Bitmap b = Bitmap.createBitmap(1, 1, Config.ARGB_8888);
		b.eraseColor(Color.parseColor(lastUpdated));
		try {
			w.setBitmap(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
