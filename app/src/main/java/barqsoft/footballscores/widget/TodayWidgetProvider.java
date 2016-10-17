package barqsoft.footballscores.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;


/**
 * Created by brianduv on 10/19/2015.
 */
public class TodayWidgetProvider extends AppWidgetProvider {
    private static final String TAG = TodayWidgetProvider.class.getSimpleName();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d(TAG, "Large Widget Provider, onUpdate()");
        for (int appWidgetId: appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_detail);

            // Intent to launch Main Activity
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            // set up the collection
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                setRemoteAdapter(context, views);
            }
            else {
                setRemoteAdapterV11(context, views);
            }
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.widget_list, new Intent(context, DetailWidgetRemoteViewsService.class));
        Log.d(TAG, "setRemoteAdapter");
    }
    @SuppressWarnings("deprecation")
    private void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(0, R.id.widget_list,
                new Intent(context, DetailWidgetRemoteViewsService.class));
    }
}
