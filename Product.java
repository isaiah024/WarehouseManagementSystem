package warehouseproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


class Product implements Serializable{
    private static final long serialVersionUID = 1L;
    private String name;
    private String productID;
    private int quantity;
    private double price;
    private ArrayList<Client> waitlistedClients = new ArrayList<>();
    private ArrayList<ProductSupplierPair> suppliers = new ArrayList<>();

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
    
    public void addQuantity(int qty){
        this.quantity += qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList getWaitlistedClients() {
        return waitlistedClients;
    }

    public void addWaitlistedClients(Client client) {
        this.waitlistedClients.add(client);
    }
    
    public Iterator getSuppliers(){
        return suppliers.iterator();
    }
    
    public ArrayList getSupplierList(){
        return suppliers;
    }

    @Override
    public String toString() {
        return "Product:" + " name = " + getName() + ", productID = " + getProductID() + ", quantity = " + getQuantity() + ", price = " + getPrice() + ", supplierList = " + Arrays.toString(getSupplierList().toArray()) + ", waitlistedClients = " + Arrays.toString(getWaitlistedClients().toArray());
    }
    
    //Add a supplier to the supplierList
    public boolean addSupplier(String supplierID, double price){
        ProductSupplierPair pair = new ProductSupplierPair(this.productID, supplierID, price);
        return suppliers.add(pair);
    }
    
    //Checks if the supplier already exixts for the product
    public boolean checkSupplier(String supplierID){
        Iterator allSupplierPairs = getSuppliers();
        while(allSupplierPairs.hasNext()){
            ProductSupplierPair pair = (ProductSupplierPair) allSupplierPairs.next();
            if(supplierID.equals(pair.getSupplierID()))
                return true;
        }
        return false;
    }
}
