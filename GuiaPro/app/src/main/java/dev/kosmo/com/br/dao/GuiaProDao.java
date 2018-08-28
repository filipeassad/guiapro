package dev.kosmo.com.br.dao;

import android.app.Application;

import dev.kosmo.com.br.models.DaoMaster;
import dev.kosmo.com.br.models.DaoSession;

public class GuiaProDao extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        daoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, "guiaprodb.db")
                .getWritableDb())
                .newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
