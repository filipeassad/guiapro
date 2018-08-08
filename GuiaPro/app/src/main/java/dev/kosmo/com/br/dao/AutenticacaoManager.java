package dev.kosmo.com.br.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dev.kosmo.com.br.models.Autenticacao;

public class AutenticacaoManager {

    private SQLiteDatabase db;

    public AutenticacaoManager(SQLiteDatabase db) {
        this.db = db;
    }


    public List<Autenticacao> getAutenticacao(){

        Cursor cursor = db.rawQuery("SELECT * FROM autenticacao",null);

        if(cursor != null){

            List<Autenticacao> lista = new ArrayList<>();

            while (cursor.moveToNext()){

                Autenticacao autenticacao = new Autenticacao();

                autenticacao.setId(cursor.getInt(0));
                autenticacao.setHash(cursor.getString(1));
                autenticacao.setUsuario(cursor.getString(2));
                autenticacao.setAtivo(cursor.getString(3).equals("true"));
                autenticacao.setUserid(cursor.getInt(4));

                lista.add(autenticacao);

            }

            cursor.close();
            cursor = null;
            return lista;
        }

        return null;
    }

    public List<Autenticacao> getAutenticacaoAtivo(){

        Cursor cursor = db.rawQuery("SELECT * FROM autenticacao WHERE ativo = 'true'",null);

        if(cursor != null){

            List<Autenticacao> lista = new ArrayList<>();

            while (cursor.moveToNext()){

                Autenticacao autenticacao = new Autenticacao();

                autenticacao.setId(cursor.getInt(0));
                autenticacao.setHash(cursor.getString(1));
                autenticacao.setUsuario(cursor.getString(2));
                autenticacao.setAtivo(cursor.getString(3).equals("true"));
                autenticacao.setUserid(cursor.getInt(4));

                lista.add(autenticacao);

            }

            cursor.close();
            cursor = null;
            return lista;
        }

        return null;
    }

    public List<Autenticacao> getAutenticacaobyUsuario(String usuario){

        Cursor cursor = db.rawQuery("SELECT * FROM autenticacao WHERE usuario = ?", new String[]{usuario});

        if(cursor != null){

            List<Autenticacao> lista = new ArrayList<>();

            while (cursor.moveToNext()){

                Autenticacao autenticacao = new Autenticacao();

                autenticacao.setId(cursor.getInt(0));
                autenticacao.setHash(cursor.getString(1));
                autenticacao.setUsuario(cursor.getString(2));
                autenticacao.setAtivo(cursor.getString(3).equals("true"));
                autenticacao.setUserid(cursor.getInt(4));

                lista.add(autenticacao);

            }

            cursor.close();
            cursor = null;
            return lista;
        }

        return null;
    }

    public boolean insertAutenticacao(Autenticacao autenticacao){

        ContentValues args = new ContentValues();

        args.put("id", autenticacao.getId());
        args.put("hash", autenticacao.getHash());
        args.put("usuario", autenticacao.getUsuario());
        args.put("ativo", autenticacao.isAtivo() ? "true":"false");
        args.put("userid", autenticacao.getUserid());


        long resultado = db.insert("autenticacao",null, args);

        if(resultado > 0){
            return true;
        }

        return false;
    }

    public boolean updateAutenticacao(Autenticacao autenticacao){

        ContentValues args = new ContentValues();

        args.put("id", autenticacao.getId());
        args.put("hash", autenticacao.getHash());
        args.put("usuario", autenticacao.getUsuario());
        args.put("ativo", autenticacao.isAtivo() ? "true":"false");
        args.put("userid", autenticacao.getUserid());

        long resultado = db.update("autenticacao", args, "id=" + autenticacao.getId(), null);

        if(resultado > 0){
            return true;
        }

        return false;
    }

}
