package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
    Button playButton;
    @FXML
    Button deleteConnectionButton;
    @FXML
    Button addConnectionButton;
    @FXML
    Button deleteElementButton;
    @FXML
    Button addQueue;
    @FXML
    Button addMachine;
    @FXML
    TextField productsNumText;
    @FXML
    Button newSimulationButton;
    @FXML
    Button reSimulationButton;
    @FXML
    ChoiceBox<Integer> stateChoiceBox;


    private boolean deleteElementFlag = false;
    private boolean deleteConnectionFlag = false;
    private boolean addConnectionFlag = false;
    private boolean isQueue = true;
    private MyQueue source;
    private MyQueue endStack;
    private MyQueue selectedQueue;
    private Machine selectedMachine;
    private Graphical currentlySelected;
    private Point selectionReference;



    @FXML
    public void clicked(MouseEvent event) {
        if(event.getClickCount() == 2){
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
            deleteConnectionButton.getStyleClass().add("selected");
            if (addConnectionFlag) {
                addConnectionButton.getStyleClass().remove(1);
                addConnectionFlag = false;
            }
        }
        deleteConnectionFlag = !deleteConnectionFlag;
    }

    @FXML
    public void deleteElement() {
        if (deleteElementFlag) {
            deleteElementButton.getStyleClass().remove(1);
        } else {
            deleteElementButton.getStyleClass().add("selected");
        }
        deleteElementFlag = !deleteElementFlag;
    }
    @FXML
    public void addConnection() {
        if (addConnectionFlag) {
            addConnectionButton.getStyleClass().remove(1);
        } else {
            addConnectionButton.getStyleClass().add("selected");
            if (deleteConnectionFlag) {
                deleteConnectionButton.getStyleClass().remove(1);
                deleteConnectionFlag = false;
            }
        }
        addConnectionFlag = !addConnectionFlag;
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
        gc.setFill(Color.rgb(0,128,0, 0.8));
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
        if (!color.equals(Color.rgb(183, 227, 255, 0.8))) {
            gc.fillText("...", x-5, y+10);
        }
        gc.fillText("M", x-5, y);
    }

    private void drawProduct(GraphicsContext gc, int index, Color color) {
        gc.setFill(color);
        int radius = 20;
        gc.fillOval(50+50*index, canvas.getHeight()-50, 2*radius, 2*radius);
    }

    @FXML
    private void select(MouseEvent event){
        double mouse_x = event.getX();
        double mouse_y = event.getY();
        State currentState = Manager.getInstance().getCurrentState();
        if(currentState==null) return;
        double distance;
        int in = 0;
        for (MyQueue t : currentState.getQueues()) {
            in++;
            if ((mouse_x >= t.getCoordinates().x - 25 && mouse_x <= t.getCoordinates().x + 25)
                    && (mouse_y >= t.getCoordinates().y - 12.5 && mouse_y <= t.getCoordinates().y + 12.5)) {
                currentlySelected = t;
                selectionReference = new Point((int) event.getSceneX(), (int) event.getSceneY());
                selectedQueue = t;
                connection(selectedQueue);
                updateCanvas();
                break;
            }
        }
        for (Machine t : currentState.getMachines()) {
            distance = Math.sqrt(Math.pow((mouse_x - t.getCoordinates().getX()), 2) + Math.pow((mouse_y - t.getCoordinates().getY()), 2));
            if (distance <= 25) {
                currentlySelected = t;
                selectionReference = new Point((int) event.getSceneX(), (int) event.getSceneY());
                selectedMachine = t;
                connection(selectedMachine);
                updateCanvas();
            }
        }
        if (deleteElementFlag && currentlySelected!= null) {
            deleteSelectedObject(currentlySelected, currentState);
            currentlySelected = null;
            updateCanvas();
        }
        // To prevent movement and deletion
        if (currentlySelected==source || currentlySelected==endStack) {
            currentlySelected = null;
        }
    }

    private void deleteSelectedObject(Graphical selectedObject, State currentState){
        try {
            Machine machine = (Machine) selectedObject;
            for (Source source: machine.getSources()) {
                source.getReadyMachines().remove(machine);
                source.getMachines().remove(machine);
            }
            currentState.getMachines().remove(machine);
        } catch (Exception e) {
            MyQueue queue = (MyQueue) selectedObject;
            currentState.getQueues().remove(queue);
        }
    }


    private void connection(MyQueue queue){
        if (addConnectionFlag) {
            if (selectedMachine != null) {
                selectedMachine.setConsumer(queue);
                selectedMachine = null;
                selectedQueue = null;
            }
        }
        else if (deleteConnectionFlag){
            if (selectedMachine != null) {
                selectedMachine.removeConsumer(queue);
                selectedMachine = null;
                selectedQueue = null;
            }
        }
    }

    private void connection(Machine machine){
        if (addConnectionFlag) {
            if (selectedQueue != null) {
                selectedQueue.addMachine(machine);
                machine.addSource(selectedQueue);
                selectedMachine = null;
                selectedQueue = null;
            }
        }
        else if (deleteConnectionFlag){
            if (selectedQueue != null) {
                selectedQueue.getMachines().remove(machine);
                selectedMachine = null;
                selectedQueue = null;
            }
        }
    }

    @FXML
    private void mouseDrag(MouseEvent event){
        if (currentlySelected != null) {
            Point centerLocation = currentlySelected.getCoordinates();
            currentlySelected.getCoordinates().x = centerLocation.x + (int)(event.getSceneX()-selectionReference.x);
            currentlySelected.getCoordinates().y = centerLocation.y + (int)(event.getSceneY()-selectionReference.y);
            selectionReference.x = (int)event.getSceneX();
            selectionReference.y = (int)event.getSceneY();
            updateCanvas();
        }
    }

    @FXML
    private void mouseRelease(){
        currentlySelected = null;
    }

    public void updateCanvas() {
        State currentState = Manager.getInstance().getCurrentState();
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.getGraphicsContext2D().setStroke(Color.BLACK);
        canvas.getGraphicsContext2D().strokeRect(0,0, canvas.getWidth(), canvas.getHeight()-53);
        for (MyQueue queue : currentState.getQueues()) {
            drawQueue(canvas.getGraphicsContext2D(), queue.getCoordinates().x, queue.getCoordinates().y, String.valueOf(queue.productsCount()));
            for (int i = 0; i < queue.getMachines().size(); i++) {
                canvas.getGraphicsContext2D().strokeLine(queue.getCoordinates().getX() - 25, queue.getCoordinates().getY(), queue.getMachines().get(i).getCoordinates().getX() + 25, queue.getMachines().get(i).getCoordinates().getY());
                canvas.getGraphicsContext2D().setFill(Color.BLACK);
            }
        }
        drawQueue(canvas.getGraphicsContext2D(), endStack.getCoordinates().x, endStack.getCoordinates().y, "Stack");
        for (Machine temp : currentState.getMachines()) {
            drawMachine(canvas.getGraphicsContext2D(), temp.getCoordinates().x, temp.getCoordinates().y, temp.getColor());
            if (temp.getConsumer() != null){
                canvas.getGraphicsContext2D().strokeLine(temp.getCoordinates().getX() -25, temp.getCoordinates().getY(), ((MyQueue)temp.getConsumer()).getCoordinates().getX() + 25, ((MyQueue)temp.getConsumer()).getCoordinates().getY());
                canvas.getGraphicsContext2D().setFill(Color.BLACK);
            }
        }
        if (endStack.getProductsQueue() != null) {
            int index = 0;
            for (Product product : endStack.getProductsQueue()) {
                Color temp = product.getColor();
                drawProduct(canvas.getGraphicsContext2D(), index, temp);
                index++;
            }
            if (index == Manager.getInstance().getProductsNumber()) { simulationFinished();}
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Manager.getInstance().setController(this);
        addQueue.getStyleClass().add("selected");
        source = Manager.getInstance().addQueue(new Point((int)canvas.getWidth()-20,(int)canvas.getHeight()/2-20));
        endStack = Manager.getInstance().addQueue(new Point(20,(int)canvas.getHeight()/2 - 20));
        newSimulationButton.setVisible(false);
        reSimulationButton.setVisible(false);
        stateChoiceBox.setVisible(false);
        updateCanvas();
    }

    @FXML
    public void play() {
        try {
            int productsNumber = Integer.parseInt(productsNumText.getText());
            Manager.getInstance().setProductsNumber(productsNumber);
        } catch (Exception e) {
            System.err.println("Moshkla fen el products number ?!!");
            return;
        }
        canvas.setDisable(true);
        playButton.setVisible(false);
        productsNumText.setVisible(false);
        productsNumText.setText("");
        Manager.getInstance().startSimulation();
        updateCanvas();
    }


    public void simulationFinished() {
        stateChoiceBox.getItems().clear();
        for (int i = 0; i < Manager.getInstance().getSavedStates().size(); i++) {
            stateChoiceBox.getItems().add(i+1);
        }
        stateChoiceBox.setVisible(true);
        reSimulationButton.setVisible(true);
        newSimulationButton.setVisible(true);
    }

    public void newSimulation() {
        stateChoiceBox.setVisible(false);
        reSimulationButton.setVisible(false);
        newSimulationButton.setVisible(false);
        canvas.setDisable(false);
        playButton.setVisible(true);
        productsNumText.setVisible(true);
        Manager.getInstance().newState();
    }

    public void replaySimulation() {
        if (!stateChoiceBox.getSelectionModel().isEmpty()) {
            stateChoiceBox.setVisible(false);
            reSimulationButton.setVisible(false);
            newSimulationButton.setVisible(false);
            Manager.getInstance().play(stateChoiceBox.getSelectionModel().getSelectedItem()-1);
            Manager.getInstance().startSimulation();
        }
    }

    public Color getContrastColor(Color color) {
        return Color.rgb((int)(255-color.getRed()),
                (int)(255-color.getGreen()),
                (int)(255-color.getBlue()));
    }
}

