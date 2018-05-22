package dev.kosmo.com.br.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

import dev.kosmo.com.br.models.Cliente;
import dev.kosmo.com.br.models.Profissional;

/**
 * Created by Filipe on 08/05/2018.
 */

public class ProfissionalManager {

    private SQLiteDatabase db;

    public ProfissionalManager(SQLiteDatabase db) {
        this.db = db;
    }

    public List<Profissional> getAllProfissional(){

        List<Profissional> lista = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM profissional", new String[]{});
        EnderecoManager enderecoManager = new EnderecoManager(db);

        while(cursor.moveToNext()){

            Profissional profissional = new Profissional();
            profissional.setId(cursor.getInt(0));
            profissional.setIdEndereco(cursor.getInt(1));
            profissional.setNome(cursor.getString(2));
            profissional.setCelular(cursor.getString(3));
            profissional.setEmail(cursor.getString(4));
            profissional.setImg(BitmapFactory.decodeByteArray(cursor.getBlob(5), 0 ,cursor.getBlob(5).length));
            profissional.setUrlImg(cursor.getString(6));
            profissional.setEnderecoObj(enderecoManager.getEnderecoById(profissional.getIdEndereco()+""));

            profissional.setDescricao1("");
            profissional.setDescricao2("");
            lista.add(profissional);

        }

        cursor.close();

        return lista;

    }

    public Profissional getProfissionalById(String id){

        Cursor cursor = db.rawQuery("SELECT * FROM profissional WHERE id = ?", new String[]{id});
        EnderecoManager enderecoManager = new EnderecoManager(db);
        Profissional profissional = null;

        if(cursor.moveToNext()){
            profissional = new Profissional();
            profissional.setId(cursor.getInt(0));
            profissional.setIdEndereco(cursor.getInt(1));
            profissional.setNome(cursor.getString(2));
            profissional.setCelular(cursor.getString(3));
            profissional.setEmail(cursor.getString(4));
            profissional.setImg(BitmapFactory.decodeByteArray(cursor.getBlob(5), 0 ,cursor.getBlob(5).length));
            profissional.setUrlImg(cursor.getString(6));
            profissional.setEnderecoObj(enderecoManager.getEnderecoById(profissional.getIdEndereco()+""));

            profissional.setDescricao1("");
            profissional.setDescricao2("");
        }

        cursor.close();

        return profissional;

    }

    public boolean insertProfissional(Profissional profissional){

        ContentValues args = new ContentValues();

        args.put("id", profissional.getId());
        args.put("id_endereco", profissional.getIdEndereco());
        args.put("nome", profissional.getNome());
        args.put("celular", profissional.getCelular());
        args.put("email", profissional.getEmail());
        args.put("img", profissional.imgBlob());
        args.put("url_img", profissional.getUrlImg());

        long resultado = db.insert("profissional",null, args);

        if(resultado > 0){
            return true;
        }

        return false;
    }

    public boolean updateProfissional(Profissional profissional){

        ContentValues args = new ContentValues();

        args.put("id", profissional.getId());
        args.put("id_endereco", profissional.getIdEndereco());
        args.put("nome", profissional.getNome());
        args.put("celular", profissional.getCelular());
        args.put("email", profissional.getEmail());
        args.put("img", profissional.imgBlob());
        args.put("url_img", profissional.getUrlImg());

        long resultado = db.update("profissional", args, "id="+profissional.getId(), null);

        if(resultado > 0){
            return true;
        }

        return false;
    }
}
