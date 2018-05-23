package dev.kosmo.com.br.utils;

public class ConversaoTexto {

    public String getTextoNomeLaranja(String texto, String nome){

        String resultado = "";

        if(texto.length() > 16 && texto.substring(0,16).equals("Você ligou para ")){
            resultado = "Você ligou para <font color='#e9a11c'>"+ nome +"</font>.";
        }else if(texto.length() > 42 && texto.substring(0, 42).equals("Você enviou uma mensagem no whatsapp para ")){
            resultado = "Você enviou uma mensagem no whatsapp para <font color='#e9a11c'>"+ nome +"</font>.";
        }else if(texto.length() > 27 && texto.substring(0, 27).equals("Você solicitou contato com ")){
            resultado = "Você solicitou contato com <font color='#e9a11c'>"+ nome +"</font>.";
        }

        return resultado;
    }

}
