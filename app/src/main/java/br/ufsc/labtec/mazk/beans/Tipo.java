package br.ufsc.labtec.mazk.beans;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import java.util.List;

/**
 * Created by Mihael Zamin on 25/03/2015.
 */
@JsonIdentityInfo(generator = JSOGGenerator.class)
//@JsonIgnoreProperties({"@ref"})
public class Tipo {

    private Integer idTipo;

    private String nome;

    private List<Usuario> usuarioList;

    public Tipo(String nome, Integer idTipo) {
        this.nome = nome;
        this.idTipo = idTipo;
    }

    public Tipo() {
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tipo)) return false;

        Tipo tipo = (Tipo) o;

        if (idTipo != null ? !idTipo.equals(tipo.idTipo) : tipo.idTipo != null) return false;
        return !(nome != null ? !nome.equals(tipo.nome) : tipo.nome != null);

    }

    @Override
    public int hashCode() {
        int result = idTipo != null ? idTipo.hashCode() : 0;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        return result;
    }
}
