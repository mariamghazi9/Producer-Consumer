package controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Simulator {

    @FXML
    Canvas canvas;


    @FXML
    public void clicked(MouseEvent event) {
        if(event.getClickCount() == 2){
            System.out.println("Double clicked");
            addMachine(canvas.getGraphicsContext2D(), (int)event.getX(), (int)event.getY());
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
        gc.setFill(Color.GREEN);
        int radius = 25;
        gc.fillOval(x-radius, y-radius, 2*radius, 2*radius);
        gc.setFont(Font.font ("Verdana", 10));
        gc.setFill(Color.WHITESMOKE);
        gc.fillText("M1", x-8, y);
    }

}

