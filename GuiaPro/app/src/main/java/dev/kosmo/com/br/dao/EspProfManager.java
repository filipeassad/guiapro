package dev.kosmo.com.br.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dev.kosmo.com.br.models.Especialidades;
import dev.kosmo.com.br.models.Profissional;

public class EspProfManager {

    private SQLiteDatabase db;

    public EspProfManager(SQLiteDatabase db) {
        this.db = db;
    }

    public List<Profissional> getProfissionaisByEspecialidades(String id){

        List<Profissional> lista = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM espcialidadeprofissional WHERE id_especialidade = ?", new String[]{id});

        ProfissionalManager profissionalManager = new ProfissionalManager(db);


        while(cursor.moveToNext()){
            Profissional profissional = profissionalManager.getProfissionalById(cursor.getInt(1) + "");
            lista.add(profissional);
        }

        cursor.close();

        return lista;

    }


    public List<Especialidades> getEspecialidadesByProfissional(String id){

        List<Especialidades> lista = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM espcialidadeprofissional WHERE id_profissional = ?", new String[]{id});

        EspecialidadeManager especialidadeManager = new EspecialidadeManager(db);

        while(cursor.moveToNext()){

            Especialidades especialidades = especialidadeManager.getEspecialidadesByID(cursor.getInt(0) + "");
            lista.add(especialidades);
        }

        cursor.close();

        return lista;

    }

    public boolean verificaExistencia(String id_Especialidade, String id_Profissional){

        Cursor cursor = db.rawQuery("SELECT * FROM espcialidadeprofissional WHERE id_especialidade = ? AND id_profissional = ?", new String[]{id_Especialidade, id_Profissional});

        if(cursor.moveToFirst())
            return true;

        return false;

    }

    public boolean insertEspProf(Integer id_Especialidade, Integer id_Profissional){

        ContentValues args = new ContentValues();

        args.put("id_especialidade", id_Especialidade);
        args.put("id_profissional",id_Profissional);

        long resultado = db.insert("espcialidadeprofissional",null, args);

        if(resultado > 0){
            return true;
        }

        return false;

    }


}
