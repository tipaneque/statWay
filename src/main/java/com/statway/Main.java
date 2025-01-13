package com.statway;

import com.statway.applications.HomeApplication;

import java.util.prefs.Preferences;

public class Main {
    private static final String REGYSTRY_KEY = "com/statway";
    private static final String PREF_KEY_EXECUTION_ALLOWED = "execution_allowed";
    private static final String PREF_KEY_FIRST_RUN_TIMESTAMP = "first_run_timestamp";
    private static final long TWO_MINUTES_IN_MILLIS = 120*1000;
    public static void main(String[] args) {
        Preferences prefs = Preferences.userRoot().node(REGYSTRY_KEY);

        boolean isExecutionAllowed = prefs.getBoolean(PREF_KEY_EXECUTION_ALLOWED, true);

        if(!isExecutionAllowed){
            System.out.println("Applicacao bloqueada! Você já utilizou sua execução permitida.");
            return;
        }

        long firstRunTimestamp = prefs.getLong(PREF_KEY_FIRST_RUN_TIMESTAMP, 0);
        if(firstRunTimestamp == 0){
            firstRunTimestamp = System.currentTimeMillis();
            prefs.putLong(PREF_KEY_FIRST_RUN_TIMESTAMP, firstRunTimestamp);
        }

        long currentTime = System.currentTimeMillis();
        if(currentTime - firstRunTimestamp > TWO_MINUTES_IN_MILLIS){
            System.out.println("Tempo de execução expiraado. Aplicação será bloqueada.");
            prefs.putBoolean(PREF_KEY_EXECUTION_ALLOWED, false);
            return;
        }
        System.out.println("Aplicação em execução. Você tem 2 minutos.");
        HomeApplication.main(args);


    }
}
