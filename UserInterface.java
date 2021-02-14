package warehouseproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.StringTokenizer;

public class UserInterface {

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static UserInterface userInterface;
    private static Warehouse warehouse;
    private static final int EXIT = 0;
    private static final int ADD_CLIENT = 1;
    private static final int ADD_PRODUCT = 2;
    private static final int ADD_SUPPLIER = 3;
    private static final int SHOW_CLIENTS = 4;
    private static final int SHOW_PRODUCTS = 5;
    private static final int SHOW_SUPPLIERS = 6;
    private static final int ADD_TO_CART = 7;
    private static final int VIEW_CART = 8;
    private static final int SAVE = 9;
    private static final int HELP = 10;

    // Check for previously saved data or create new warehouse instance
    private UserInterface() {
        if (yesOrNo("Look for saved data and  use it?")) {
            retrieve();
        } else {
            warehouse = Warehouse.instance();
        }
    }

    // Creates an instance of the UI
    public static UserInterface instance() {
        if (userInterface == null) {
            return userInterface = new UserInterface();
        } else {
            return userInterface;
        }
    }

    // Will get the users input
    public String getToken(String prompt) {
        do {
            try {
                System.out.println(prompt);
                String line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
                if (tokenizer.hasMoreTokens()) {
                    return tokenizer.nextToken();
                }
            } catch (IOException ioe) {
                System.exit(0);
            }
        } while (true);
    }

    public int getNumber(String prompt) {
    do {
      try {
        String item = getToken(prompt);
        Integer num = Integer.valueOf(item);
        return num.intValue();
      } catch (NumberFormatException nfe) {
        System.out.println("Please input a number ");
      }
    } while (true);
  }

    // Checks if the users answer is yes or no
    private boolean yesOrNo(String prompt) {
        String more = getToken(prompt + " (Y|y)[es] or anything else for no: ");
        if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
            return false;
        }
        return true;
    }

    // Retrieves the previous save state of the warehouse
    private void retrieve() {
        try {
            Warehouse tempWarehouse = Warehouse.retrieve();
            if (tempWarehouse != null) {
                System.out.println("The warehouse has been successfully retrieved from the file WarehouseData \n");
                warehouse = tempWarehouse;
            } else {
                System.out.println("File doesnt exist; creating new warehouse");
                warehouse = Warehouse.instance();
            }
        } catch (Exception cnfe) {
            cnfe.printStackTrace();
        }
    }

    // Save the data
    private void save() {
        if (warehouse.save()) {
            System.out.println(" The warehouse has been successfully saved in the file WarehouseData \n");
        } else {
            System.out.println(" There has been an error in saving \n");
        }
    }

    // Add a client to the clientList
    public void addClient() {
        String name = getToken("Enter client name: ");
        Client result;
        result = warehouse.addClient(name);
        if (result == null) {
            System.out.println("Could not add client");
        }
        System.out.println(result);
    }

    // Add a product to the productList
    public void addProduct() {
        String name = getToken("Enter product name: ");
        String productID = getToken("Enter productID: ");
        String quantity = getToken("Enter quantity: ");
        String price = getToken("Enter the price (XX.XX): ");
        Product result;
        result = warehouse.addProduct(name, productID, quantity, price);
        if (result == null) {
            System.out.println("Could not add product");
        }
        System.out.println(result);
    }

    // Add a supplier to the supplierList
    public void addSupplier() {
        String name = getToken("Enter the suppliers name: ");
        Supplier result;
        result = warehouse.addSupplier(name);
        if (result == null) {
            System.out.println("Could not add product");
        }
        System.out.println(result);
    }

    // Display all clients
    public void displayClients() {
        Iterator allClients = warehouse.getClients();
        while (allClients.hasNext()) {
            Client client = (Client) (allClients.next());
            System.out.println(client);
        }
    }

    // Display all products
    public void displayProducts() {
        Iterator allProducts = warehouse.getProducts();
        while (allProducts.hasNext()) {
            Product product = (Product) (allProducts.next());
            System.out.println(product);
        }
    }

    // Display all suppliers
    public void displaySuppliers() {
        Iterator allSuppliers = warehouse.getSuppliers();
        while (allSuppliers.hasNext()) {
            Supplier supplier = (Supplier) (allSuppliers.next());
            System.out.println(supplier);
        }
    }

    //Add product to client cart
    public void addToClientCart(){
        String client_id = getToken("Enter client id");
        String product_id = getToken("Enter product id");
        int product_quantity = getNumber("Enter product quantity");
        warehouse.addToClientCart(client_id, product_id, product_quantity);
    }

    //view all products in client cart
    public void displayClientCart(){
        String client_id = getToken("Enter client id");
        Iterator it = warehouse.getCartContents(client_id);
        System.out.
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }


    public void help() {
        System.out.println("Enter a number between 0 and 1 as explained below: ");
        System.out.println(EXIT + " to Exit\n");
        System.out.println(ADD_CLIENT + " to add a client");
        System.out.println(ADD_PRODUCT + " to add a product");
        System.out.println(ADD_SUPPLIER + " to add a supplier");
        System.out.println(SHOW_CLIENTS + " to show all of the clients");
        System.out.println(SHOW_PRODUCTS + " to show all of the products");
        System.out.println(SHOW_SUPPLIERS + " to show all of the suppliers");
        System.out.println(ADD_TO_CART + " to add a product to a client's cart");
        System.out.println(VIEW_CART + " to view cart");
        System.out.println(SAVE + " to save the data");
    }

    public int getCommand() {
        do {
            try {
                int value = Integer.parseInt(getToken("Enter command " + HELP + " for help \nCommand Number:"));
                if (value >= EXIT && value <= HELP) {
                    return value;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Enter a number");
            }
        } while (true);
    }

    public void process() {
        int command;
        while ((command = getCommand()) != EXIT) {
            switch (command) {
                case ADD_CLIENT:
                    addClient();
                    break;
                case ADD_PRODUCT:
                    addProduct();
                    break;
                case ADD_SUPPLIER:
                    addSupplier();
                    break;
                case SHOW_CLIENTS:
                    displayClients();
                    break;
                case SHOW_PRODUCTS:
                    displayProducts();
                    break;
                case SHOW_SUPPLIERS:
                    displaySuppliers();
                    break;
                case ADD_TO_CART:
                    addToClientCart();
                    break;
                case VIEW_CART:
                    displayClientCart();
                    break;
                case SAVE:
                    save();
                    break;
                case HELP:
                    help();
                    break;
            }
        }
    }

//    public static void main(String[] args) {
//        UserInterface.instance().process();
//        
//    }

}


