package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;
import java.util.*;
import java.net.URL;
import java.util.List;


public class Simulator implements Initializable {

    @FXML
    Canvas canvas;
    List circles=new ArrayList<Point>();
    @FXML
    Button deleteButton;
    @FXML
    Button addQueue;
    @FXML
    Button addMachine;
    @FXML
    TextField productsNumText;

    private boolean deleteFlag = false;
    private boolean isQueue = true;
    private ArrayList<Point> queues = new ArrayList<Point>();
    private ArrayList<Point> machines = new ArrayList<Point>();
    private ArrayList<Color> finishedProducts = new ArrayList<Color>();


    @FXML
    public void clicked(MouseEvent event) {
        deleteButton.getStyleClass().add("button-style-disable");
        if(event.getClickCount() == 2){
            System.out.println("Double clicked");
            if (isQueue) {
                addQueue(canvas.getGraphicsContext2D(), (int) event.getX(), (int) event.getY());
            } else {
                addMachine(canvas.getGraphicsContext2D(), (int) event.getX(), (int) event.getY());
            }
        }
    }

    @FXML
    public void deleteConnection() {
        if (deleteFlag) {
            deleteButton.getStyleClass().remove(1);
        } else {
            deleteButton.getStyleClass().add("disable");
        }
        deleteFlag = !deleteFlag;
    }

    @FXML
    protected void handleAddQueue() {
        toggleInsert(true);
    }

    @FXML
    protected void handleAddMachine() {
        toggleInsert(false);
    }

    public void toggleInsert(boolean isQueue) {
        if (isQueue != this.isQueue) {
            if (isQueue) {
                addMachine.getStyleClass().remove(1);
                addQueue.getStyleClass().add("selected");
            } else {
                addQueue.getStyleClass().remove(1);
                addMachine.getStyleClass().add("selected");
            }
            this.isQueue = isQueue;
        }
    }

    private void addQueue(GraphicsContext gc, int x, int y) {
        gc.setFill(Color.GREEN);
        gc.fillRect(x-25, y-12.5, 50, 25);
        gc.setFont(Font.font ("Verdana", 10));
        gc.setFill(Color.WHITESMOKE);
        gc.fillText("Q1", x-8, y);
    }

    private void addMachine(GraphicsContext gc, int x, int y) {
        gc.setFill(Color.RED);
        int radius = 25;
        gc.fillOval(x-radius, y-radius, 2*radius, 2*radius);
        gc.setFont(Font.font ("Verdana", 10));
        gc.setFill(Color.WHITESMOKE);
        gc.fillText("M1", x-8, y);
    }

    private void drawProduct(GraphicsContext gc, int index, Color color) {
        gc.setFill(color);
        int radius = 20;
        System.err.println(50+50*index);
        gc.fillOval(50+50*index, canvas.getHeight()-50, 2*radius, 2*radius);
    }

    @FXML
    private boolean select(MouseEvent event){
        boolean selected=false;
        double mouse_x = event.getX();
        double mouse_y = event.getY();
        double distance=0;
        Iterator<Point> pointsM= machines.listIterator();
        Iterator<Point> pointsQ= queues.listIterator();
        while(pointsM.hasNext()){
            Point t = pointsM.next();
            distance=Math.sqrt(Math.pow((mouse_x - t.getX()), 2) + Math.pow((mouse_y - t.getY()), 2));
            if(distance <= 25)
                selected=true;
        }
        while(pointsQ.hasNext()){
            Point t = pointsQ.next();
            if((mouse_x>=t.getX()-25 && mouse_x<=t.getX()+25) || (mouse_y>=t.getY()-12.5 && mouse_y<=t.getY()-12.5))
                selected=true;
        }
        return selected;
    }

    private void connect(GraphicsContext gc){
        // Iterator<Point> pointsM= machines.listIterator();
        //Iterator<Point> pointsQ= queues.listIterator();
        gc.strokeLine(100-25,100,25+25,25);
        gc.setFill(Color.BLACK);
    }

    public void updateCanvas() {
        Iterator<Point> queueIterator = queues.iterator();
        while (queueIterator.hasNext()) {
            Point temp = queueIterator.next();
            addQueue(canvas.getGraphicsContext2D(), temp.x, temp.y);
        }
        Iterator<Point> machineIterator = machines.iterator();
        while (machineIterator.hasNext()) {
            Point temp = machineIterator.next();
            addMachine(canvas.getGraphicsContext2D(), temp.x, temp.y);
        }

        int index = 0;
        for (ListIterator<Color> iterator = finishedProducts.listIterator(finishedProducts.size()); iterator.hasPrevious();) {
            Color temp = iterator.previous();
            drawProduct(canvas.getGraphicsContext2D(), index, temp);
            index++;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addQueue.getStyleClass().add("selected");
        queues.add(new Point(100, 100));
        queues.add(new Point(200, 200));
        queues.add(new Point(300, 300));
        machines.add(new Point(25, 25));
        machines.add(new Point(250, 250));
        machines.add(new Point(350, 350));
        finishedProducts.add(Color.STEELBLUE);
        finishedProducts.add(Color.ROYALBLUE);
        finishedProducts.add(Color.BEIGE);
        finishedProducts.add(Color.MEDIUMAQUAMARINE);
        updateCanvas();
        connect(canvas.getGraphicsContext2D());
    }
}

