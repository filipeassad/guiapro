package dev.kosmo.com.br.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by 0118431 on 19/04/2018.
 */

public class IDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("Token da App", token);

    }
}
