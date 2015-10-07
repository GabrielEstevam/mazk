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
public class Tentativa {

    private Integer idTentativa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation

    private Double desempenho;

    private Date data;

    private Integer tempoTotal;
    private List<Resposta> respostaList;
    private Usuario usuario;

    public Integer getIdTentativa() {
        return idTentativa;
    }

    public void setIdTentativa(Integer idTentativa) {
        this.idTentativa = idTentativa;
    }

    public Double getDesempenho() {
        return desempenho;
    }

    public void setDesempenho(Double desempenho) {
        this.desempenho = desempenho;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getTempoTotal() {
        return tempoTotal;
    }

    public void setTempoTotal(Integer tempoTotal) {
        this.tempoTotal = tempoTotal;
    }

    public List<Resposta> getRespostaList() {
        return respostaList;
    }

    public void setRespostaList(List<Resposta> respostaList) {
        this.respostaList = respostaList;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
