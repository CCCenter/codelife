package com.codelife.cloud.util;

import com.codelife.cloud.util.sensitive.SensitiveFilter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SensitiveWordUtils {
    /**
     * @param result
     * @return
     */
    public static Object apply(Object result) {
        if (result == null) {
            return null;
        }
        objectParse(result);
        return result;
    }
 
 
 
    /**
     * @param obj
     */
    public static void objectParse(Object obj) {
        List<Field> allField = findAllField(obj);
        for (Field field : allField) {
            field.setAccessible(true);
            Class<?> typeClazz = field.getType();
            matchFieldType(obj, field, typeClazz);
        }
    }
 
    public static List<Field> findAllField(Object object){
        List<Field> result = new ArrayList<>();
 
        Class<?> clazz = object.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        result.addAll(Arrays.asList(declaredFields));
        return result;
    }
 
 
    /**
     * @param obj
     * @param field
     * @param clazz
     */
    public static <T> void matchFieldType(Object obj, Field field, T clazz) {
        try {
            T param = (T) field.get(obj);
            if(param == null){
                return;
            }
            if (clazz == List.class) {
                List p = (List)param;
                for (Object o : p) {
                    objectParse(o);
                }
            } else if (clazz == String.class) {
                setValue(obj, field, param);
            } else if (clazz == Map.class) {
                Map map = (Map)param;
                for (Object o : map.keySet()) {
                    objectParse(o);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
 
    /**
     *
     * @param object
     * @param field
     * @param param
     * @throws IllegalAccessException
     */
    public static void setValue(Object object, Field field, Object param) throws IllegalAccessException {
        if(!field.isAccessible()){
            throw new IllegalAccessException("modify the field fail.");
        }
        SensitiveFilter filter = SensitiveFilter.DEFAULT;
        String filtered = filter.filter(param.toString(), '*');
        field.set(object,filtered);
    }
}