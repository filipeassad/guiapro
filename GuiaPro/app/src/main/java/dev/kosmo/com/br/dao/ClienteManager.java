package dev.kosmo.com.br.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

import dev.kosmo.com.br.models.Cliente;

/**
 * Created by Filipe on 08/05/2018.
 */

public class ClienteManager {

    private SQLiteDatabase db;

    public ClienteManager(SQLiteDatabase db) {
        this.db = db;
    }

    public List<Cliente> getAllCliente(){

        List<Cliente> lista = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM cliente", new String[]{});
        EnderecoManager enderecoManager = new EnderecoManager(db);

        while(cursor.moveToNext()){

            Cliente cliente = new Cliente();
            cliente.setId(cursor.getInt(0));
            cliente.setIdEndereco(cursor.getInt(1));
            cliente.setNome(cursor.getString(2));
            cliente.setCelular(cursor.getString(3));
            cliente.setEmail(cursor.getString(4));
            cliente.setImg(BitmapFactory.decodeByteArray(cursor.getBlob(5), 0 ,cursor.getBlob(5).length));
            cliente.setEnderecoObj(enderecoManager.getEnderecoById(cliente.getIdEndereco()+""));
            lista.add(cliente);

        }

        cursor.close();

        return lista;

    }

    public Cliente getClienteById(String id){

        Cursor cursor = db.rawQuery("SELECT * FROM cliente WHERE id = ?", new String[]{id});
        EnderecoManager enderecoManager = new EnderecoManager(db);
        Cliente cliente = new Cliente();

        if(cursor.moveToNext()){
            cliente.setId(cursor.getInt(0));
            cliente.setIdEndereco(cursor.getInt(1));
            cliente.setNome(cursor.getString(2));
            cliente.setCelular(cursor.getString(3));
            cliente.setEmail(cursor.getString(4));
            cliente.setImg(BitmapFactory.decodeByteArray(cursor.getBlob(5), 0 ,cursor.getBlob(5).length));
            cliente.setEnderecoObj(enderecoManager.getEnderecoById(cliente.getIdEndereco()+""));
        }

        cursor.close();

        return cliente;

    }

    public boolean insertCliente(Cliente cliente){

        ContentValues args = new ContentValues();

        args.put("id", cliente.getId());
        args.put("id_endereco", cliente.getIdEndereco());
        args.put("nome", cliente.getNome());
        args.put("celular", cliente.getCelular());
        args.put("email", cliente.getEmail());
        args.put("img", cliente.imgBlob());

        long resultado = db.insert("cliente",null, args);

        if(resultado > 0){
            return true;
        }

        return false;
    }

    public boolean updateCliente(Cliente cliente){

        ContentValues args = new ContentValues();

        args.put("id", cliente.getId());
        args.put("id_endereco", cliente.getIdEndereco());
        args.put("nome", cliente.getNome());
        args.put("celular", cliente.getCelular());
        args.put("email", cliente.getEmail());
        args.put("img", cliente.imgBlob());

        long resultado = db.update("cliente", args, "id="+cliente.getId(), null);

        if(resultado > 0){
            return true;
        }

        return false;
    }
}
