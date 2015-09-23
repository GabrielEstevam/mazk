package br.ufsc.labtec.mazk.services;

import br.ufsc.labtec.mazk.beans.Usuario;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.POST;


/**
 * Created by Mihael Zamin on 29/03/2015.
 */
public interface PublicResource {
    @POST("/public/cadastro")
    public void cadastro(@Body Usuario u, Callback<Usuario> cb);
    @POST("/public/checkemail")
    public void isExistentEmail(@Body String email, Callback<Boolean> cb);
}
