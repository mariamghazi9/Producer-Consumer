package controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Simulator {
    @FXML
    Canvas canvas;
    List circles=new ArrayList<Point>();

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
        circles.add(new Point(x,y));
        gc.setFill(Color.GREEN);
        int radius = 25;
        gc.fillOval(x-radius, y-radius, 2*radius, 2*radius);
        gc.setFont(Font.font ("Verdana", 10));
        gc.setFill(Color.WHITESMOKE);
        gc.fillText("M1", x-8, y);
    }
    @FXML
    private void select(MouseEvent event){
        boolean selected=false;
        double mouse_x = event.getX();
        double mouse_y = event.getY();
        double distance=0;
        Iterator<Point> points= circles.listIterator();
        while(points.hasNext()){
            Point t = points.next();
            distance=Math.sqrt(Math.pow((mouse_x - t.getX()), 2) + Math.pow((mouse_y - t.getY()), 2));
            if(distance <= 25)
                selected=true;
        }
        System.out.println(distance);
        System.out.println(selected);
    }

}

