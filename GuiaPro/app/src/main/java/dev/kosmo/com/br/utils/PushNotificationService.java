package dev.kosmo.com.br.utils;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.net.URISyntaxException;

import dev.kosmo.com.br.activitys.MainActivity;
import dev.kosmo.com.br.guiapro.R;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class PushNotificationService extends Service {

    private Socket socket;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //"http://192.168.0.23:4555/"
        try {
            //socket = IO.socket("http://192.168.254.176:4555");
            //Log.v("ID Profissional", VariaveisEstaticas.getUsuario().getPerfilId() + "");
            IO.Options options = new IO.Options();
            options.reconnectionDelay = 1000;
            options.reconnection = true;
            socket = IO.socket(FerramentasBasicas.getURLSocket(), options);

            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    //Log.v("Conectou", args.toString());
                }
            }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    //Log.v("Erro de conexão", args[0].toString());
                }
            }).on(Socket.EVENT_MESSAGE, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                }
            }).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    //Log.e("timeout", "true");
                    socket.connect();
                }
            }).on("guiapro-notificacao-" + VariaveisEstaticas.getUsuario().getPerfilId(), new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    try{
                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(getBaseContext())
                                        .setSmallIcon(R.drawable.logo_guiapro_notification)
                                        .setLargeIcon(BitmapFactory.decodeResource(getBaseContext().getResources(),
                                                R.mipmap.ic_launcher))
                                        .setContentTitle("Notificação do cliente")
                                        .setContentText("Você possui uma solicitação de ligação!")
                                        .setContentIntent(pendingIntent);

                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(getBaseContext().NOTIFICATION_SERVICE);

                        mNotificationManager.notify(1, builder.build());
                    }catch (Exception e){

                    }
                }
            });

            socket.connect();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }catch (Exception e){

        }

        return super.onStartCommand(intent, flags, startId);
    }

}