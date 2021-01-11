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

    public void setController(Simulator controller) {
        this.controller = controller;
    }

    @Override
    public void update(Producer machine) {
        this.readyMachines.add(machine);
        registerProduct();
    }

    public int productsCount() {
        if (productsQueue == null) return 0;
        return productsQueue.size();
    }

    private synchronized void registerProduct() {
        Product product = productsQueue.remove();
        controller.updateCanvas();
        if (!readyMachines.isEmpty()) {
            Producer activeMachine = readyMachines.remove(0);
            activeMachine.serve(product);
        }

    }

    @Override
    public void consume(Product product) {
        productsQueue.add(product);
        controller.updateCanvas();
        registerProduct();
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


    public void addMachine(Machine machine){
        this.readyMachines.add(machine);
        this.machines.add(machine);
    }
}

