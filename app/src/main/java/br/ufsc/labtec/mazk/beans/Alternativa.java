package br.ufsc.labtec.mazk.beans;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import java.util.List;

/**
 * Created by Mihael Zamin on 25/03/2015.
 */
@JsonIdentityInfo(generator = JSOGGenerator.class)
//@JsonIgnoreProperties({"@ref"})
public class Alternativa {

    private Integer idAlternativa;
    private Boolean correta;
    private String descricao;
    private List<Resposta> respostaList;
    private Pergunta pergunta;

    public Integer getIdAlternativa() {
        return idAlternativa;
    }

    public void setIdAlternativa(Integer idAlternativa) {
        this.idAlternativa = idAlternativa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isCorreta() {
        if (correta == null)
            correta = Boolean.FALSE;
        return correta;
    }

    public void setCorreta(Boolean correta) {
        this.correta = correta;
    }

    public List<Resposta> getRespostaList() {
        return respostaList;
    }

    public void setRespostaList(List<Resposta> respostaList) {
        this.respostaList = respostaList;
    }

    public Pergunta getPergunta() {
        return pergunta;
    }

    public void setPergunta(Pergunta pergunta) {
        this.pergunta = pergunta;
       /* if(pergunta.getAlternativaList() == null)
            pergunta.setAlternativaList(new ArrayList<Alternativa>());
        pergunta.getAlternativaList().add(this);*/
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alternativa)) return false;

        Alternativa that = (Alternativa) o;

        if (!correta.equals(that.correta)) return false;
        if (!descricao.equals(that.descricao)) return false;
        if (!idAlternativa.equals(that.idAlternativa)) return false;
        if (pergunta != null ? !pergunta.equals(that.pergunta) : that.pergunta != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idAlternativa.hashCode();
        result = 31 * result + correta.hashCode();
        result = 31 * result + descricao.hashCode();
        result = 31 * result + (pergunta != null ? pergunta.hashCode() : 0);
        return result;
    }
}
