package warehouseproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Client implements Serializable{
    private static final long serialVersionUID = 1L;
    private String name;
    private String clientID;
    private static final String MEMBER_STRING = "C";
    private Cart cart = new Cart();
    private ArrayList<Product> waitlistedProducts = new ArrayList<Product>();

    public Client(String name) {
        this.name = name;
        this.clientID = MEMBER_STRING + (ClientIDServer.instance()).getId();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return clientID;
    }

    public ArrayList getWaitlistedItems() {
        return waitlistedProducts;
    }

    public void addWaitlistedItems(Product product) {
        waitlistedProducts.add(product);
    }
    
    public boolean addToCart(Product product, int quantity){
       return cart.addToCart(product, quantity);
    }

    public Iterator getCartContents(){
       return cart.getCartContents();
    }

    @Override
    public String toString() {
        return "Client: " + "name = " + getName() + ", id = " + getId() + ", waitlisted items = " + Arrays.toString(getWaitlistedItems().toArray());
    }
    
    
    
}

