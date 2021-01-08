package model;

import java.util.ArrayList;

public class State {

    private final ArrayList<Product> products = new ArrayList<>();
    private final ArrayList<Machine> machines = new ArrayList<>();
    private final ArrayList<MyQueue> queues = new ArrayList<>();
    private MyQueue firstQueue;

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Machine> getMachines() {
        return machines;
    }

    public ArrayList<MyQueue> getQueues() {
        return queues;
    }

    public MyQueue getFirstQueue() {
        return firstQueue;
    }

    public void setFirstQueue(MyQueue firstQueue) {
        this.firstQueue = firstQueue;
    }
}
