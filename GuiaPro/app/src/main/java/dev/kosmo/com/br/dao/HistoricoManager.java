package dev.kosmo.com.br.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

import dev.kosmo.com.br.models.Historico;
import dev.kosmo.com.br.models.Profissional;

/**
 * Created by Filipe on 08/05/2018.
 */

public class HistoricoManager {

    private SQLiteDatabase db;

    public HistoricoManager(SQLiteDatabase db) {
        this.db = db;
    }


    public List<Historico> getAllHistorico(){

        List<Historico> lista = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM historico ORDER BY data", new String[]{});
        ClienteManager clienteManager = new ClienteManager(db);
        ProfissionalManager profissionalManager = new ProfissionalManager(db);

        while(cursor.moveToNext()){

            Historico historico = new Historico();
            historico.setId(cursor.getInt(0));
            historico.setIdCliente(cursor.getInt(1));
            historico.setIdProfissional(cursor.getInt(2));
            historico.setDescricao(cursor.getString(3));
            historico.setDataStr(cursor.getString(4));
            historico.setClienteObj(clienteManager.getClienteById(historico.getIdCliente() + ""));
            historico.setProfissionalObj(profissionalManager.getProfissionalById(historico.getIdProfissional() + ""));
            lista.add(historico);

        }

        cursor.close();

        return lista;

    }

    public Historico getHistoricoById(String id){

        Cursor cursor = db.rawQuery("SELECT * FROM historico WHERE id = ?", new String[]{id});
        ClienteManager clienteManager = new ClienteManager(db);
        ProfissionalManager profissionalManager = new ProfissionalManager(db);

        Historico historico = new Historico();

        if(cursor.moveToNext()){
            historico.setId(cursor.getInt(0));
            historico.setIdCliente(cursor.getInt(1));
            historico.setIdProfissional(cursor.getInt(2));
            historico.setDescricao(cursor.getString(3));
            historico.setDataStr(cursor.getString(4));
            historico.setClienteObj(clienteManager.getClienteById(historico.getIdCliente() + ""));
            historico.setProfissionalObj(profissionalManager.getProfissionalById(historico.getIdProfissional() + ""));
        }

        cursor.close();
        return historico;

    }

    public boolean insertHistorico(Historico historico){

        ContentValues args = new ContentValues();

        args.put("id_cliente", historico.getIdCliente());
        args.put("id_profissional", historico.getIdProfissional());
        args.put("descricao", historico.getDescricao());
        args.put("data", historico.dataExtenso());

        long resultado = db.insert("historico",null, args);

        if(resultado > 0){
            return true;
        }

        return false;
    }

    public boolean updateHistorico(Historico historico){

        ContentValues args = new ContentValues();

        args.put("id", historico.getId());
        args.put("id_cliente", historico.getIdCliente());
        args.put("id_profissional", historico.getIdProfissional());
        args.put("descricao", historico.getDescricao());
        args.put("data", historico.dataExtenso());

        long resultado = db.update("historico", args, "id="+historico.getId(), null);

        if(resultado > 0){
            return true;
        }

        return false;
    }

}
