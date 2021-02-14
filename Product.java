package warehouseproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


class Product implements Serializable{
    private static final long serialVersionUID = 1L;
    private String name;
    private String productID;
    private int quantity;
    private double price;
    private ArrayList<Supplier> supplierList = new ArrayList<Supplier>();
    private ArrayList<Client> waitlistedClients = new ArrayList<Client>();

    //Would I need to create a productID or have the supplier provide the productID?
    public Product(String name, String productID, int quantity, double price) {
        this.name = name;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
    }
    
    //copy constructor
    public Product(Product p) {
        this(p.getName(), p.getProductID(), 0, p.getPrice());
    }
    
    //Functions:
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductID() {
        return productID;
    }
    
    public void setProductID(String id){
        this.productID = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList getSupplierList() {
        return supplierList;
    }

    public void addSupplierToList(Supplier supplier) {
        this.supplierList.add(supplier);
    }

    public ArrayList getWaitlistedClients() {
        return waitlistedClients;
    }

    public void addWaitlistedClients(Client client) {
        this.waitlistedClients.add(client);
    }

    @Override
    public String toString() {
        return "Product:" + " name = " + getName() + ", productID = " + getProductID() + ", quantity = " + getQuantity() + ", price = " + getPrice() + ", supplierList = " + Arrays.toString(getSupplierList().toArray()) + ", waitlistedClients = " + Arrays.toString(getWaitlistedClients().toArray());
    }
    
    //Implement
    public void addSupplierPair(ProductSupplierPair pair){
        
    }
}
