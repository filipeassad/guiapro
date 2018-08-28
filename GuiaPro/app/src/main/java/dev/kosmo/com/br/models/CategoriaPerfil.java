package dev.kosmo.com.br.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class CategoriaPerfil {

    @Id(autoincrement = true) private Long id;
    private Long perfilId;
    private Long categoriaId;

    public CategoriaPerfil() {
    }

    @Generated(hash = 2111552388)
    public CategoriaPerfil(Long id, Long perfilId, Long categoriaId) {
        this.id = id;
        this.perfilId = perfilId;
        this.categoriaId = categoriaId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Long perfilId) {
        this.perfilId = perfilId;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}
