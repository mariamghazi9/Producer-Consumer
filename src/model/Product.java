package model;

import java.awt.*;
import java.util.Random;

public class Product {

    final Color color;
    final int delayTime;

    public Product() {
        Random rand = new Random();
        color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
        delayTime = rand.nextInt(); //TODO set max and min
    }
}
