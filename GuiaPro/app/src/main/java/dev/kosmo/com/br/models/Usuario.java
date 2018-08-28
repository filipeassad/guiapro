package dev.kosmo.com.br.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class Usuario {

    @Id(autoincrement = true) private Long id;
    private String email;
    private String token;
    private long perfilId;

    @ToOne(joinProperty = "perfilId") private Perfil perfil;
    @Transient private String senha;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1305250862)
    private transient UsuarioDao myDao;
    @Generated(hash = 1931986542)
    private transient Long perfil__resolvedKey;

    public Usuario() {
    }

    @Generated(hash = 1416295381)
    public Usuario(Long id, String email, String token, long perfilId) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.perfilId = perfilId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getPerfilId() {
        return this.perfilId;
    }

    public void setPerfilId(long perfilId) {
        this.perfilId = perfilId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 712794651)
    public Perfil getPerfil() {
        long __key = this.perfilId;
        if (perfil__resolvedKey == null || !perfil__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PerfilDao targetDao = daoSession.getPerfilDao();
            Perfil perfilNew = targetDao.load(__key);
            synchronized (this) {
                perfil = perfilNew;
                perfil__resolvedKey = __key;
            }
        }
        return perfil;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 246410771)
    public void setPerfil(@NotNull Perfil perfil) {
        if (perfil == null) {
            throw new DaoException(
                    "To-one property 'perfilId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.perfil = perfil;
            perfilId = perfil.getId();
            perfil__resolvedKey = perfilId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1312617472)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUsuarioDao() : null;
    }
}
