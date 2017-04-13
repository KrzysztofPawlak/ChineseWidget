package com.pawlak.krzysiek.chinwidg;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.PowerManager;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.Random;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    TextView s, v, t;
    int rowS = 1;
    int rowV = 1;
    int rowtr = 2;
    String sign, voice, trans;
    Sheet sh;

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
        //Acquire the lock
        wl.acquire();

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

        //You can do the processing here update the widget/remote views.
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.time_widget_layout);

//        remoteViews.setTextViewText(R.id.tvTime, Utility.getCurrentTime("hh:mm:ss a"));
//        Toast.makeText(context, voice, Toast.LENGTH_SHORT).show();

//        remoteViews.setTextViewText(R.id.tvTime, sign);
//        remoteViews.setTextViewText(R.id.tvTime, sign);
        remoteViews.setTextViewText(R.id.tvTime, sign + "\n" + trans);

        ComponentName thiswidget = new ComponentName(context, TimeWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(thiswidget, remoteViews);

        //Release the lock
        wl.release();
    }
}