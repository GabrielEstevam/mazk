package br.ufsc.labtec.mazk.beans;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import java.util.Date;
import java.util.List;

/**
 * Created by Mihael Zamin on 25/03/2015.
 */
@JsonIdentityInfo(generator = JSOGGenerator.class)
//@JsonIgnoreProperties({"@ref"})
public class Usuario {

    private Integer idUsuario;

    private String nome;

    private String email;

    private String senha;

    private Double experiencia;

    private Date dataDeCadastro;
    private byte[] avatar;
    private Date dataDeNascimento;
    private List<Acesso> acessoList;
    private Tipo tipo;
    private List<Tentativa> tentativaList;

    public Usuario() {

    }

    public Usuario(String nome, String email, String senha, Date dataDeNascimento) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataDeNascimento = dataDeNascimento;

    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Double getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Double experiencia) {
        this.experiencia = experiencia;
    }

    public Date getDataDeCadastro() {
        return dataDeCadastro;
    }

    public void setDataDeCadastro(Date dataDeCadastro) {
        this.dataDeCadastro = dataDeCadastro;
    }

    public List<Acesso> getAcessoList() {
        return acessoList;
    }

    public void setAcessoList(List<Acesso> acessoList) {
        this.acessoList = acessoList;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public List<Tentativa> getTentativaList() {
        return tentativaList;
    }

    public void setTentativaList(List<Tentativa> tentativaList) {
        this.tentativaList = tentativaList;
    }

    public Date getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(Date dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
