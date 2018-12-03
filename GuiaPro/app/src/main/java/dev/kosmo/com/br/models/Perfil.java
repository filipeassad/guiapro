package dev.kosmo.com.br.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;

@Entity
public class Perfil {

    @Id private Long id;
    private String nome;
    private String sobrenome;
    private Date dataNascimento;
    private String cpf;
    private String sexo;
    private String celular;
    private String urlImg;

    private long enderecoId;
    private long tipoPerfilId;

    @ToOne(joinProperty = "enderecoId") private Endereco endereco;
    @ToOne(joinProperty = "tipoPerfilId") private TipoPerfil tipoPerfil;

    @ToMany
    @JoinEntity(
            entity = CategoriaPerfil.class,
            sourceProperty = "perfilId",
            targetProperty = "categoriaId"
    )
    private List<Categoria> categorias;
    @Transient
    private List<Categoria> categoriasInsercao;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1371249193)
    private transient PerfilDao myDao;
    @Generated(hash = 1241562132)
    private transient Long endereco__resolvedKey;
    @Generated(hash = 13958810)
    private transient Long tipoPerfil__resolvedKey;

    public Perfil() {
    }

    @Generated(hash = 664846739)
    public Perfil(Long id, String nome, String sobrenome, Date dataNascimento, String cpf,
            String sexo, String celular, String urlImg, long enderecoId, long tipoPerfilId) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.sexo = sexo;
        this.celular = celular;
        this.urlImg = urlImg;
        this.enderecoId = enderecoId;
        this.tipoPerfilId = tipoPerfilId;
    }

    public void setDataNascimentoByString(String valor){
        if(valor != null && (valor.trim().equals("")!= true) && (valor.trim().equals("null")!= true)){
            try {
                //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                dataNascimento = new Date(simpleDateFormat.parse(valor).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return this.sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public Date getDataNascimento() {
        return this.dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSexo() {
        return this.sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCelular() {
        return this.celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getUrlImg() {
        return this.urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public long getEnderecoId() {
        return this.enderecoId;
    }

    public void setEnderecoId(long enderecoId) {
        this.enderecoId = enderecoId;
    }

    public long getTipoPerfilId() {
        return this.tipoPerfilId;
    }

    public void setTipoPerfilId(long tipoPerfilId) {
        this.tipoPerfilId = tipoPerfilId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 786043367)
    public Endereco getEndereco() {
        long __key = this.enderecoId;
        if (endereco__resolvedKey == null || !endereco__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            EnderecoDao targetDao = daoSession.getEnderecoDao();
            Endereco enderecoNew = targetDao.load(__key);
            synchronized (this) {
                endereco = enderecoNew;
                endereco__resolvedKey = __key;
            }
        }
        return endereco;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 700760219)
    public void setEndereco(@NotNull Endereco endereco) {
        if (endereco == null) {
            throw new DaoException(
                    "To-one property 'enderecoId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.endereco = endereco;
            enderecoId = endereco.getId();
            endereco__resolvedKey = enderecoId;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 419880910)
    public TipoPerfil getTipoPerfil() {
        long __key = this.tipoPerfilId;
        if (tipoPerfil__resolvedKey == null || !tipoPerfil__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TipoPerfilDao targetDao = daoSession.getTipoPerfilDao();
            TipoPerfil tipoPerfilNew = targetDao.load(__key);
            synchronized (this) {
                tipoPerfil = tipoPerfilNew;
                tipoPerfil__resolvedKey = __key;
            }
        }
        return tipoPerfil;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 137483327)
    public void setTipoPerfil(@NotNull TipoPerfil tipoPerfil) {
        if (tipoPerfil == null) {
            throw new DaoException(
                    "To-one property 'tipoPerfilId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.tipoPerfil = tipoPerfil;
            tipoPerfilId = tipoPerfil.getId();
            tipoPerfil__resolvedKey = tipoPerfilId;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1951802529)
    public List<Categoria> getCategorias() {
        if (categorias == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CategoriaDao targetDao = daoSession.getCategoriaDao();
            List<Categoria> categoriasNew = targetDao._queryPerfil_Categorias(id);
            synchronized (this) {
                if (categorias == null) {
                    categorias = categoriasNew;
                }
            }
        }
        return categorias;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1090732649)
    public synchronized void resetCategorias() {
        categorias = null;
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

    public List<Categoria> getCategoriasInsercao() {
        return categoriasInsercao;
    }

    public void setCategoriasInsercao(List<Categoria> categoriasInsercao) {
        this.categoriasInsercao = categoriasInsercao;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 684696215)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPerfilDao() : null;
    }
}
