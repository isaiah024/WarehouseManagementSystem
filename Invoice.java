/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package warehouseproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Chandler
 */
public class Invoice implements Serializable{
    private static final long serialVersionUID = 1L;
    private double totalCost;
    private ArrayList<Product> shippedProducts;

    public Invoice() {
        shippedProducts = new ArrayList<Product>();
        totalCost = 0;
    }

    public void addProduct(Product p) {
        totalCost = totalCost + p.getPrice() * p.getQuantity();
        shippedProducts.add(p);
    }

    public String toString() {
        Iterator it = shippedProducts.iterator();
        String s = "";
        while (it.hasNext()) {
            Product p = (Product) (it.next());
            s = s + ("\nProduct id: " + p.getProductID() + " Price: " + p.getPrice() + " Quantity: " + p.getQuantity());
        }

        s = s + "\nTotal cost: " + totalCost;
        return s;
    }

    public Iterator getInvoiceProducts() {
        return shippedProducts.iterator();
    }

    public double getTotalCost() {
        return totalCost;
    }

}
