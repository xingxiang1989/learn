package com.some.http.converter;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.some.http.ApiException;
import com.some.http.entity.BaseEntity;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author xiangxing
 */
public class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    MyGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException{
        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        try {
            T result = adapter.read(jsonReader);
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonIOException("JSON document was not fully consumed.");
            }
            if(result instanceof BaseEntity){
                BaseEntity entity = (BaseEntity)result;
                if(entity.getResult() == 1000){
                    return (T) entity.getInfo();
                }else{
                    throw new ApiException(entity.getResult(),
                            entity.getMsg());
                }
            }
            return result;
        } finally {
            value.close();
        }
    }
}
