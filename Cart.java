package warehouseproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

class Cart implements Serializable{
    private static final long serialVersionUID = 1L;
    private ArrayList<Product> products = new ArrayList<Product>();
    
    public boolean addToCart(Product product, int quantity){
        
        //Create a copy of the product object using the copy constructor
        Product copiedProduct = new Product(product);
        copiedProduct.setQuantity(quantity);
        return products.add(copiedProduct);
    }

    public Iterator getCartContents(){
        return products.iterator();
    }
    @Override
    public String toString(){
        String productString = "";
        for(Product p : products){
            productString += "\n" + p;
        }
        return productString;
    }

    
    
}
