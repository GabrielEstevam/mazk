package br.ufsc.labtec.mazk.beans;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.gson.annotations.SerializedName;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Mihael Zamin on 25/03/2015.
 */
@JsonIdentityInfo(generator=JSOGGenerator.class)
//@JsonIgnoreProperties({"@ref"})
public class Resposta {

    private Integer idResposta;
    
    private Date data;
    
    private int tempoDecorrido;
    private Alternativa alternativa;
    private Pergunta pergunta;
    private Tentativa tentativa;

    public Integer getIdResposta() {
        return idResposta;
    }

    public void setIdResposta(Integer idResposta) {
        this.idResposta = idResposta;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getTempoDecorrido() {
        return tempoDecorrido;
    }

    public void setTempoDecorrido(int tempoDecorrido) {
        this.tempoDecorrido = tempoDecorrido;
    }

    public Alternativa getAlternativa() {
        return alternativa;
    }

    public void setAlternativa(Alternativa alternativa) {
        this.alternativa = alternativa;
        if(alternativa.getRespostaList() == null)
            alternativa.setRespostaList(new ArrayList<Resposta>());
        if(alternativa.getRespostaList().indexOf(this) == -1)
        alternativa.getRespostaList().add(this);
    }

    public Pergunta getPergunta() {
        return pergunta;
    }

    public void setPergunta(Pergunta pergunta) {
        this.pergunta = pergunta;
        if(pergunta.getRespostaList() == null)
            pergunta.setRespostaList(new ArrayList<Resposta>());
         if(pergunta.getRespostaList().indexOf(this) == -1)
             pergunta.getRespostaList().add(this);
    }

    public Tentativa getTentativa() {
        return tentativa;
    }

    public void setTentativa(Tentativa tentativa) {
        this.tentativa = tentativa;
        if(tentativa.getRespostaList() == null)
            tentativa.setRespostaList(new ArrayList<Resposta>());
        if(tentativa.getRespostaList().indexOf(this) == -1)
        tentativa.getRespostaList().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resposta)) return false;

        Resposta resposta = (Resposta) o;

        if (tempoDecorrido != resposta.tempoDecorrido) return false;
        if (idResposta != null ? !idResposta.equals(resposta.idResposta) : resposta.idResposta != null)
            return false;
        if (data != null ? !data.equals(resposta.data) : resposta.data != null) return false;
        if (alternativa != null ? !alternativa.equals(resposta.alternativa) : resposta.alternativa != null)
            return false;
        if (pergunta != null ? !pergunta.equals(resposta.pergunta) : resposta.pergunta != null)
            return false;
        return !(tentativa != null ? !tentativa.equals(resposta.tentativa) : resposta.tentativa != null);

    }

    @Override
    public int hashCode() {
        int result = idResposta != null ? idResposta.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + tempoDecorrido;
        result = 31 * result + (alternativa != null ? alternativa.hashCode() : 0);
        result = 31 * result + (pergunta != null ? pergunta.hashCode() : 0);
        result = 31 * result + (tentativa != null ? tentativa.hashCode() : 0);
        return result;
    }
}
