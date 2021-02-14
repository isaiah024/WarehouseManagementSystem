package warehouseproject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;

//Still need to fix the retrieve and save features. Can't find the file after its been saved
public class Warehouse implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Warehouse warehouse;
    private ClientList clientList;
    private ProductList productList;
    private SupplierList supplierList;

    // instantaniate all singletons that are in this class
    public Warehouse() {
        clientList = ClientList.instance();
        productList = ProductList.instance();
        supplierList = SupplierList.instance();
    }

    public static Warehouse instance() {
        if (warehouse == null) {
            // instantiate all singletons that are not variables in this class
            ClientIDServer.instance();
            SupplierIDServer.instance();
            return (warehouse = new Warehouse());
        } else {
            return warehouse;
        }
    }

    // Retrieves the saved data from previous sessions
    public static Warehouse retrieve() {
        try {
            FileInputStream file = new FileInputStream("WarehouseData");
            ObjectInputStream input = new ObjectInputStream(file);
            input.readObject();
            ClientIDServer.retrieve(input);
            SupplierIDServer.retrieve(input);
            return warehouse;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            return null;
        }
    }

    // Save the data
    public static boolean save() {
        try {
            FileOutputStream file = new FileOutputStream("WarehouseData");
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(warehouse);
            output.writeObject(ClientIDServer.instance());
            output.writeObject(SupplierIDServer.instance());
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    // Add client to clientList
    public Client addClient(String name) {
        Client client = new Client(name);
        if (clientList.insertClient(client)) {
            return (client);
        }
        return null;
    }

    // Add product to productList
    public Product addProduct(String name, String productID, String tempQuantity, String tempPrice) {
        int quantity = Integer.parseInt(tempQuantity);
        double price = Double.parseDouble(tempPrice);
        Product product = new Product(name, productID, quantity, price);
        if (productList.insertProduct(product)) {
            return (product);
        }
        return null;
    }

    // Add supplier to supplierList
    public Supplier addSupplier(String name) {
        Supplier supplier = new Supplier(name);
        if (supplierList.insertSupplier(supplier)) {
            return (supplier);
        }
        return null;
    }

    // Gets clients ArrayList iterator
    public Iterator getClients() {
        return clientList.getClients();
    }

    // Gets products ArrayList iterator
    public Iterator getProducts() {
        return productList.getProducts();
    }

    // Gets suppliers ArrayList iterator
    public Iterator getSuppliers() {
        return supplierList.getSuppliers();
    }

    public boolean addToClientCart(String client_id, String product_id, int quantity){
        Client client = clientList.find(client_id);
        Product product = productList.find(product_id);
        return client.addToCart(product, quantity);
    }

    public Iterator getCartContents(String client_id){

        Client client = clientList.find(client_id);
        return client.getCartContents();
    }
}

