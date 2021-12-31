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

    public static List<Fruit> createLabList() {
        List<Fruit> basket = new ArrayList<>();
        basket.add(new Fruit("apple", false, 3));
        basket.add(new Fruit("strawberry", true, 20));
        basket.add(new Fruit("apple", false, 4));
        basket.add(new Fruit("cherry", false, 2));
        basket.add(new Fruit("orange", true, 10));
        basket.add(new Fruit("apple", false, 5));
        basket.add(new Fruit("kiwi", true, 6));
        basket.add(new Fruit("apple", true, 7));
        basket.add(new Fruit("kiwi", false, 12));
        basket.add(new Fruit("banana"));
        basket.add(new Fruit("raspberry", true, 3));
        basket.add(new Fruit("strawberry", false, 2));
        basket.add(new Fruit("pineapple", true, 1));
        return basket;
    }
}
