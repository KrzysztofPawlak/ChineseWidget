package com.pawlak.krzysiek.chinwidg;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.Random;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class TimeWidgetProvider extends AppWidgetProvider {

    TextView s, v, t;
    int rowS = 1;
    int rowV = 1;
    int rowtr = 2;
    String sign, voice, trans;
    Sheet sh;

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Toast.makeText(context, "TimeWidgetRemoved id(s):" + appWidgetIds, Toast.LENGTH_SHORT).show();
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        Toast.makeText(context, "onDisabled():last widget instance removed", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        //After after 3 seconds

        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000 * 3, 900000, pi);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        ComponentName thisWidget = new ComponentName(context,
                TimeWidgetProvider.class);

        try {
            AssetManager am = context.getAssets();
            InputStream is = am.open("chinEdit.xls");
            Workbook wb = Workbook.getWorkbook(is);
            sh = wb.getSheet(0);

            int rowS;
            do  {
                rowS = (new Random().nextInt(1000));
            } while (rowS == 0 || rowS == 1);

            Cell z = sh.getCell(1, rowS*2-1); // (col, row)
            sign = z.getContents();
            Cell vo = sh.getCell(2, rowS*2-1);
            voice = vo.getContents();
            Cell tr = sh.getCell(2, rowS*2);
            trans = tr.getContents();

        } catch (Exception e) {
//            Toast.makeText(context, "chuja", Toast.LENGTH_SHORT).show();
        }

        for (int widgetId : appWidgetManager.getAppWidgetIds(thisWidget)) {

            //Get the remote views
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.time_widget_layout);

            // Set the text with the current time.
            // to tylko poczatek
            remoteViews.setTextViewText(R.id.tvTime, sign + "\n" + trans);
//            remoteViews.setTextViewText(R.id.tvVoice, Utility.getCurrentTime("hh:mm:ss a"));
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context,
                                          AppWidgetManager appWidgetManager, int appWidgetId,
                                          Bundle newOptions) {
        //Do some operation here, once you see that the widget has change its size or position.
        Toast.makeText(context, "onAppWidgetOptionsChanged() called", Toast.LENGTH_SHORT).show();
    }
}