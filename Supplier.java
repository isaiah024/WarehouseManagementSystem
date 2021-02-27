package warehouseproject;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

class Supplier implements Serializable {
    private static final long serialVersionUID = 1L;
    private String supplierID;
    private static final String MEMBER_STRING = "S";
    private String supplierName;
    private ArrayList<ProductSupplierPair> products = new ArrayList<>();

    public Supplier(String supplierName) {
        this.supplierID = MEMBER_STRING + (SupplierIDServer.instance()).getId();
        this.supplierName = supplierName;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public ArrayList getProductList() {
        return products;
    }

    // Adds a product to the productList
    public boolean addProduct(String productID, double price) {
        ProductSupplierPair pair = new ProductSupplierPair(productID, this.supplierID, price);
        return products.add(pair);
    }

    public Iterator getProducts() {
        return products.iterator();
    }

    // Checks if a product already exists in the productList
    public boolean checkProduct(String productID) {
        Iterator allProductPairs = getProducts();
        while (allProductPairs.hasNext()) {
            ProductSupplierPair pair = (ProductSupplierPair) allProductPairs.next();
            if (productID.equals(pair.getProductID()))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Supplier:" + " supplierID = " + getSupplierID() + ", supplierName = " + getSupplierName()
                + ", productList = " + Arrays.toString(getProductList().toArray());
    }

}
