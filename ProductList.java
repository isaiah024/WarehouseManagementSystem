package warehouseproject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class ProductList implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Product> products = new ArrayList<Product>();
    private static ProductList productList;
    
    public ProductList(){
    }
    
    //Singleton class initializes
    public static ProductList instance() {
        if (productList == null) {
            return (productList = new ProductList());
        }else{
            return productList;
        }
    }
    
    public boolean insertProduct(Product product){
        this.products.add(product);
        return true;
    }
    
    public Iterator getClients(){
        return products.iterator();
    }
    
    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(productList);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    private void readObject(java.io.ObjectInputStream input) {
        try {
            if(productList != null) {
                return;
            }else{
                input.defaultReadObject();
                if(productList == null) {
                    productList = (ProductList) input.readObject();
                }else{
                    input.readObject();
                }
            }
        }catch(IOException ioe) {
        ioe.printStackTrace();
        }catch(ClassNotFoundException cnfe) {
        cnfe.printStackTrace();
        }
    }
    
}
