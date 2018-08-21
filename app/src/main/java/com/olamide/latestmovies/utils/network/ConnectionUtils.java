package com.olamide.latestmovies.utils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.olamide.latestmovies.enums.ConnectionStatus;

public class ConnectionUtils {
    private static final String TAG = ConnectionUtils.class.getSimpleName();

    public static ConnectionStatus getConnectionStatus(Context context){

        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected){

            Log.e(TAG, "connection type  " +activeNetwork.getType());

            boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
            if(isWiFi){
                return ConnectionStatus.WIFI;
            }
            else {
                return ConnectionStatus.MOBILE;
            }
        }else {
            return ConnectionStatus.NONE;
        }


    }

}
