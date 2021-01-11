package model;

import controllers.Simulator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Manager {

    private State currentState;
    private final ArrayList<State> savedStates = new ArrayList<>();
    final private static Manager instance = new Manager();
    private Integer productsNumber;
    private Simulator controller;

    private Manager() {
    }

    public void setController(Simulator controller) {
        this.controller = controller;
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

    public MyQueue addQueue(Point coordinates) {
        if (currentState == null) currentState = new State();
        MyQueue myQueue = new MyQueue(coordinates);
        currentState.getQueues().add(myQueue);
        return myQueue;

    }

    public void removeQueue(MyQueue myQueue) {
        currentState.getQueues().remove(myQueue);
    }

    public Machine addMachine(Point coordinates) {
        if (currentState == null) currentState = new State();
        Machine machine = new Machine(coordinates);
        currentState.getMachines().add(machine);
        return machine;
    }

    public void removeMachine(Machine machine) {
        currentState.getMachines().remove(machine);
    }

    public void startSimulation() {
        if (!setMainQueue() || (productsNumber == null)) {
            throw new RuntimeException();
        }
        ArrayList<MyQueue> queues = currentState.getQueues();
        ArrayList<Product> products = currentState.getProducts();
        for (MyQueue queue: queues) {
            queue.createBlockingQueue(this.productsNumber);
            queue.setController(controller);
        }

        for (int i = 0; i < this.productsNumber; i++) {
            products.add(new Product());
        }

        this.saveState();
        this.play(savedStates.size() - 1);
    }

    private void saveState() {
        this.savedStates.add(currentState);
        currentState = null;
        productsNumber = null;
    }

    public void play(int stateIndex) {
        State myState = savedStates.get(stateIndex);
    }

    private boolean setMainQueue()
    {
        List<MyQueue> mainQueue=new ArrayList<>();
        List<MyQueue> allQueues=this.currentState.getQueues();
        for (MyQueue queue: allQueues)
        {
             if(!queue.hasSource())
             {
                mainQueue.add(queue);
             }
        }
        if(mainQueue.size()==1) {
            this.currentState.setFirstQueue(mainQueue.get(0));
            return true;
        }
        return false;
    }

    public State getCurrentState() {
        return currentState;
    }
}
