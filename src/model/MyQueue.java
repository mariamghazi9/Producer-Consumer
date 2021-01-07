package model;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class MyQueue implements Source,Consumer {

    private List<Producer> readyMachines;
    private ArrayBlockingQueue<Product> ProductsQueue;


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
