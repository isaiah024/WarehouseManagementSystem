package warehouseproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

class Cart implements Serializable{
    private static final long serialVersionUID = 1L;
    private ArrayList<Product> cartProducts = new ArrayList<>();
    private ArrayList<Product> stockProducts = new ArrayList<>();
    
    public boolean addToCart(Product product, int quantity){
        //Create a copy of the product object using the copy constructor
        Product copiedProduct = new Product(product);
        copiedProduct.setQuantity(quantity);
        return cartProducts.add(copiedProduct);
    }

    public Iterator getCartContents(){
        return cartProducts.iterator();
    }
    @Override
    public String toString(){
        String productString = "";
        for(Product p : cartProducts){
            productString += "\n" + p;
        }
        return productString;
    }

    
    
}