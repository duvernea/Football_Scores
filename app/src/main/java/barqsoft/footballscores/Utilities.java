package barqsoft.footballscores;

import android.content.Context;

/**
 * Created by yehya khaled on 3/3/2015.
 */
public class Utilities
{
    // league numbers used in getLeague
    // THIS CHANGES EVERY SEASON
    public static final int BUNDESLIGA = 394; // actually BUNDESLIGA1
    public static final int BUNDESLIGA2 = 395;
    public static final int LIGUE1 = 396;
    public static final int LIGUE2 = 397;
    public static final int PREMIER_LEAGUE = 398;
    public static final int PRIMERA_DIVISION = 399;
    public static final int SEGUNDA_DIVISION = 400;
    public static final int SERIE_A = 401;
    public static final int PRIMERA_LIGA = 402;
    public static final int BUNDESLIGA3 = 403;
    public static final int EREDIVISIE = 404;
    public static final int CHAMPIONS_LEAGUE = 405;


    // team names to get logos
    private static final String ARSENAL_LONDON_FC = "Arsenal London FC";
    private static final String MANCHESTER_UNITED_FC = "Manchester United FC";
    private static final String SWANSEA_CITY = "Swansea City";
    private static final String LEICESTER_CITY = "Leicester City";
    private static final String EVERTON_FC = "Everton FC";
    private static final String WEST_HAM_UNITED_FC = "West Ham United FC";
    private static final String TOTTENHAM_HOTSPUR_FC = "Tottenham Hotspur FC";
    private static final String WEST_BROMWICH_ALBION = "West Bromwich Albion";
    private static final String SUNDERLAND_AFC = "Sunderland AFC";
    private static final String STOKE_CITY_FC = "Stoke City FC";

    public static String getLeague(Context context, int league_num)
    {
        switch (league_num)
        {
            case SERIE_A : return context.getResources().getString(R.string.Seria_A);
            case PREMIER_LEAGUE : return context.getResources().getString(R.string.Premier_League);
            case CHAMPIONS_LEAGUE : return context.getResources().getString(R.string.Champions_League);
            case PRIMERA_DIVISION : return context.getResources().getString(R.string.Primera_Division);
            case BUNDESLIGA : return context.getResources().getString(R.string.Bundesliga);
            default: return context.getResources().getString(R.string.league_not_known_msg);
        }
    }
    public static String getMatchDay(Context context, int match_day,int league_num)
    {
        if(league_num == CHAMPIONS_LEAGUE)
        {
            if (match_day <= 6)
            {
                return context.getResources().getString(R.string.matchday_6_or_less);
            }
            else if(match_day == 7 || match_day == 8)
            {
                return context.getResources().getString(R.string.matchday_7_8);
            }
            else if(match_day == 9 || match_day == 10)
            {
                return context.getResources().getString(R.string.matchday_9_10);
            }
            else if(match_day == 11 || match_day == 12)
            {
                return context.getResources().getString(R.string.matchday_11_12);
            }
            else
            {
                return context.getResources().getString(R.string.matchday_13);
            }
        }
        else
        {
            return context.getResources().getString(R.string.matchday_prefix) + String.valueOf(match_day);
        }
    }

    public static String getScores(int home_goals,int awaygoals)
    {
        if(home_goals < 0 || awaygoals < 0)
        {
            return " - ";
        }
        else
        {
            return String.valueOf(home_goals) + " - " + String.valueOf(awaygoals);
        }
    }

    public static int getTeamCrestByTeamName (String teamname)
    {
        if (teamname==null){return R.drawable.no_icon;}
        switch (teamname)
        { //This is the set of icons that are currently in the app. Feel free to find and add more
            //as you go.
            case ARSENAL_LONDON_FC : return R.drawable.arsenal;
            case MANCHESTER_UNITED_FC : return R.drawable.manchester_united;
            case SWANSEA_CITY : return R.drawable.swansea_city_afc;
            case LEICESTER_CITY : return R.drawable.leicester_city_fc_hd_logo;
            case EVERTON_FC : return R.drawable.everton_fc_logo1;
            case WEST_HAM_UNITED_FC : return R.drawable.west_ham;
            case TOTTENHAM_HOTSPUR_FC : return R.drawable.tottenham_hotspur;
            case WEST_BROMWICH_ALBION : return R.drawable.west_bromwich_albion_hd_logo;
            case SUNDERLAND_AFC : return R.drawable.sunderland;
            case STOKE_CITY_FC : return R.drawable.stoke_city;
            default: return R.drawable.no_icon;
        }
    }
}
