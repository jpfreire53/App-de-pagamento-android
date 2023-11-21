package com.example.appfinalcomcielo.utils;

import android.app.Activity;

public class CepUtil {

    public CepUtil(Activity activity, int... ids) {
        this.activity = activity;
        this.ids = ids;
    }

    private Activity activity;
    private int[] ids;

    public void lockField(boolean isToLock){
        for(int id : ids){
            setLockField(id, isToLock);
        }

    }

    private void setLockField(int id, boolean isToLock) {
        activity.findViewById(id).setEnabled(isToLock);
    }


}
