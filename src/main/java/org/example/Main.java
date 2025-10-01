package org.example;

public class Main {
    public static void main(String[] args) {
        CustomHashMap<String, Integer> customHashMap = new CustomHashMap<>();
        customHashMap.put("a", 1);
        customHashMap.put("b", 2);
        customHashMap.put("Nikita", 22);
        System.out.println(customHashMap.get("Nikita"));
        customHashMap.put(null, 100);
        customHashMap.put("BLAKE", 10);
        customHashMap.remove("a");
    }
}