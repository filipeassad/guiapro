package dev.kosmo.com.br.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
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

    public static Date converterStringParaData(String data, String formato){
        try{
            SimpleDateFormat format = new SimpleDateFormat(formato);
            return format.parse(data);
        }catch (Exception e){

        }
        return new Date();
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
        //return "https://guiaproweb.herokuapp.com/";
        return "https://guiapro.herokuapp.com/";
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

    public static String obterTipoAtendimento(int codigo){

        switch (codigo){
            case 1:
                return "Ligação";
            case 2:
                return "Whatsapp";
            case 3:
                return "Me ligue";
        }
        return "";
    }

    public static String obterSituacao(int codigo){

        switch (codigo){
            case 1:
                return "Aguardando Ligação";
            case 2:
                return "Aguardando Atendimento";
            case 3:
                return "Atendimento Confirmado Pelo Cliente";
            case 4:
                return "Atendimento Não Confirmado Pelo Cliente";
            case 5:
                return "Atendido";
            case 6:
                return "Não Atendido";
            case 7:
                return "Atendimento Com Respostas Divergentes";
            case 8:
                return "Cliente Confirmou Fechamento De Trabalho";
            case 9:
                return "Cliente Não Confirmou Fechamento De Trabalho";
            case 10:
                return "Trabalho Fechado";
            case 11:
                return "Trabalho Não Fechado";
            case 12:
                return "Fechamento De Trabalho Com Respostas Divergentes";
            case 13:
                return "Cliente Confirmou Finalização Do Trabalho";
            case 14:
                return "Cliente Não Confirmou Finalização Do Trabalho";
            case 15:
                return "Trabalho Finalizado";
            case 16:
                return "Trabalho Não Finalizado";
            case 17:
                return "Finalização Do Trabalho Com Respostas Divergentes";
        }
        return "";
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }


}
