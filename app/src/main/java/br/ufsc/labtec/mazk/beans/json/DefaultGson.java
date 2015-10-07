package br.ufsc.labtec.mazk.beans.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

/**
 * Created by Mihael Zamin on 07/04/2015.
 */
public class DefaultGson {
    public static Gson getDefaultGson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").registerTypeAdapter(Date.class, new DateSerializer()).create();

    }
}
