
package warehouseproject;

class ProductSupplierPair {
    private String productID;
    private String supplierID;
    private double supplyPrice;

    public ProductSupplierPair() {
    }
    
    ProductSupplierPair(String productID, String supplierID, double price) {
        this.productID = productID;
        this.supplierID = supplierID;
        this.supplyPrice = price;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public double getSalePrice() {
        return supplyPrice;
    }

    public void setSalePrice(double price) {
        this.supplyPrice = price;
    }

    @Override
    public String toString() {
        return "ProductSupplierPair{" + "productID=" + productID + ", supplierID=" + supplierID + ", price=" + supplyPrice + '}';
    }
    
    
    
}
