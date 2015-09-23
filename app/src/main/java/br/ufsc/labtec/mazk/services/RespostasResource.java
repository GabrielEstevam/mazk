package br.ufsc.labtec.mazk.services;

import java.util.List;

import br.ufsc.labtec.mazk.beans.Pergunta;
import br.ufsc.labtec.mazk.beans.Resposta;
import br.ufsc.labtec.mazk.beans.Tentativa;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by Mihael Zamin on 29/03/2015.
 */
public interface RespostasResource {
    public final static String PATH = "/respostas";
    @GET(PATH+"/get/random")
    public void getRandom(Callback<List<Pergunta>> cb);
    @GET(PATH+"/get/tentativa")
    public void getTentativa(Callback<Tentativa> cb);
    @POST(PATH)
    public void addRespostas(@Body Tentativa tentativa, Callback<Tentativa> cb);
}
