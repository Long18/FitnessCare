package com.william.Fitness.Database;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.william.Fitness.Login.WelcomeStartUpScreen;

public class CheckInternet {

    public boolean isNotConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo dataConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConn!=null && wifiConn.isConnected() || dataConn != null && dataConn.isConnected())) {
            return true;
        }else{
            return false;
        }
    }

    
}
