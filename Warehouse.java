package warehouseproject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

//Still need to fix the retrieve and save features. Can't find the file after its been saved
public class Warehouse implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Warehouse warehouse;
    private ClientList clientList;
    private ProductList productList;
    private SupplierList supplierList;
    private ArrayList<WaitlistProduct> removeWaitlist = new ArrayList<>();

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
    public Product addProduct(String supplierID, String supplyPrice, String name, String productID, String tempQuantity,
            String tempPrice) {
        int qty = Integer.parseInt(tempQuantity);
        double price = Double.parseDouble(tempPrice);
        Product product = productList.getProduct(productID);
        Supplier supplier = supplierList.getSupplier(supplierID);
        // If productsPair doesn't exist
        if (supplier == null)
            return null;
        // If product doesn't exist
        if (product == null) {
            product = new Product(name, productID, qty, price);
            if (productList.insertProduct(product)) {
                product.addSupplier(supplierID, Double.parseDouble(supplyPrice));
                supplier.addProduct(productID, Double.parseDouble(supplyPrice));
                return product;
            }
        }
        // If product does exist and productsPair doesn't exist for that product
        else if (supplier != null && !product.checkSupplier(supplier.getSupplierID())) {
            product.addSupplier(supplierID, Double.parseDouble(supplyPrice));
            supplier.addProduct(productID, Double.parseDouble(supplyPrice));
            product.addQuantity(qty);
            return product;
        }
        //If product does exist and productPair does exist
        else if(supplier != null && product.checkSupplier(supplier.getSupplierID()))
            System.out.println("Product already exists with the supplier. To add to the products quantity use a different command.");
        return null;
    }

    // Add productsPair to supplierList
    public Supplier addSupplier(String name) {
        Supplier supplier = new Supplier(name);
        if (supplierList.insertSupplier(supplier)) {
            return (supplier);
        }
        return null;
    }

    public boolean addToClientCart(String client_id, String product_id, int quantity) {
        Client client = clientList.getClient(client_id);
        Product product = productList.getProduct(product_id);
        if (client == null || product == null)
            return false;
        return client.addToCart(product, quantity);
    }

    public boolean removeFromClientCart(String client_id, String product_id, int quantity) {
        Client client = clientList.getClient(client_id);
        if (client == null)
            return false;
        return client.removeFromCart(product_id, quantity);
    }

    public Iterator getCartContents(String client_id) {

        Client client = clientList.getClient(client_id);
        return client.getCartContents();
    }

    // Gets clients ArrayList iterator
    public Iterator getClients() {
        return clientList.getClients();
    }

    public Client searchMembership(String client_id) {
        return clientList.getClient(client_id);
    }

    // Gets products ArrayList iterator
    public Iterator getProducts() {
        return productList.getProducts();
    }

    public Product getProduct(String productID){
        return productList.getProduct(productID);
    }
    
    // Gets suppliers ArrayList iterator
    public Iterator getSuppliers() {
        return supplierList.getSuppliers();
    }

    // Gets a products suppliers list
    public Iterator getProductSuppliers(String productID) {
        Product product = productList.getProduct(productID);
        return product.getSuppliers();
    }

    // Gets a products suppliers list
    public Iterator getSupplierProducts(String supplierID) {
        Supplier supplier = supplierList.getSupplier(supplierID);
        return supplier.getProducts();
    }

    // Change the products price
    public Product changePrice(String id, double newPrice) {
        Product product = productList.getProduct(id);
        product.setPrice(newPrice);
        return product;
    }

    // Change the products qty available to sell
    public Product changeQty(String id, int qty) {
        Product product = productList.getProduct(id);
        product.setQuantity(qty);
        return product;
    }

    public ProductSupplierPair findSupplierProduct(String productID, Iterator suppliersProducts) {
        ProductSupplierPair suppliersPair = new ProductSupplierPair();
        while (suppliersProducts.hasNext()) {
            suppliersPair = (ProductSupplierPair) suppliersProducts.next();
            if (suppliersPair.getSupplierID().equals(productID)) {
                break;
            }
        }
        return suppliersPair;
    }

    public ProductSupplierPair findProductSupplier(String supplierID, Iterator productsSuppliers) {
        ProductSupplierPair productsPair = new ProductSupplierPair();
        while (productsSuppliers.hasNext()) {
            productsPair = (ProductSupplierPair) productsSuppliers.next();
            if (productsPair.getSupplierID().equals(supplierID)) {
                break;
            }
        }
        return productsPair;
    }

    // Change the products sale price
    public Product changeSalePrice(String productID, double newPrice) {
        Product result = productList.getProduct(productID);
        result.setPrice(newPrice);
        return result;
    }

    // Change the clients name
    public Client changeName(String id, String name) {
        Client client = clientList.getClient(id);
        client.setName(name);
        return client;
    }

    public double getBalance(String client_id){
        Client client = clientList.getClient(client_id);
        return client.getBalance();
    }

    public double acceptPayment(String client_id, double amount ){
        Client client = clientList.getClient(client_id);
        return client.setBalance(client.getBalance() - amount);
    }

    public Iterator getClientWaitlist(String client_id) {
        Client c = clientList.getClient(client_id);
        return c.getWaitlist();
    }

    public Iterator getProductWaitlist(String product_id) {
        Product p = productList.getProduct(product_id);
        return p.getWaitlist();
    }

    public Iterator getClientTransactions(String client_id) {
        Client c = clientList.getClient(client_id);
        return c.getTransactions();
    }

    // Place client order
    public Invoice placeClientOrder(String clientID) {
        int stockQuantity = 0;
        int cartQuantity = 0;
        int invoiceQuantity = 0;
        int waitlistQuantity = 0;

        // get Client
        Client client = clientList.getClient(clientID);

        // get cart contents
        Iterator cartProducts = client.getCartContents();

        // create invoice
        Invoice invoice = new Invoice();

        // Check the quantity in stock for each product in the cart, then add to invoice
        // and waitlist
        while (cartProducts.hasNext()) {
            Product cartProduct = (Product) (cartProducts.next());

            // check the amount in stock
            Product stockProduct = productList.getProduct(cartProduct.getProductID());
            stockQuantity = stockProduct.getQuantity();
            cartQuantity = cartProduct.getQuantity();

            if (stockQuantity >= cartQuantity) {
                invoiceQuantity = cartQuantity;

                // decrease quantity in stock by amount ordered
                stockProduct.setQuantity(stockProduct.getQuantity() - cartQuantity);

            }
            // Not enough in stock, add what's available to invoice and waitlist the
            // remaining
            else {
                invoiceQuantity = stockQuantity;
                waitlistQuantity = cartQuantity - stockQuantity;

                // decrease quantity in stock
                stockProduct.setQuantity(0);
            }

            // Add shipped product with it's associated quantity to invoice
            if (invoiceQuantity > 0) {

                // Create a copy of the cart product and set quantity to be the invoice quantity
                Product invoiceProduct = new Product(cartProduct);
                invoiceProduct.setQuantity(invoiceQuantity);
                invoice.addProduct(invoiceProduct);

            }

            // add waitlisted product with it's associated quantity to waitlist
            if (waitlistQuantity > 0) {
                WaitlistProduct waitlistproduct = new WaitlistProduct(client, stockProduct, waitlistQuantity);
                client.addToWaitlist(waitlistproduct);
                stockProduct.addToWaitlist(waitlistproduct);

            }
        }

        // decrease balance by shipped products total cost
        client.setBalance(client.getBalance() + invoice.getTotalCost());
        //client.addToBalance(0-invoice.getTotalCost());

        // Creates and stores order with cart contents then clears cart
        client.createOrder(invoice.getTotalCost());

        return invoice;

    }

    public Invoice fullfillWaitlistedOrder(WaitlistProduct waitlist){
        //Gets waitlisted clients, products, and quantity
        //Creates a new invoice for the waitlisred order
        Client client = waitlist.getClient();
        Product product = waitlist.getProduct();
        int invoiceQuantity = waitlist.getQuantity();
        Invoice invoice = new Invoice();
        
        //Creates a copy of the product reference to be added to the invoice and sets the quantity to the waitlisted order quantity
        Product invoiceProduct = new Product(product);
        invoiceProduct.setQuantity(invoiceQuantity);
        
        //Creates a new invoice with the waitlisted product
        invoice.addProduct(invoiceProduct);
        
        //Add the waitlisted product/client to the removeWaitlist
        removeWaitlist.add(waitlist);
        
        //Updates the clients balance
        client.addToBalance(0-invoice.getTotalCost());
        return invoice;
    }
    
    public void removeWaitlistedOrders(){
        Client client;
        Product product;
        for(WaitlistProduct wait : removeWaitlist){
            client = wait.getClient();
            product = wait.getProduct();
            client.removeWaitlist(wait);
            product.removeWaitlist(wait);
        }
        removeWaitlist.clear();
    }
    
}