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
import model.*;

import java.awt.*;
import java.util.*;
import java.net.URL;


public class Simulator implements Initializable {

    @FXML
    Canvas canvas;
    @FXML
    Button deleteConnectionButton;
    @FXML
    Button deleteElementButton;
    @FXML
    Button addQueue;
    @FXML
    Button addMachine;
    @FXML
    TextField productsNumText;

    private boolean deleteElementFlag = false;
    private boolean deleteConnectionFlag = false;
    private boolean isQueue = true;
    private MyQueue source;
    private MyQueue endStack;
    private Graphical currentlySelected;
    private Point selectionReference;
    private ArrayList<Color> finishedProducts = new ArrayList<Color>();



    @FXML
    public void clicked(MouseEvent event) {
        if(event.getClickCount() == 2){
            System.out.println("Double clicked");
            if (isQueue) {
                Manager.getInstance().addQueue(new Point((int) event.getX(), (int) event.getY()));
            } else {
                Manager.getInstance().addMachine(new Point((int) event.getX(), (int) event.getY()));
            }
            updateCanvas();
        }
    }

    @FXML
    public void deleteConnection() {
        if (deleteConnectionFlag) {
            deleteConnectionButton.getStyleClass().remove(1);
        } else {
            deleteConnectionButton.getStyleClass().add("disable");
        }
        deleteConnectionFlag = !deleteConnectionFlag;
    }

    @FXML
    public void deleteElement() {
        if (deleteElementFlag) {
            deleteElementButton.getStyleClass().remove(1);
        } else {
            deleteElementButton.getStyleClass().add("disable");
        }
        deleteElementFlag = !deleteElementFlag;
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

    private void drawQueue(GraphicsContext gc, int x, int y, String text) {
        gc.setFill(Color.GREEN);
        gc.fillRect(x-25, y-12.5, 50, 25);
        gc.setFont(Font.font ("Verdana", 10));
        gc.setFill(Color.WHITESMOKE);
        gc.fillText(text, x-8, y);
    }

    private void drawMachine(GraphicsContext gc, int x, int y, Color color) {
        gc.setFill(color);
        int radius = 25;
        gc.fillOval(x-radius, y-radius, 2*radius, 2*radius);
        gc.setFont(Font.font ("Verdana", 10));
        gc.setFill(getContrastColor(color));
        gc.fillText("M", x-5, y);
    }

    private void drawProduct(GraphicsContext gc, int index, Color color) {
        gc.setFill(color);
        int radius = 20;
        System.err.println(50+50*index);
        gc.fillOval(50+50*index, canvas.getHeight()-50, 2*radius, 2*radius);
    }

    @FXML
    private void select(MouseEvent event){
        boolean selected=false;
        double mouse_x = event.getX();
        double mouse_y = event.getY();
        State currentState = Manager.getInstance().getCurrentState();
        if(currentState==null) return;
        double distance=0;
        Iterator<MyQueue> queueIterator = currentState.getQueues().iterator();
        while(queueIterator.hasNext()){
            MyQueue t = queueIterator.next();
            // TODO queue selection to be modified here
            if((mouse_x>=t.getCoordinates().x-25 && mouse_x<=t.getCoordinates().x+25)
                    || (mouse_y>=t.getCoordinates().y-12.5 && mouse_y<=t.getCoordinates().y-12.5)) {
                currentlySelected = t;
                selectionReference = new Point((int)event.getX(), (int)event.getY());
            }
        }
        Iterator<Machine> machineIterator = currentState.getMachines().iterator();
        while(machineIterator.hasNext()){
            Machine t = machineIterator.next();
            distance=Math.sqrt(Math.pow((mouse_x - t.getCoordinates().getX()), 2) + Math.pow((mouse_y - t.getCoordinates().getY()), 2));
            if(distance <= 25) {
                currentlySelected = t;
                selectionReference = new Point((int) event.getX(), (int) event.getY());
            }
        }
        // TODO connection adding code may go here
        // To prevent movement and deletion
        if (currentlySelected==source || currentlySelected==endStack) {
            currentlySelected = null;
        }
        if (deleteElementFlag && currentlySelected!= null) {
            currentState.getMachines().remove(currentlySelected);
            currentState.getQueues().remove(currentlySelected);
            currentlySelected = null;
            updateCanvas();
        }
    }

    private void connect(GraphicsContext gc){
        // Iterator<Point> pointsM= machines.listIterator();
        //Iterator<Point> pointsQ= queues.listIterator();
        gc.strokeLine(100-25,100,25+25,25);
        gc.setFill(Color.BLACK);
        
    }

    @FXML
    private void mouseDrag(MouseEvent event){
        if (currentlySelected != null) {
            Point centerLocation = currentlySelected.getCoordinates();
            currentlySelected.getCoordinates().x = centerLocation.x + (int)(event.getX()-selectionReference.x);
            currentlySelected.getCoordinates().y = centerLocation.y + (int)(event.getY()-selectionReference.y);
            selectionReference.x = (int)event.getX();
            selectionReference.y = (int)event.getY();
            updateCanvas();
        }
    }

    @FXML
    private void mouseRelease(MouseEvent event){
        currentlySelected = null;
    }

    public void updateCanvas() {
        State currentState = Manager.getInstance().getCurrentState();
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.getGraphicsContext2D().setStroke(Color.BLACK);
        canvas.getGraphicsContext2D().strokeRect(0,0, canvas.getWidth(), canvas.getHeight()-50);
        for (MyQueue temp : currentState.getQueues()) {
            drawQueue(canvas.getGraphicsContext2D(), temp.getCoordinates().x, temp.getCoordinates().y, temp.productsCount() + "");
        }
        drawQueue(canvas.getGraphicsContext2D(), endStack.getCoordinates().x, endStack.getCoordinates().y, "Stack");
        for (Machine temp : currentState.getMachines()) {
            drawMachine(canvas.getGraphicsContext2D(), temp.getCoordinates().x, temp.getCoordinates().y, temp.getColor());
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
        source = Manager.getInstance().addQueue(new Point((int)canvas.getWidth()-20,(int)canvas.getHeight()/2-20));
        endStack = Manager.getInstance().addQueue(new Point(20,(int)canvas.getHeight()/2 - 20));
        /*queues.add(new Point(100, 100));
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
        finishedProducts.add(Color.MEDIUMAQUAMARINE);*/
    }

    public Color getContrastColor(Color color) {
        return Color.rgb((int)(255-color.getRed()),
                (int)(255-color.getGreen()),
                (int)(255-color.getBlue()));
    }
}

