package br.ufsc.labtec.mazk.beans;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import java.util.List;

/**
 * Created by Mihael Zamin on 25/03/2015.
 */
@JsonIdentityInfo(generator = JSOGGenerator.class)
//@JsonIgnoreProperties({"@ref"})
public class Area {

    private Integer idArea;

    private String nome;
    private List<Pergunta> perguntaList;
    private List<Area> areaList;
    private Area area;

    public Area getArea() {
        return this.area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }

    public List<Pergunta> getPerguntaList() {
        return perguntaList;
    }

    public void setPerguntaList(List<Pergunta> perguntaList) {
        this.perguntaList = perguntaList;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Area)) return false;

        Area area1 = (Area) o;

        if (idArea != null ? !idArea.equals(area1.idArea) : area1.idArea != null) return false;
        if (nome != null ? !nome.equals(area1.nome) : area1.nome != null) return false;
        else return true;

    }

    @Override
    public int hashCode() {
        return idArea != null ? idArea.hashCode() : 0;
    }
}
