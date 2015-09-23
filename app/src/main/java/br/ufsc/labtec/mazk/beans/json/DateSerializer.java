package br.ufsc.labtec.mazk.beans.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by Mihael Zamin on 01/04/2015.
 */
public class DateSerializer implements JsonSerializer<Date>, JsonDeserializer<Date> {


    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext
            context) {
        return new JsonPrimitive(src.getTime());
    }

    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext
            context)
            throws JsonParseException {
        return new Date(json.getAsJsonPrimitive().getAsLong());
    }
}
