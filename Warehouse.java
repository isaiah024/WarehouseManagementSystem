package warehouseproject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;

//Still need to fix the retrieve and save features. Can't find the file after its been saved
public class Warehouse implements Serializable{
    private static final long serialVersionUID = 1L;
    private static Warehouse warehouse;
    private ClientList clientList;
    private ProductList productList;
    private SupplierList supplierList;

    //instantaniate all singletons that are in this class
    public Warehouse() {
        clientList = ClientList.instance();
        productList = ProductList.instance();
        supplierList = SupplierList.instance();
    }
    
    public static Warehouse instance(){
        if(warehouse == null){
            // instantiate all singletons that are not variables in this class
            ClientIDServer.instance();
            SupplierIDServer.instance();
            return (warehouse = new Warehouse());
        }else{
            return warehouse;
        }
    }
    
    //Retrieves the saved data from previous sessions
    public static Warehouse retrieve() {
        try {
            FileInputStream file = new FileInputStream("WarehouseData");
            ObjectInputStream input = new ObjectInputStream(file);
            input.readObject();
            ClientIDServer.retrieve(input);
            SupplierIDServer.retrieve(input);
            return warehouse;
        }catch(IOException ioe){
            ioe.printStackTrace();
            return null;
        }catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
            return null;
        }
    }
    
    //Save the data
    public static  boolean save() {
        try {
            FileOutputStream file = new FileOutputStream("WarehouseData");
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(warehouse);
            output.writeObject(ClientIDServer.instance());
            output.writeObject(SupplierIDServer.instance());
            return true;
        }catch(IOException ioe){
            ioe.printStackTrace();
            return false;
        }
  }
    
    //Add client to clientList
    public Client addClient(String name) {
        Client client = new Client(name);
        if (clientList.insertClient(client)) {
            return (client);
        }
        return null;
    }
    
    //Add product to productList
    public Product addProduct(String supplierID, String supplyPrice, String name, String productID, String tempQuantity, String tempPrice) {
        int qty = Integer.parseInt(tempQuantity);
        double price = Double.parseDouble(tempPrice);
        Product product = productList.getProduct(productID);
        Supplier supplier = supplierList.getSupplier(supplierID);
        //If productsPair doesn't exist
        if(supplier == null)
            return null;
        //If product doesn't exist
        if(product == null){
            product = new Product(name, productID, qty, price);
            if(productList.insertProduct(product)) {
                product.addSupplier(supplierID, Double.parseDouble(supplyPrice));
                supplier.addProduct(productID, Double.parseDouble(supplyPrice));
                return product;
            }
        }
        //If product does exist and productsPair doesn't exist for that product
        else if(supplier != null && !product.checkSupplier(supplier.getSupplierID())){
            product.addSupplier(supplierID, Double.parseDouble(supplyPrice));
            supplier.addProduct(productID, Double.parseDouble(supplyPrice));
            product.addQuantity(qty);
            return product;
        }
        return null;
    }
    
    //Add productsPair to supplierList
    public Supplier addSupplier(String name) {
        Supplier supplier = new Supplier(name);
        if (supplierList.insertSupplier(supplier)) {
            return (supplier);
        }
        return null;
    }
    
    public boolean addToClientCart(String client_id, String product_id, int quantity){
        Client client = clientList.getClient(client_id);
        Product product = productList.getProduct(product_id);
        if(client == null || product == null)
            return false;
        return client.addToCart(product, quantity);
    }

    public Iterator getCartContents(String client_id){

        Client client = clientList.getClient(client_id);
        return client.getCartContents();
    }
    
    //Gets clients ArrayList iterator
    public Iterator getClients(){
        return clientList.getClients();
    }
    
    //Gets products ArrayList iterator
    public Iterator getProducts(){
        return productList.getProducts();
    }
    
    //Gets suppliers ArrayList iterator
    public Iterator getSuppliers(){
        return supplierList.getSuppliers();
    }
    
    //Gets a products suppliers list
    public Iterator getProductSuppliers(String productID){
        Product product = productList.getProduct(productID);
        return product.getSuppliers();
    }
    
    //Gets a products suppliers list
    public Iterator getSupplierProducts(String supplierID){
        Supplier supplier = supplierList.getSupplier(supplierID);
        return supplier.getProducts();
    }
    
    public ProductSupplierPair findSupplierProduct(String productID, Iterator suppliersProducts){
        ProductSupplierPair suppliersPair = new ProductSupplierPair();
        while(suppliersProducts.hasNext()){
            suppliersPair = (ProductSupplierPair) suppliersProducts.next();
            if(suppliersPair.getSupplierID().equals(productID)){
                break;
            }
        }
        return suppliersPair;
    }
    
    public ProductSupplierPair findProductSupplier(String supplierID, Iterator productsSuppliers){
        ProductSupplierPair productsPair = new ProductSupplierPair();
        while(productsSuppliers.hasNext()){
            productsPair = (ProductSupplierPair) productsSuppliers.next();
            if(productsPair.getSupplierID().equals(supplierID)){
                break;
            }
        }
        return productsPair;
    }
    
    //Change the products sale price
    public Product changeSalePrice(String productID, double newPrice){
        Product result = productList.getProduct(productID);
        result.setPrice(newPrice);
        return result;
        }
    
    //Change the clients name
    public Client changeName(String id, String name){
        Client client = clientList.getClient(id);
        client.setName(name);
        return client;
    }
    
    //Change the products qty available to sell
    public Product changeQty(String id, int qty){
        Product product = productList.getProduct(id);
        product.setQuantity(qty);
        return product;
    }
}
