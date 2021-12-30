package com.example.rxandroidpresentation;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public static List<Fruit> createFruitsList() {
        List<Fruit> basket = new ArrayList<>();
        basket.add(new Fruit("banana"));
        basket.add(new Fruit("apple", false, 3));
        basket.add(new Fruit("apple", true, 7));
        basket.add(new Fruit("kiwi", false, 12));
        return basket;
    }
}
