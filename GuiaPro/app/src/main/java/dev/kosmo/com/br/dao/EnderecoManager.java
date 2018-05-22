package dev.kosmo.com.br.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dev.kosmo.com.br.models.Endereco;

/**
 * Created by Filipe on 08/05/2018.
 */

public class EnderecoManager {

    private SQLiteDatabase db;

    public EnderecoManager(SQLiteDatabase db) {
        this.db = db;
    }

    public List<Endereco> getAllEndereco(){

        List<Endereco> lista = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM endereco", new String[]{});

        while(cursor.moveToNext()){

            Endereco endereco = new Endereco();
            endereco.setId(cursor.getInt(0));
            endereco.setLogradouro(cursor.getString(1));
            endereco.setNumero(cursor.getString(2));
            endereco.setBairro(cursor.getString(3));
            endereco.setCidade(cursor.getString(4));
            endereco.setEstado(cursor.getString(5));
            endereco.setPais(cursor.getString(6));
            endereco.setComplemento(cursor.getString(7));
            endereco.setLatitude(cursor.getString(8));
            endereco.setLongitude(cursor.getString(9));

            lista.add(endereco);

        }

        cursor.close();

        return lista;

    }

    public Endereco getEnderecoById(String id){

        Cursor cursor = db.rawQuery("SELECT * FROM endereco WHERE id = ?", new String[]{id});
        Endereco endereco = new Endereco();

        if(cursor.moveToNext()){

            endereco.setId(cursor.getInt(0));
            endereco.setLogradouro(cursor.getString(1));
            endereco.setNumero(cursor.getString(2));
            endereco.setBairro(cursor.getString(3));
            endereco.setCidade(cursor.getString(4));
            endereco.setEstado(cursor.getString(5));
            endereco.setPais(cursor.getString(6));
            endereco.setComplemento(cursor.getString(7));
            endereco.setLatitude(cursor.getString(8));
            endereco.setLongitude(cursor.getString(9));

        }

        cursor.close();

        return endereco;
    }

    public boolean insertEndereco(Endereco endereco){

        ContentValues args = new ContentValues();

        args.put("id", endereco.getId());
        args.put("logradouro", endereco.getLogradouro());
        args.put("numero", endereco.getNumero());
        args.put("bairro", endereco.getBairro());
        args.put("cidade", endereco.getCidade());
        args.put("estado", endereco.getEstado());
        args.put("pais", endereco.getPais());
        args.put("complemento", endereco.getComplemento());
        args.put("latitude", endereco.getLatitude());
        args.put("longitude", endereco.getLongitude());

        long resultado = db.insert("endereco",null, args);

        if(resultado > 0){
            return true;
        }

        return false;
    }

    public boolean updateEndereco(Endereco endereco){

        ContentValues args = new ContentValues();

        args.put("id", endereco.getId());
        args.put("logradouro", endereco.getLogradouro());
        args.put("numero", endereco.getNumero());
        args.put("bairro", endereco.getBairro());
        args.put("cidade", endereco.getCidade());
        args.put("estado", endereco.getEstado());
        args.put("pais", endereco.getPais());
        args.put("complemento", endereco.getComplemento());
        args.put("latitude", endereco.getLatitude());
        args.put("longitude", endereco.getLongitude());

        long resultado = db.update("endereco", args, "id="+endereco.getId(), null);

        if(resultado > 0){
            return true;
        }

        return false;
    }

    /*public boolean insertCliente(Cliente cliente){

        ContentValues args = new ContentValues();

        args.put("id", cliente.getId());
        args.put("created", FerramentasBasicas.DatePraStr(cliente.getCreated()));
        args.put("modified", FerramentasBasicas.DatePraStr(cliente.getModified()));
        args.put("nome", cliente.getNome());
        args.put("fone1", cliente.getFone1());
        args.put("fone2", cliente.getFone2());
        args.put("fone_cel1", cliente.getFone_cel1());
        args.put("fone_cel2", cliente.getFone_cel2());
        args.put("email_principal", cliente.getEmail_principal());
        args.put("email_secundario", cliente.getEmail_secundario());
        args.put("logradouro", cliente.getLogradouro());
        args.put("numero", cliente.getNumero());
        args.put("complemento", cliente.getComplemento());
        args.put("cep", cliente.getCep());
        args.put("latitude", cliente.getLatitude());
        args.put("longitude", cliente.getLongitude());
        args.put("codigo_cliente", cliente.getCodigo_cliente());
        args.put("data_criacao", FerramentasBasicas.DatePraStr(cliente.getData_criacao()));
        args.put("ativo", cliente.isAtivo() ? "true":"false" );
        args.put("cidade", cliente.getCidade());

        long resultado = db.insert("CLIENTE",null, args);

        if(resultado > 0){
            return true;
        }

        return false;
    }*/
}
