package model;

import java.util.ArrayList;

public class Manager {

    ArrayList<Product> products = new ArrayList<>();
    final private Manager instance = new Manager();
    private int productsNumber;

    private Manager() {
    }

    public Manager getInstance() {
        return this.instance;
    }

    public int getProductsNumber() {
        return productsNumber;
    }

    public void setProductsNumber(int productsNumber) {
        this.productsNumber = productsNumber;
    }
}
