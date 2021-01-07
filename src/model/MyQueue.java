package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class MyQueue implements Source, Consumer {
    private GraphicalQueue graphicalQueue;
    private List<Producer> readyMachines;
    private ArrayBlockingQueue<Product> ProductsQueue;
    public MyQueue() {
        graphicalQueue = new GraphicalQueue();
        readyMachines = new ArrayList<>();
        ProductsQueue = new ArrayBlockingQueue<Product>(Manager.getInstance().getProductsNumber());
    }

    public GraphicalQueue getGraphicalQueue() {
        return graphicalQueue;
    }

    @Override
    public void update(Producer machine) {
        this.readyMachines.add(machine);
        registerProduct();

    }

    private synchronized void registerProduct() {
        Product product = ProductsQueue.remove();
        if (!readyMachines.isEmpty()) {
            Producer activeMachine = readyMachines.remove(0);
            activeMachine.serve(product);
        }

    }

    @Override
    public void consume(Product product) {

        ProductsQueue.add(product);
        registerProduct();
    }

    private class GraphicalQueue {
        private Point coordinates;

        public Point getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(Point coordinates) {
            this.coordinates = coordinates;
        }

    }
}
