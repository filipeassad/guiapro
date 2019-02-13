package dev.kosmo.com.br.enuns;

public enum TipoPerfilEnum {
    CLIENTE(1),
    PROFISSIONAL(2),
    NEUTRO(3);

    private final int id;
    TipoPerfilEnum(int id) { this.id = id; }
    public int getValue() { return id; }
}
