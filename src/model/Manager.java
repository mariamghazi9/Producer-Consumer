package model;

import controllers.Simulator;

import java.awt.*;
import java.util.ArrayList;

public class Manager {

    private State currentState;
    private final ArrayList<State> savedStates = new ArrayList<>();
    final private static Manager instance = new Manager();
    private static int machineIDGenerator = 0;
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

    public static int getMachineIDGenerator() {
        return machineIDGenerator;
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
        machineIDGenerator++;
        currentState.getMachines().add(machine);
        return machine;
    }

    public void removeMachine(Machine machine) {
        currentState.getMachines().remove(machine);
    }

    public void startSimulation() {
        if (productsNumber == null) {
            throw new RuntimeException();
        }
        this.currentState.setFirstQueue(currentState.getQueues().get(0));
        ArrayList<MyQueue> queues = currentState.getQueues();
        for (MyQueue queue: queues) {
            queue.createBlockingQueue(this.productsNumber);
            queue.setController(controller);
        }

        for (int i = 0; i < this.productsNumber; i++) {
            currentState.getFirstQueue().consume(new Product());
        }

        this.savedStates.add(currentState);
        this.play(savedStates.size() - 1);
    }

    public void newState() {
        currentState = null;
        productsNumber = null;
    }

    public void play(int stateIndex) {
        currentState = savedStates.get(stateIndex);
    }

    public State getCurrentState() {
        return currentState;
    }

    public ArrayList<State> getSavedStates() {
        return savedStates;
    }
}
