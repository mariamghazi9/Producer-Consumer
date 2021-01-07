package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class MyQueue implements Source,Consumer {
    private class GraphicalQueue{
        public Point getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(Point coordinates) {
            this.coordinates = coordinates;
        }

        private Point coordinates;

    }

    public GraphicalQueue getGraphicalQueue() {
        return graphicalQueue;
    }



    private GraphicalQueue graphicalQueue=new GraphicalQueue();
    private List<Producer> readyMachines=new ArrayList<>();
    private ArrayBlockingQueue<Product> ProductsQueue=new ArrayBlockingQueue<Product>(1000);



    @Override
    public void update(Producer machine) {
        this.readyMachines.add(machine);
        registerProduct();

    }

    private synchronized void registerProduct()
    {
        Product product=ProductsQueue.remove();
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
}
