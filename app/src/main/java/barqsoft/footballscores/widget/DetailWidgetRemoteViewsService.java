package barqsoft.footballscores.widget;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;

/**
 * Created by brianduv on 10/18/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DetailWidgetRemoteViewsService extends RemoteViewsService {

    public static final String TAG = DetailWidgetRemoteViewsService.class.getSimpleName();

    private static final String[] SCORES_COLUMNS = {

            DatabaseContract.scores_table.DATE_COL,
            DatabaseContract.scores_table.MATCH_ID,
            DatabaseContract.scores_table.HOME_COL,
            DatabaseContract.scores_table.AWAY_COL,
            DatabaseContract.scores_table.HOME_GOALS_COL,
            DatabaseContract.scores_table.AWAY_GOALS_COL,
            DatabaseContract.scores_table.TIME_COL
    };
    public static final int COL_DATE = 0;
    public static final int COL_ID = 1;
    public static final int COL_HOME = 2;
    public static final int COL_AWAY = 3;
    public static final int COL_HOME_GOALS = 4;
    public static final int COL_AWAY_GOALS = 5;
    public static final int COL_MATCHTIME = 6;
    //public static final int COL_LEAGUE = 5;
    //public static final int COL_MATCHDAY = 9;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }
                final long identityToken = Binder.clearCallingIdentity();
                Uri uri = DatabaseContract.scores_table.buildScoreWithDate();
                // 1 day = 86400000 milliseconds
                Date fragmentdate = new Date(System.currentTimeMillis()+0*86400000);
                SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
                String[] selectionArgs = new String[1];
                selectionArgs[0] = mformat.format(fragmentdate);
                data = getContentResolver().query(uri,
                        SCORES_COLUMNS,
                        null,
                        selectionArgs,
                        null);
            }
            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }

            }
            @Override
            public int getCount() {
                RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_detail);
                views.setEmptyView(R.id.widget_list, R.id.widget_empty_list);

                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {

                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.widget_detail_list_item);
                data.moveToPosition(position);
                String homeTeam = data.getString(COL_HOME);
                String awayTeam = data.getString(COL_AWAY);
                String homeTeamScore = data.getString(COL_HOME_GOALS);
                String awayTeamScore = data.getString(COL_AWAY_GOALS);
                String matchTime = data.getString(COL_MATCHTIME);

                views.setTextViewText(R.id.widget_home_team, homeTeam);
                views.setTextViewText(R.id.widget_away_team, awayTeam);


                if (homeTeamScore.equals("-1") || awayTeamScore.equals("-1")) {
                    views.setTextViewText(R.id.widget_home_score, "0");
                    views.setTextViewText(R.id.widget_away_score, "0");
                    views.setTextViewText(R.id.widget_time, matchTime);
                } else {
                    views.setTextViewText(R.id.widget_home_score, homeTeamScore);
                    views.setTextViewText(R.id.widget_away_score, awayTeamScore);
                    views.setTextViewText(R.id.widget_time, getResources().getString(R.string.widget_final_score));
                }

                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_detail_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position)) {
                    return data.getLong(COL_ID);
                }
                return position;
            }
            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
