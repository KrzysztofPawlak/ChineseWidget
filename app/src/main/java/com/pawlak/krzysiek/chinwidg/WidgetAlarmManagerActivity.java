package com.pawlak.krzysiek.chinwidg;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class WidgetAlarmManagerActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_alarm_manager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_widget_alarm_manager, menu);
        return true;
    }


}