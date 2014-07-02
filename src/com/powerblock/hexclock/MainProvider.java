package com.powerblock.hexclock;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MainProvider extends AppWidgetProvider {
	private PendingIntent service = null;
	public static String ACTION_WIDGET_RECEIVER = "ActionReceiverWidget";
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds){
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		for(int i = 0; i < appWidgetIds.length; i++){
			
			final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			
			final Calendar time = Calendar.getInstance();
			time.set(Calendar.MINUTE, 0);
			time.set(Calendar.SECOND, 0);
			time.set(Calendar.MILLISECOND, 0);
			
			final Intent intent = new Intent(context,MainService.class);
			if(service == null){
				service = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			}
			
			m.setRepeating(AlarmManager.RTC, time.getTime().getTime(), 1000*1 , service);
		}
	}
	
	@Override
	public void onDisabled(Context context) {
		Log.v("Test","Cancelled");
		super.onDisabled(context);
		final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		if(service != null){
			Log.v("Test","Calling Cancel");
			m.cancel(service);
		}else{
			final Intent intent = new Intent(context,MainService.class);
			service = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			m.cancel(service);
		}
	}
}
