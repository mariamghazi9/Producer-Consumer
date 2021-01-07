package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Machine implements Producer, Runnable {

    private static class GraphicMachine {
        private Color color = Color.cyan;
        private Point coordinates;

        public Color getColor() {
            return color;
        }

        public Point getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(Point coordinates) {
            this.coordinates = coordinates;
        }
    }

    private final GraphicMachine graphicMachine = new GraphicMachine();
    private final int serviceTime;
    private final ArrayList<Source> sources = new ArrayList<>();
    private Consumer consumer;
    private final int id;
    private Product currentProduct = null;

    public Machine(int id) {
        this.id = id;
        final int low = 2000;
        final int high = 10000;
        Random rand = new Random();
        serviceTime = rand.nextInt(high - low) + low;
    }

    public GraphicMachine getGraphicMachine(){
        return this.graphicMachine;
    }

    public void addSource(Source source) {
        this.sources.add(source);
    }

    public void removeSource(Source source) {
        this.sources.remove(source);
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(serviceTime);
        } catch (InterruptedException e) {
            System.err.println("Interrupted");
            e.printStackTrace();
        } finally {
            consumer.consume(currentProduct);
            this.graphicMachine.color = Color.cyan;
            this.currentProduct = null;

            for (Source source : sources) {
                source.update(this);
            }
        }
    }

    @Override
    public void serve(Product product) {
        this.currentProduct = product;
        this.graphicMachine.color = this.currentProduct.getColor();
        Thread thread = new Thread(this, "Machine #" + id);
        thread.start();
    }
}
