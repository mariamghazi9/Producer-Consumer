package model;

import javafx.scene.paint.Color;

import java.util.Random;

public class Product {

    private final Color color;
    private final int delayTime;

    public Product() {
        final int low = 2000;
        final int high = 10000;
        Random rand = new Random();
        color = Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255), 0.8);
        delayTime = rand.nextInt(high-low) + low;
    }

    public Color getColor() {
        return color;
    }

    public int getDelayTime() {
        return delayTime;
    }
}
