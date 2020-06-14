package com.collection.map;

import java.util.HashMap;
import java.util.Map;

public class HashMapdemo1 {
    public static void main(String[] args){
        Map<String,Object> map = new HashMap<>();
        map.put("hello","world");
        String hello = (String)map.get("hello");
        System.out.println(hello);

    }
}
