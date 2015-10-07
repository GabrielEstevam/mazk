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
public class Pergunta {

    private byte[] explicacao;
    private Integer idPergunta;
    private String enunciado;

    private Boolean ativo;

    private Double dificuldade;

    private double tempoMedio;

    private Date dataInserida;
    private List<Area> areaList;
    private List<Alternativa> alternativaList;
    private List<Resposta> respostaList;
    private List<Exemplo> exemploList;

    public byte[] getExplicacao() {
        return explicacao;
    }

    public void setExplicacao(byte[] explicacao) {
        this.explicacao = explicacao;
    }

    public Integer getIdPergunta() {
        return idPergunta;
    }

    public void setIdPergunta(Integer idPergunta) {
        this.idPergunta = idPergunta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Double getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(Double dificuldade) {
        this.dificuldade = dificuldade;
    }

    public double getTempoMedio() {
        return tempoMedio;
    }

    public void setTempoMedio(double tempoMedio) {
        this.tempoMedio = tempoMedio;
    }

    public Date getDataInserida() {
        return dataInserida;
    }

    public void setDataInserida(Date dataInserida) {
        this.dataInserida = dataInserida;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }

    public List<Alternativa> getAlternativaList() {
        return alternativaList;
    }

    public void setAlternativaList(List<Alternativa> alternativaList) {
        this.alternativaList = alternativaList;
    }

    public List<Resposta> getRespostaList() {
        return respostaList;
    }

    public void setRespostaList(List<Resposta> respostaList) {
        this.respostaList = respostaList;
    }

    public List<Exemplo> getExemploList() {
        return exemploList;
    }

    public void setExemploList(List<Exemplo> exemploList) {
        this.exemploList = exemploList;
    }
}
