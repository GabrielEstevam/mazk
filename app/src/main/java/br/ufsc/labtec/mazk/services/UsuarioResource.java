package br.ufsc.labtec.mazk.services;

import java.util.List;

import br.ufsc.labtec.mazk.beans.Tentativa;
import br.ufsc.labtec.mazk.beans.Tipo;
import br.ufsc.labtec.mazk.beans.Usuario;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Mihael Zamin on 29/03/2015.
 */
public interface UsuarioResource {
    @GET("/usuario/listar")
    public void listar(Callback<List<Usuario>> cb);
    @GET("/usuario/getinfo/{id}")
    public void getUserInfo(@Path("id") int id, Callback<Usuario> cb);
    @GET("/usuario/login")
    public void login(Callback<Usuario> cb);
    @GET("/usuario/tipo")
    public void getAllTipos(Callback<List<Tipo>> cb);
    @GET("/usuario/tentativas")
    public void getAllTentativas(Callback<List<Tentativa>> cb);
}
