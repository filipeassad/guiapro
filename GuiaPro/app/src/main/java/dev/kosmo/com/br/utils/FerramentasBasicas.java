package dev.kosmo.com.br.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FerramentasBasicas {

    public static boolean isOnline(Context context){
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static String converterDataParaString(Date data, String formato){
        String dataConvertida = "";

        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
            dataConvertida = simpleDateFormat.format(data);
        }catch (Exception e){

        }

        return dataConvertida;
    }

    public static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

    public static String getURLSocket(){
        return "https://guiaproweb.herokuapp.com/";
    }

    public static String getURL(){
        //return "http://guia-pro.herokuapp.com/api/";
        //return "http://192.168.0.106:8000/api/";
        //return "http://104.236.17.236/api/";
        //return "http://192.168.0.13:3000/api/";
        //return "https://guiaproweb.herokuapp.com/api/";
        return "https://guiapro.herokuapp.com/api/";
    }

    public static void fazerLigacao(String numero){
        //insertHistorico("Você ligou para " + VariaveisEstaticas.getProfissional().getNome());
        //callIntent.setData(Uri.parse("tel:067991611563"));
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:0" + numero));
        VariaveisEstaticas.getFragmentInterface().mudaActivity(callIntent);
    }

    public static void enviarWhats(Context contexto, String numero){
        PackageManager pm = contexto.getPackageManager();
        try {
            //String url = "https://api.whatsapp.com/send?phone="+ "+55067991611563" +"&text=" + URLEncoder.encode("", "UTF-8");

            Intent i = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone="+ "+550"
                    + numero
                    +"&text="
                    + URLEncoder.encode("", "UTF-8");

            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));

            if (i.resolveActivity(pm) != null) {
                contexto.startActivity(i);
            }

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(contexto, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
