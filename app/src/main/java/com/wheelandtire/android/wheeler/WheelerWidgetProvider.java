package com.wheelandtire.android.wheeler;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import java.util.HashMap;
import java.util.Random;


public class WheelerWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.wheeler_widget_layout);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(context.getString(R.string.write_up_offset_title), context.getString(R.string.write_up_offset_text));
        hashMap.put(context.getString(R.string.write_up_center_bore_title), context.getString(R.string.write_up_center_bore_text));
        hashMap.put(context.getString(R.string.write_up_pcd_title), context.getString(R.string.write_up_pcd_text));

        Random generator = new Random();
        Object[] keys = hashMap.keySet().toArray();
        String randomKey = null;
        if (keys != null) {
            randomKey = (String) keys[generator.nextInt(keys.length)];
        }

        views.setTextViewText(R.id.terminology_title_tv, randomKey);
        views.setTextViewText(R.id.terminology_content_tv, hashMap.get(randomKey));

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

}
