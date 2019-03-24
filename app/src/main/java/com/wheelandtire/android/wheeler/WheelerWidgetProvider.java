package com.wheelandtire.android.wheeler;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;


public class WheelerWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.wheeler_widget_layout);
        views.setTextViewText(R.id.terminology_title_tv, "test title");
        views.setTextViewText(R.id.terminology_content_tv, context.getString(R.string.write_up_test_text));

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

}
