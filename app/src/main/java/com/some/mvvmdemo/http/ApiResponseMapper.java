package com.some.mvvmdemo.http;

import com.some.http.ApiException;
import com.some.http.entity.BaseEntity;

import io.reactivex.functions.Function;

/**
 * @author xiangxing
 * 注意理解泛型的使用
 */
public class ApiResponseMapper {

    /**
     * 理解泛型方法的使用
     * @param <T>
     * @return
     */
    public static <T> Function<BaseEntity<T>, T> create(){

        return new Function<BaseEntity<T>, T>() {
            @Override
            public T apply(@io.reactivex.annotations.NonNull BaseEntity<T> encryptInfoBaseEntity) throws Exception {
                if(encryptInfoBaseEntity.getResult() == 1000){
                    return encryptInfoBaseEntity.getInfo();
                }
                throw new ApiException(encryptInfoBaseEntity.getResult(),
                        " msg = " + encryptInfoBaseEntity.getMsg());
            }
        };
    }
}
