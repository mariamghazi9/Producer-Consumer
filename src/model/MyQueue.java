package model;

import controllers.Simulator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class MyQueue implements Source, Consumer {

    protected boolean hasSource=false;
    private final List<Producer> readyMachines;
    private ArrayBlockingQueue<Product> ProductsQueue;
    private Simulator controller;

    public MyQueue() {
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

    private synchronized void registerProduct() {
        Product product = ProductsQueue.remove();
        controller.updateCanvas();
        if (!readyMachines.isEmpty()) {
            Producer activeMachine = readyMachines.remove(0);
            activeMachine.serve(product);
        }

    }

    @Override
    public void consume(Product product) {

        ProductsQueue.add(product);
        controller.updateCanvas();
        registerProduct();
    }

    public void createBlockingQueue(int maxSize)
    {
        ProductsQueue=new ArrayBlockingQueue<>(maxSize);
    }

        private Point coordinates;

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

    }

