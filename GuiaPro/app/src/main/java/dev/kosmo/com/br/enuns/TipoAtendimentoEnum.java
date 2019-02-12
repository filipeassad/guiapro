package dev.kosmo.com.br.enuns;

public enum TipoAtendimentoEnum {
    LIGACAO(1),
    WHATSAPP(2),
    MELIGUE(3);

    private final int id;
    TipoAtendimentoEnum(int id) { this.id = id; }
    public int getValue() { return id; }
}
