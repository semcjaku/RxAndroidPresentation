package com.example.rxandroidpresentation;

public class Fruit {

    private String name;
    private boolean isRotten;
    private int daysOld;

    public Fruit(String name, boolean isRotten, int daysOld) {
        this.name = name;
        this.isRotten = isRotten;
        this.daysOld = daysOld;
    }

    public Fruit(String name) {
        this(name, false, 0);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRotten() {
        return isRotten;
    }

    public void setRotten(boolean rotten) {
        isRotten = rotten;
    }

    public int getDaysOld() {
        return daysOld;
    }

    public void setDaysOld(int daysOld) {
        this.daysOld = daysOld;
    }

    @Override
    public String toString() {
        String rotString = isRotten ? " " : " not ";
        return name + " is" + rotString + "rotten after " + daysOld + " days";
    }
}
