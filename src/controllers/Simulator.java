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

import java.net.URL;
import java.util.ResourceBundle;


public class Simulator implements Initializable {

    @FXML
    Canvas canvas;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addQueue.getStyleClass().add("selected");
    }
}

