package com.some.mvvmdemo.hotfix;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author xiangxing
 */
public class ShareReflectUtil {

    public static Field findField(Object instance, String name){

        Class<?> cls = instance.getClass();
        while (cls != Object.class){
            try{
                Field field = cls.getDeclaredField(name);
                if(field != null){
                    field.setAccessible(true);
                    return field;
                }

            }catch (NoSuchFieldException e){

            }
            cls = cls.getSuperclass();
        }
        return null;
    }

    public static Method getMethod(Object instance, String methodName, Class<?>... parameterTypes){

        Class<?> cls = instance.getClass();
        while (cls != Object.class){
            try{
                Method method = cls.getDeclaredMethod(methodName, parameterTypes);
                if(method != null){
                    method.setAccessible(true);
                    return method;
                }

            }catch (NoSuchMethodException e){

            }
            cls = cls.getSuperclass();
        }
        return null;
    }
}
