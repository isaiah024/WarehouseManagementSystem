package warehouseproject;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.Arrays;



class Supplier implements Serializable{
    private static final long serialVersionUID = 1L;
    private String supplierID;
    private static final String MEMBER_STRING = "S";
    private String supplierName;
    private ArrayList<Product> productList = new ArrayList<Product>();

    public Supplier(String supplierName) {
        this.supplierID = MEMBER_STRING + (SupplierIDServer.instance()).getId();
        this.supplierName = supplierName;
    }
    
    public String getSupplierID(){
        return supplierID;
    }
    
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public ArrayList getProductList() {
        return productList;
    }

    public void addProductToList(Product product) {
        productList.add(product);
    }

    @Override
    public String toString() {
        return "Supplier:" + " supplierID = " + getSupplierID() + ", supplierName = " + getSupplierName() + ", productList = " + Arrays.toString(getProductList().toArray());
    }
    
}
