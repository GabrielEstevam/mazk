package br.ufsc.labtec.mazk.services;

import java.util.List;

import br.ufsc.labtec.mazk.beans.Alternativa;
import br.ufsc.labtec.mazk.beans.Pergunta;
import br.ufsc.labtec.mazk.beans.Tipo;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Mihael Zamin on 29/03/2015.
 */
public interface PerguntaResource {
    @GET("/pergunta/listar")
    public void listarPerguntas(Callback<List<Pergunta>> cb);
    @POST("/pergunta/getalternativas")
    public void getAlternativas(@Body Pergunta p, Callback<List<Alternativa>> cb);
    @POST("/pergunta/inserir")
    public void addPergunta(@Body Pergunta p, Callback<Pergunta> cb);
    @POST("/pergunta/update")
    public void updatePergunta(@Body Pergunta p, Callback<Pergunta> cb);

}
