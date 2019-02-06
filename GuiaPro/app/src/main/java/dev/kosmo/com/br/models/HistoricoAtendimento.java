package dev.kosmo.com.br.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class HistoricoAtendimento {
    @Id
    private Long id;
    private Date data;
    private String titulo;
    private String descricao;
    private long atendimentoId;
    private long clienteId;
    private long profissionalId;
    private long categoriaId;
    private long situcaoId;
    private long tipoAtendimentoId;

    @ToOne(joinProperty = "atendimentoId") private Atendimento atendimento;
    @ToOne(joinProperty = "clienteId") private Perfil cliente;
    @ToOne(joinProperty = "profissionalId") private Perfil profisisonal;
    @ToOne(joinProperty = "categoriaId") private Categoria categoria;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1929390857)
    private transient HistoricoAtendimentoDao myDao;
    @Generated(hash = 340124019)
    public HistoricoAtendimento(Long id, Date data, String titulo, String descricao,
            long atendimentoId, long clienteId, long profissionalId,
            long categoriaId, long situcaoId, long tipoAtendimentoId) {
        this.id = id;
        this.data = data;
        this.titulo = titulo;
        this.descricao = descricao;
        this.atendimentoId = atendimentoId;
        this.clienteId = clienteId;
        this.profissionalId = profissionalId;
        this.categoriaId = categoriaId;
        this.situcaoId = situcaoId;
        this.tipoAtendimentoId = tipoAtendimentoId;
    }
    @Generated(hash = 1298231894)
    public HistoricoAtendimento() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Date getData() {
        return this.data;
    }
    public void setData(Date data) {
        this.data = data;
    }
    public String getTitulo() {
        return this.titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescricao() {
        return this.descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public long getAtendimentoId() {
        return this.atendimentoId;
    }
    public void setAtendimentoId(long atendimentoId) {
        this.atendimentoId = atendimentoId;
    }
    public long getClienteId() {
        return this.clienteId;
    }
    public void setClienteId(long clienteId) {
        this.clienteId = clienteId;
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
    public long getSitucaoId() {
        return this.situcaoId;
    }
    public void setSitucaoId(long situcaoId) {
        this.situcaoId = situcaoId;
    }
    public long getTipoAtendimentoId() {
        return this.tipoAtendimentoId;
    }
    public void setTipoAtendimentoId(long tipoAtendimentoId) {
        this.tipoAtendimentoId = tipoAtendimentoId;
    }
    @Generated(hash = 780597081)
    private transient Long atendimento__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 784754402)
    public Atendimento getAtendimento() {
        long __key = this.atendimentoId;
        if (atendimento__resolvedKey == null
                || !atendimento__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AtendimentoDao targetDao = daoSession.getAtendimentoDao();
            Atendimento atendimentoNew = targetDao.load(__key);
            synchronized (this) {
                atendimento = atendimentoNew;
                atendimento__resolvedKey = __key;
            }
        }
        return atendimento;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1600722148)
    public void setAtendimento(@NotNull Atendimento atendimento) {
        if (atendimento == null) {
            throw new DaoException(
                    "To-one property 'atendimentoId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.atendimento = atendimento;
            atendimentoId = atendimento.getId();
            atendimento__resolvedKey = atendimentoId;
        }
    }
    @Generated(hash = 1668724671)
    private transient Long cliente__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 251604525)
    public Perfil getCliente() {
        long __key = this.clienteId;
        if (cliente__resolvedKey == null || !cliente__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PerfilDao targetDao = daoSession.getPerfilDao();
            Perfil clienteNew = targetDao.load(__key);
            synchronized (this) {
                cliente = clienteNew;
                cliente__resolvedKey = __key;
            }
        }
        return cliente;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1650853875)
    public void setCliente(@NotNull Perfil cliente) {
        if (cliente == null) {
            throw new DaoException(
                    "To-one property 'clienteId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.cliente = cliente;
            clienteId = cliente.getId();
            cliente__resolvedKey = clienteId;
        }
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
    @Generated(hash = 1128366586)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getHistoricoAtendimentoDao() : null;
    }
}
