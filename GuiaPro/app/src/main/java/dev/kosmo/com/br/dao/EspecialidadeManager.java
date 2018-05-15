package dev.kosmo.com.br.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

import dev.kosmo.com.br.models.Especialidades;

public class EspecialidadeManager {

    private SQLiteDatabase db;

    public EspecialidadeManager(SQLiteDatabase db) {
        this.db = db;
    }

    public List<Especialidades> getAllEspecialidades(){

        List<Especialidades> lista = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM especialidade", new String[]{});

        while(cursor.moveToNext()){

            Especialidades especialidades = new Especialidades();

            especialidades.setId(cursor.getInt(0));
            especialidades.setNome(cursor.getString(1));
            especialidades.setImagem(BitmapFactory.decodeByteArray(cursor.getBlob(2), 0 ,cursor.getBlob(2).length));
            especialidades.setDescricao(cursor.getString(3));

            lista.add(especialidades);
        }

        cursor.close();

        return lista;

    }

    public Especialidades getEspecialidadesByID(String id){

        Especialidades especialidades = new Especialidades();

        Cursor cursor = db.rawQuery("SELECT * FROM especialidade WHERE id = ?", new String[]{id});

        if(cursor.moveToNext()){

            especialidades.setId(cursor.getInt(0));
            especialidades.setNome(cursor.getString(1));
            especialidades.setImagem(BitmapFactory.decodeByteArray(cursor.getBlob(2), 0 ,cursor.getBlob(2).length));
            especialidades.setDescricao(cursor.getString(3));

        }

        return especialidades;
    }

    public boolean insertEspecialidade(Especialidades especialidades){

        ContentValues args = new ContentValues();

        args.put("nome", especialidades.getNome());
        args.put("imagem", especialidades.imgBlob());
        args.put("descricao", especialidades.getDescricao());

        long resultado = db.insert("especialidade",null, args);

        if(resultado > 0){
            return true;
        }

        return false;

    }

    public boolean updateEspecialidade(Especialidades especialidades){

        ContentValues args = new ContentValues();

        args.put("id", especialidades.getId());
        args.put("nome", especialidades.getNome());
        args.put("imagem", especialidades.imgBlob());
        args.put("descricao", especialidades.getDescricao());

        long resultado = db.update("especialidade", args, "id="+especialidades.getId(), null);

        if(resultado > 0){
            return true;
        }

        return false;
    }
}
