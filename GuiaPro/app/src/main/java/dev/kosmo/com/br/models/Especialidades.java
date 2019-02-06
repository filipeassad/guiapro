package dev.kosmo.com.br.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class Especialidades {

    @Id
    private Long id;
    private String descricao;
    private long profissionalId;
    private long categoriaId;

    @ToOne(joinProperty = "profissionalId") private Perfil profisisonal;
    @ToOne(joinProperty = "categoriaId") private Categoria categoria;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1498715685)
    private transient EspecialidadesDao myDao;
    @Generated(hash = 211064493)
    public Especialidades(Long id, String descricao, long profissionalId,
            long categoriaId) {
        this.id = id;
        this.descricao = descricao;
        this.profissionalId = profissionalId;
        this.categoriaId = categoriaId;
    }
    @Generated(hash = 1115524198)
    public Especialidades() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescricao() {
        return this.descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public long getProfissionalId() {
        return this.profissionalId;
    }
    public void setProfissionalId(long profissionalId) {
        this.profissionalId = profissionalId;
    }
    public long getCategoriaId() {
        return this.categoriaId;
    }
    public void setCategoriaId(long categoriaId) {
        this.categoriaId = categoriaId;
    }
    @Generated(hash = 2087056803)
    private transient Long profisisonal__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 964488801)
    public Perfil getProfisisonal() {
        long __key = this.profissionalId;
        if (profisisonal__resolvedKey == null
                || !profisisonal__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PerfilDao targetDao = daoSession.getPerfilDao();
            Perfil profisisonalNew = targetDao.load(__key);
            synchronized (this) {
                profisisonal = profisisonalNew;
                profisisonal__resolvedKey = __key;
            }
        }
        return profisisonal;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 471350697)
    public void setProfisisonal(@NotNull Perfil profisisonal) {
        if (profisisonal == null) {
            throw new DaoException(
                    "To-one property 'profissionalId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.profisisonal = profisisonal;
            profissionalId = profisisonal.getId();
            profisisonal__resolvedKey = profissionalId;
        }
    }
    @Generated(hash = 1426606615)
    private transient Long categoria__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2041599367)
    public Categoria getCategoria() {
        long __key = this.categoriaId;
        if (categoria__resolvedKey == null
                || !categoria__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CategoriaDao targetDao = daoSession.getCategoriaDao();
            Categoria categoriaNew = targetDao.load(__key);
            synchronized (this) {
                categoria = categoriaNew;
                categoria__resolvedKey = __key;
            }
        }
        return categoria;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1111731668)
    public void setCategoria(@NotNull Categoria categoria) {
        if (categoria == null) {
            throw new DaoException(
                    "To-one property 'categoriaId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.categoria = categoria;
            categoriaId = categoria.getId();
            categoria__resolvedKey = categoriaId;
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
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1799512105)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getEspecialidadesDao() : null;
    }
}
