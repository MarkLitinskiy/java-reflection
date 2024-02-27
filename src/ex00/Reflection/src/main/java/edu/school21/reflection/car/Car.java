package edu.school21.reflection.car;

import edu.school21.reflection.user.User;

import java.util.StringJoiner;

public class Car {
    private String firm;
    private String model;
    private int price;

    public Car() {
        this.firm = "Default firm";
        this.model = "Default model";
        this.price = 0;
    }

    public Car(String firm, String model, int price) {
        this.firm = firm;
        this.model = model;
        this.price = price;
    }

    public int inflation(int value) {
        this.price += value;
        return price;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("firstName='" + firm + "'")
                .add("lastName='" + model + "'")
                .add("height=" + price)
                .toString();
    }
}
