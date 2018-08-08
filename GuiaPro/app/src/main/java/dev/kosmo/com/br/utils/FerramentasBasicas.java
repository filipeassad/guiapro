package dev.kosmo.com.br.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class FerramentasBasicas {

    public static boolean isOnline(Context context){
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static String getURL(){
        return "http://guia-pro.herokuapp.com/api/";
        //return "http://192.168.0.106:8000/api/";
        //return "http://104.236.17.236/api/";
    }
}
