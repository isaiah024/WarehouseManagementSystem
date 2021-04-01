package warehouseproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

//Modifying file with comment 
class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Product> products = new ArrayList<>();

    public boolean addToCart(Product product, int quantity) {
        // Create a copy of the product object using the copy constructor
        Product copiedProduct = new Product(product);
        copiedProduct.setQuantity(quantity);
        return products.add(copiedProduct);
    }

    public boolean removeFromCart(String product_id, int quantity){
        Iterator it = products.iterator();

        while(it.hasNext()){
            Product p = (Product)it.next();
            System.out.println("p.getProductID is: " + p.getProductID() + " product_id is: " + product_id);
            if(p.getProductID().equals(product_id)){
                int newQuantity = p.getQuantity() - quantity;
                if(newQuantity <= 0){
                    it.remove();
                    return true;
                }

                else{
                    p.setQuantity(newQuantity);
                    return true;
                }
            }
        }

        return false;
    }

    public Iterator getCartContents() {
        return products.iterator();
    }

    @Override
    public String toString() {
        String productString = "";
        for (Product p : products) {
            productString += "\n" + p;
        }
        return productString;
    }

}