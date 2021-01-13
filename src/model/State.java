package model;

import java.util.ArrayList;

public class State {

    private final ArrayList<Product> products = new ArrayList<>();
    private final ArrayList<Machine> machines = new ArrayList<>();
    private final ArrayList<MyQueue> queues = new ArrayList<>();
    private MyQueue firstQueue;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    private int size;

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
    protected void resetState() {
        this.getQueues().get(1).getProductsQueue().clear();
        ArrayList<MyQueue> list = this.getQueues();
        for (MyQueue q : list) {
            int size = q.getMachines().size();
            q.getReadyMachines().clear();
            for (int i = 0; i < size; i++) {
                q.getReadyMachines().add(q.getMachines().get(i));
            }
        }
    }

}
