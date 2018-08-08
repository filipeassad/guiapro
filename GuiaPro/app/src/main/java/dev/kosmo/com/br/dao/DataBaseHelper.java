package dev.kosmo.com.br.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper{

    private static final String DB_NANEM = "guiaprodb";
    private static final int VERSAO = 1;
    private final Context myContext;

    public DataBaseHelper(Context context) {
        super(context, DB_NANEM, null, VERSAO);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Tabela historico
        db.execSQL("CREATE TABLE historico(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "id_cliente INTEGER," +
                "id_profissional INTEGER," +
                "descricao TEXT," +
                "data TEXT" +
                ");");

        //Tabela cliente
        db.execSQL("CREATE TABLE cliente(" +
                "id INTEGER PRIMARY KEY ," +
                "id_endereco INTEGER," +
                "nome TEXT," +
                "celular TEXT," +
                "email TEXT," +
                "img BLOB" +
                ");");

        //Tabela profissional
        db.execSQL("CREATE TABLE profissional(" +
                "id INTEGER PRIMARY KEY ," +
                "id_endereco INTEGER," +
                "nome TEXT," +
                "celular TEXT," +
                "email TEXT," +
                "img BLOB," +
                "url_img TEXT"+
                ");");

        //Tabela profissional
        db.execSQL("CREATE TABLE endereco(" +
                "id INTEGER PRIMARY KEY ," +
                "logradouro TEXT," +
                "numero TEXT," +
                "bairro TEXT," +
                "cidade TEXT," +
                "estado TEXT," +
                "pais TEXT,"+
                "complemento TEXT,"+
                "latitude TEXT,"+
                "longitude TEXT"+
                ");");

        //Tabela Especialidade
        db.execSQL("CREATE TABLE especialidade(" +
                "id INTEGER PRIMARY KEY ," +
                "nome TEXT," +
                "imagem BLOB," +
                "descricao TEXT" +
                ");");

        //Tabela EspecialidadeXProfissional
        db.execSQL("CREATE TABLE espcialidadeprofissional(" +
                "id_especialidade INTEGER," +
                "id_profissional INTEGER" +
                ");");

        db.execSQL("CREATE TABLE autenticacao (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "hash TEXT, " +
                "usuario TEXT, " +
                "ativo TEXT," +
                "userid INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
