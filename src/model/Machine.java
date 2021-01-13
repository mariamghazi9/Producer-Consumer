package model;


import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Machine implements Producer, Runnable, Graphical {


    private Color color = Color.rgb(183, 227, 255, 0.8);
    private Point coordinates;
    private final int serviceTime;
    private int machineID;

    public ArrayList<Source> getSources() {
        return sources;
    }

    private final ArrayList<Source> sources = new ArrayList<>();

    public Consumer getConsumer() {
        return consumer;
    }

    public MyQueue getDestination(){
        return (MyQueue) consumer;
    }


    private Consumer consumer;
    private Product currentProduct = null;
    public Machine(Point coordinates) {
        this.machineID = Manager.getMachineIDGenerator();
        this.coordinates = coordinates;
        final int low = 2000;
        final int high = 10000;
        Random rand = new Random();
        serviceTime = rand.nextInt(high - low) + low;
    }

    public Color getColor() {
        return color;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public void addSource(Source source) {
        this.sources.add(source);
    }

    public void removeSource(Source source) {
        this.sources.remove(source);
    }

    public void removeConsumer(Consumer consumer) {
        this.consumer = null;
        ((MyQueue)consumer).setHasSource(false);
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
        ((MyQueue)consumer).setHasSource(true);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(serviceTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.color = Color.rgb(183, 227, 255, 0.8);
            consumer.consume(currentProduct);
            this.currentProduct = null;
            for (Source source : sources) {
                if (source.update(this)) {
                    break;
                }
            }
        }
    }

    @Override
    public synchronized void serve(Product product) {
        this.currentProduct = product;
        this.color = this.currentProduct.getColor();
        for (Source source: sources) {
            source.getReadyMachines().remove(this);
        }
        Thread thread = new Thread(this);
        thread.start();
    }
}
