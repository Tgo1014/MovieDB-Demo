package tgo1014.moviedb_demo.persistence.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class IntegerListTypeConverter {

    @TypeConverter
    public static String toJson(List<Integer> value) {
        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        return  new Gson().toJson(value, type);
    }

    @TypeConverter
    public static List<Integer> fromJson(String value) {
        Type typeToken = new TypeToken<List<Integer>>() {
        }.getType();
        return new Gson().fromJson(value, typeToken);
    }
}
