package br.ufsc.labtec.mazk.services.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.Date;

import br.ufsc.labtec.mazk.beans.json.DateSerializer;
import br.ufsc.labtec.mazk.beans.json.DefaultGson;
import br.ufsc.labtec.mazk.interceptor.LoginRequestInterceptor;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import retrofit.converter.JacksonConverter;

/**
 * Created by Mihael Zamin on 30/03/2015.
 */
public abstract class ServiceGenerator <S> {
    // No need to instantiate this class.
    private Class<S> serviceClass;
    public ServiceGenerator()
    {

    }
    protected ServiceGenerator(Class<S> serviceClass) {
        this.serviceClass = serviceClass;
    }

    public S createService(String baseUrl) {
        // call basic auth generator method without user and pass
        return createService(baseUrl, null, null);
    }

    protected S createService(String baseUrl, String username, String password) {
        // set endpoint url and use OkHTTP as HTTP client

        ObjectMapper mapper = new ObjectMapper();

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseUrl).setConverter(new JacksonConverter(mapper))
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).setLog(new AndroidLog("RETROFIT"));

        if (username != null && password != null) {
            builder.setRequestInterceptor(new LoginRequestInterceptor(username, password));
        }

        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }
}
