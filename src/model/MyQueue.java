package model;

import controllers.Simulator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class MyQueue implements Source, Consumer, Graphical {

    protected boolean hasSource=false;
    private final List<Producer> readyMachines;

    public List<Machine> getMachines() {
        return machines;
    }

    private List<Machine> machines = new ArrayList<>();
    private ArrayBlockingQueue<Product> productsQueue;
    private Simulator controller;
    private Point coordinates;

    public MyQueue(Point coordinates) {
        this.coordinates = coordinates;
        readyMachines = new ArrayList<>();
    }

    public ArrayBlockingQueue<Product> getProductsQueue() {
        return productsQueue;
    }

    public void setController(Simulator controller) {
        this.controller = controller;
    }

    @Override
    public boolean update(Producer machine) {
        this.readyMachines.add(machine);
        if (productsCount() > 0) {
            registerProduct();
            return true;
        }
        return false;
    }

    public int productsCount() {
        if (productsQueue == null) return 0;
        return productsQueue.size();
    }

    private synchronized void registerProduct() {
        if (!readyMachines.isEmpty()) {
            Product product = productsQueue.poll();
            Producer activeMachine = readyMachines.remove(0);
            activeMachine.serve(product);
        }
        controller.updateCanvas();
    }
    @Override
    public void consume(Product product) {
        productsQueue.add(product);
        registerProduct();
        controller.updateCanvas();
    }

    public void createBlockingQueue(int maxSize)
    {
        productsQueue=new ArrayBlockingQueue<>(maxSize);
    }

    public Point getCoordinates() {
            return coordinates;
        }

    public void setCoordinates(Point coordinates) {
            this.coordinates = coordinates;
        }

    protected boolean hasSource()
      {
          return this.hasSource;
      }

    protected void setHasSource(boolean hasSource)
      {
          this.hasSource=hasSource;
      }

    public synchronized List<Producer> getReadyMachines() {
        return readyMachines;
    }

    public void addMachine(Machine machine){
        this.readyMachines.add(machine);
        this.machines.add(machine);
    }
}

