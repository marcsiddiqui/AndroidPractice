package com.example.apr;

import android.content.ContentValues;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class GenericFunctions {

    public static <T> HashMap<String, String> ConvertClassIntoHashMap(T cls){
        if (cls == null)
            return null;
        HashMap<String, String> hm = new HashMap<>();
        Field[] fileds = cls.getClass().getDeclaredFields();
        for(Field field: fileds){
            try {
                Object ab = field.get(cls);
                hm.put(field.getName(), ab.toString());
            }
            catch (Exception e) {
                System.out.println("Error");
            }
        }
        return hm;
    }

    public static <T> ContentValues ConvertClassIntoContentValues(T cls){
        if (cls == null)
            return null;
        ContentValues hm = new ContentValues();
        Field[] fileds = cls.getClass().getDeclaredFields();
        for(Field field: fileds){
            if (field.getName() != "Id"){
                try {
                    Object ab = field.get(cls);
                    hm.put(field.getName(), ab.toString());
                }
                catch (Exception e) {
                    System.out.println("Error");
                }
            }
        }
        return hm;
    }

}
