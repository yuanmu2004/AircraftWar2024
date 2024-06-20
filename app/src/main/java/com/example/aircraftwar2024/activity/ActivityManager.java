package com.example.aircraftwar2024.activity;


//import static androidx.core.view.ViewCompat.getDisplay;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.aircraftwar2024.game.OnlineGame;

import java.util.Stack;

public class ActivityManager {
    private static Stack<Activity> activityStack;
    private static ActivityManager instance;

    public static int screenWidth;
    public static int screenHeight;

    public ActivityManager() {
    }

    public static ActivityManager getActivityManager(){
        if(instance == null){
            instance = new ActivityManager();
        }
        return instance;
    }

    public void addActivity(Activity activity){
        if(activityStack == null){
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
        Log.v("info", "ActivityManager stack size:" + activityStack.size());
    }

    public Activity currentActivity(){
        Activity activity = activityStack.lastElement();
        return activity;
    }

    public void finishActivity(){
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }


    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    public void finishActivity(Class<?> cls){
        for(Activity activity : activityStack){
            if(activity.getClass().equals(cls)){
                finishActivity(activity);
            }
        }
    }

    public void finishAllActivity(){
        for(int i = 0,size = activityStack.size();i<size;i++){
            if(activityStack.get(i) != null){
                activityStack.get(i).finish();;
            }
        }
        activityStack.clear();
    }

    public void back2Title() {
        finishActivity(RankListActivity.class);
        finishActivity(GameActivity.class);
        finishActivity(OfflineActivity.class);
        finishActivity(OnlineResultActivity.class);
        finishActivity(OnlineGameActivity.class);
    }
    public void exitApp (Context context){
        try{
            finishAllActivity();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }catch(Exception ex){
        }
    }
}
