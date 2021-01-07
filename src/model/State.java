package model;

import java.util.ArrayList;

public class State {

    private ArrayList<Product> products;
    private ArrayList<Machine> machines;
    private ArrayList<MyQueue> queues;

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Machine> getMachines() {
        return machines;
    }

    public ArrayList<MyQueue> getQueues() {
        return queues;
    }
}
