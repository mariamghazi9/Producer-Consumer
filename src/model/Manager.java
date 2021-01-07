package model;

import java.util.ArrayList;

public class Manager {

    private State currentState;
    private final ArrayList<State> savedStates = new ArrayList<>();
    final private static Manager instance = new Manager();
    private Integer productsNumber;

    private Manager() {
    }

    public static Manager getInstance() {
        return instance;
    }

    public int getProductsNumber() {
        return productsNumber;
    }

    public void setProductsNumber(int productsNumber) {
        this.productsNumber = productsNumber;
    }

    public MyQueue addQueue() {
        if (currentState == null) currentState = new State();
        MyQueue myQueue = new MyQueue();
        currentState.getQueues().add(myQueue);
        return myQueue;

    }

    public void removeQueue(MyQueue myQueue) {
        currentState.getQueues().remove(myQueue);
    }

    public Machine addMachine() {
        if (currentState == null) currentState = new State();
        Machine machine = new Machine();
        currentState.getMachines().add(machine);
        return machine;
    }

    public void removeMachine(Machine machine) {
        currentState.getMachines().remove(machine);
    }

    public void startSimulation() {
        //TODO Check conditions met
        ArrayList<MyQueue> queues = currentState.getQueues();
        ArrayList<Product> products = currentState.getProducts();
        for (MyQueue queue: queues) {
            //TODO add blocking queues
        }

        for (int i = 0; i < this.productsNumber; i++) {
            products.add(new Product());
        }

        this.saveState();
    }

    private void saveState() {
        this.savedStates.add(currentState);
        currentState = null;
        productsNumber = null;
    }

    public void play(int stateIndex) {
        //TODO
    }
}
