package model;

import java.util.ArrayList;
import java.util.List;

public class Manager {

    private State currentState;
    private final ArrayList<State> savedStates = new ArrayList<>();
    final private static Manager instance = new Manager();
    private int productsNumber;
    private MyQueue mainQueue;

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
        ArrayList<MyQueue> queues = currentState.getQueues();
        ArrayList<Product> products = currentState.getProducts();
        for (MyQueue queue: queues) {

        }

        for (int i = 0; i < this.productsNumber; i++) {
            products.add(new Product());
        }

        this.saveState();
    }

    private void saveState() {
        this.savedStates.add(currentState);
        currentState = null;
    }

    public void play(int stateIndex) {

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
            this.mainQueue = mainQueue.get(0);
            return true;
        }
        return false;
    }
}
