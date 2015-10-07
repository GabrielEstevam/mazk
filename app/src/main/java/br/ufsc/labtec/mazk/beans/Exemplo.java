package br.ufsc.labtec.mazk.beans;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

/**
 * Created by Mihael Zamin on 07/04/2015.
 */
@JsonIdentityInfo(generator = JSOGGenerator.class)
//@JsonIgnoreProperties({"@ref"})
public class Exemplo {
    private Integer idExemplo;
    private byte[] conteudo;
    private Pergunta pergunta;

    public Integer getIdExemplo() {
        return idExemplo;
    }

    public void setIdExemplo(Integer idExemplo) {
        this.idExemplo = idExemplo;
    }

    public byte[] getConteudo() {
        return conteudo;
    }

    public void setConteudo(byte[] conteudo) {
        this.conteudo = conteudo;
    }

    public Pergunta getPergunta() {
        return pergunta;
    }

    public void setPergunta(Pergunta pergunta) {
        this.pergunta = pergunta;
    }


}
