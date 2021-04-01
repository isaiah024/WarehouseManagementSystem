package warehouseproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String productID;
    private int quantity;
    private double price;
    private ArrayList<ProductSupplierPair> suppliers = new ArrayList<>();
    private ArrayList<WaitlistProduct> waitlist = new ArrayList<WaitlistProduct>();

    // Would I need to create a productID or have the supplier provide the
    // productID?
    public Product(String name, String productID, int quantity, double price) {
        this.name = name;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
    }

    // copy constructor
    public Product(Product p) {
        this(p.getName(), p.getProductID(), 0, p.getPrice());
    }

    // Functions:
    public String getName() {
        return name;
    }

    public void addQuantity(int qty) {
        this.quantity += qty;
    }

    public Iterator getSuppliers() {
        return suppliers.iterator();
    }

    public ArrayList getSupplierList() {
        return suppliers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String id) {
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

    public Iterator getWaitlist() {
        return waitlist.iterator();
    }

    public void addToWaitlist(WaitlistProduct product) {
        this.waitlist.add(product);
    }
    
    public void removeWaitlist(WaitlistProduct waitlistClient){
        waitlist.remove(waitlistClient);
    }

    // Add a supplier to the supplierList
    public boolean addSupplier(String supplierID, double price) {
        ProductSupplierPair pair = new ProductSupplierPair(this.productID, supplierID, price);
        return suppliers.add(pair);
    }

    // Checks if the supplier already exixts for the product
    public boolean checkSupplier(String supplierID) {
        Iterator allSupplierPairs = getSuppliers();
        while (allSupplierPairs.hasNext()) {
            ProductSupplierPair pair = (ProductSupplierPair) allSupplierPairs.next();
            if (supplierID.equals(pair.getSupplierID()))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Product:" + " name = " + getName() + ", productID = " + getProductID() + ", quantity = " + getQuantity()
                + ", price = " + getPrice();
    }
}