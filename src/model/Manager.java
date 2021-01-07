package model;

import java.util.ArrayList;

public class Manager {

    ArrayList<Product> products = new ArrayList<>();
    final private Manager instance = new Manager();


    private Manager() {
    }

    public Manager getInstance() {
        return this.instance;
    }
}
