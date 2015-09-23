package br.ufsc.labtec.mazk.services;

import java.util.List;

import br.ufsc.labtec.mazk.beans.Area;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;

/**
 * Created by Mihael Zamin on 29/03/2015.
 */
public interface AreaResource {
    public final static String PATH = "/area";
    @GET(AreaResource.PATH)
    public void getAreas(Callback<List<Area>> cb);
    @POST(AreaResource.PATH)
    public void addArea(@Body Area t, Callback<Area> cb);
    @PUT(AreaResource.PATH)
    public void updateArea(Area t, Callback<Area> cb);
    @DELETE(AreaResource.PATH)
    public void deleteArea(Integer idArea);
}
