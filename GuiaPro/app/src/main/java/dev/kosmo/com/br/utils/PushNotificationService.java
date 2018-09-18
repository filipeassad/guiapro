package dev.kosmo.com.br.utils;


import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import java.net.URISyntaxException;

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
            socket = IO.socket(FerramentasBasicas.getURL());
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                }
            }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                }
            }).on(Socket.EVENT_MESSAGE, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                }
            }).on("guiapro-notificacao-" + VariaveisEstaticas.getUsuario().getPerfilId(), new Emitter.Listener() {
                @Override
                public void call(Object... args) {

                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(getBaseContext())
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setContentTitle("Notificação do cliente")
                                    .setContentText("Mensagem: " + args[0]);

                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(getBaseContext().NOTIFICATION_SERVICE);

                    mNotificationManager.notify(1, builder.build());
                }
            });

            socket.connect();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

}