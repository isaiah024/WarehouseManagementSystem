package warehouseproject;

import java.util.*;
import java.text.*;
import java.io.*;
public class ManagerState extends WarehouseState {

    
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Warehouse warehouse;
    private WarehouseContext context;
    private static ManagerState instance;
    private static final int EXIT = 0;
    private static final int ADD_PRODUCT = 1;
    private static final int ADD_SUPPLIER = 2;
    private static final int LIST_SUPPLIERS = 3;
    private static final int SHOW_PRODUCT_SUPPLIERS = 4;
    private static final int SHOW_SUPPLIER_PRODUCTS = 5;
    private static final int CHANGE_PRODUCT_PRICE = 6;
    private static final int CHANGE_PRODUCT_QTY = 7;
    private static final int REMOVE_FROM_CART = 8;
    private static final int CLERK_MENU = 9;
    private static final int LOGOUT = 10;
    private static final int HELP = 12;
    
    private ManagerState() {
        super();
        warehouse = Warehouse.instance();
    }

    public static ManagerState instance() {
        if (instance == null) {
            instance = new ManagerState();
        }
        return instance;
    }
    
    public String getToken(String prompt){
        do{
            try{
                System.out.println(prompt);
                String line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
                if (tokenizer.hasMoreTokens()){
                  return tokenizer.nextToken();
                }
            } catch (IOException ioe){
                System.exit(0);
            }
        } while (true);
    }
  
     private int getNumber(String prompt) {
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
	
	public int getCommand()
  {
    do {
      try{
        int value = Integer.parseInt(getToken("Enter command:    (" + HELP + "for help)"));
        if (value >= EXIT && value <= HELP)
        {
            return value;
        }
      }catch (NumberFormatException nfe){
        System.out.println("Enter a number");
      }
    }while (true);
  }
  
    private void addProduct() {
        String supplierID = getToken("Enter the suppliers id: ");
        String supplyPrice = getToken("Enter the supply price (XX.XX): ");
        String name = getToken("Enter product name: ");
        String productID = getToken("Enter productID: ");
        String quantity = getToken("Enter quantity: ");
        String price = getToken("Enter the sale price (XX.XX): ");
        Product result;
        result = warehouse.addProduct(supplierID, supplyPrice, name, productID, quantity, price);
        if (result == null) {
            System.out.println(
                    "Could not add product because of either: Supplier doesn't exist or Product with the Supplier already exists.");
        }
        System.out.println(result);
    }
    
     public void addSupplier() {
        String name = getToken("Enter the suppliers name: ");
        Supplier result;
        result = warehouse.addSupplier(name);
        if (result == null) {
            System.out.println("Could not add supplier");
        }
        System.out.println(result);
    }
    
    private void listSuppliers() {
         Iterator<Supplier>  allSuppliers = warehouse.getSuppliers();
    if(allSuppliers.hasNext() == false){
      System.out.println("No Supplier in the System.\n");
      return;
    }else{
      System.out.println("-------------------------------------------------");
      while ((allSuppliers.hasNext()) != false)
      {
        Supplier supplier = allSuppliers.next();
        System.out.println(supplier.toString());
      }
      System.out.println("-------------------------------------------------\n");
    }
   }
    
   // Displays all the supplier's products
    public void displaySupplierProducts() {
        String supplierID = getToken("Enter the suppliers ID: ");
        Iterator supplierProducts = warehouse.getSupplierProducts(supplierID);
        while (supplierProducts.hasNext()) {
            ProductSupplierPair pair = (ProductSupplierPair) supplierProducts.next();
            System.out.println(pair.getProductID());
        }
    }

  
    // Displays all the product's suppliers
    public void displayProductSuppliers() {
        String productID = getToken("Enter the products ID: ");
        Iterator productSuppliers = warehouse.getProductSuppliers(productID);
        while (productSuppliers.hasNext()) {
            ProductSupplierPair pair = (ProductSupplierPair) productSuppliers.next();
            System.out.println(pair.getSupplierID());
        }
    }
    
     // Changes a products price
    public void changeProductPrice() {
        String productID = getToken("Enter the product's id that you want to modify: ");
        String price = getToken("Enter the new price (XX.XX): ");
        double newPrice = Double.parseDouble(price);
        Product result = warehouse.changeSalePrice(productID, newPrice);
        if (result.getPrice() != newPrice)
            System.out.println("Products price could not be changed.");
        else
            System.out.println(result);
    }

    // Changes the clients name
    public void changeClientName() {
        String clientID = getToken("Enter the clients id that you want to modify: ");
        String name = getToken("Enter the new name: ");
        Client result = warehouse.changeName(clientID, name);
        if (result.getName() != name)
            System.out.println("Clients name could not be changed.");
        else
            System.out.println(result);
    }

    // Show client balance
    public void showBalance() {
        String clientID = getToken("Enter the client's id to view balance: ");
        double balance = warehouse.getBalance(clientID);
        System.out.println("The client's balance is: " + balance);
        
    }

    // Accept payment
    public void acceptPayment() {
        String clientID = getToken("Enter the client's ID to accept payment: ");
        String amountString = getToken("Enter the amount of money to be paid: ");
        double amount = Double.parseDouble(amountString);
        double newBalance = warehouse.acceptPayment(clientID, amount);
        System.out.println("Payment accepted. The client's updated balance is: " + newBalance);
    }

    // Show client transactions
    public void showTransactions() {
        String clientID = getToken("Enter the clients id to view transactions ");
        Iterator it = warehouse.getClientTransactions(clientID);
        System.out.println("\nThe transactions are: ");
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
    
    // Changes a products qty
    public void changeProductQty() {
        String productID = getToken("Enter the products id that you want to modify: ");
        String qty = getToken("Enter the amount that you would like to change the quantity to: ");
        int newQty = Integer.parseInt(qty);
        Product result = warehouse.changeQty(productID, newQty);
        if (result.getQuantity() != newQty)
            System.out.println("Products quantity could not be changed.");
        else
            System.out.println(result);
    }

    // Add product to client cart
    public void addToClientCart() {
        String client_id = getToken("Enter client id");
        String product_id = getToken("Enter product id");
        int product_quantity = getNumber("Enter product quantity");
        warehouse.addToClientCart(client_id, product_id, product_quantity);
    }

    // Remove from client cart
    public void removeFromClientCart() {
        String client_id = getToken("Enter client id");
        String product_id = getToken("Enter product id");
        int product_quantity = getNumber("Enter product quantity to remove");
        boolean result = warehouse.removeFromClientCart(client_id, product_id, product_quantity);

        if(result){
            System.out.println("Successfully removed product from cart. ");
        }
        else{
            System.out.println("Error: Unable to remove from cart. Check that client id and product id are correct. ");
        }
    }

    private void ClerkMenu()
    {     
      (WarehouseContext.instance()).changeState(0); //go to Clerk state
    }
    
    private void save() {
        if (warehouse.save()) {
            System.out.println(" The warehouse has been successfully saved in the file WarehouseData \n");
        } else {
            System.out.println(" There has been an error in saving \n");
        }
    }

    private void retrieve() {
        try {
            Warehouse tempLibrary = Warehouse.retrieve();
            if (tempLibrary != null) {
                System.out.println(" The warehouse has been successfully retrieved from the file WarehouseData \n");
                warehouse = tempLibrary;
            } else {
                System.out.println("File doesnt exist; creating new warehouse");
                warehouse = Warehouse.instance();

            }
        } catch (Exception cnfe) {
            cnfe.printStackTrace();
        }
    }

    private void help() {
        System.out.println("\nMANAGER MENU");
        System.out.println(ADD_PRODUCT + " to add products");
        System.out.println(ADD_SUPPLIER + " to add suppliers");
        System.out.println(SHOW_PRODUCT_SUPPLIERS + " to show all of a product's suppliers");
        System.out.println(SHOW_SUPPLIER_PRODUCTS + " to show all of the supplier's products");
        System.out.println(CHANGE_PRODUCT_PRICE + " to change the products price");
        System.out.println(CHANGE_PRODUCT_QTY + " to change the products quantity");
        System.out.println(CLERK_MENU + " to  switch to the Clerk      Person menu");
        System.out.println(REMOVE_FROM_CART + " to remove a product from cart");
        System.out.println(LOGOUT + " to logout");
        
        //System.out.println(HELP + " for help.");
    }
    
    public void logout(){
        (WarehouseContext.instance()).changeState(2);
    }
    
    public void process() {
        int command;
        help();
        while ((command = getCommand()) != EXIT) {
            switch (command) {
            	case ADD_PRODUCT: 
                    addProduct();
                    break;
                case ADD_SUPPLIER: 
                    addSupplier();
                    break;
                case LIST_SUPPLIERS: 
                    listSuppliers();
                    break;
                case SHOW_PRODUCT_SUPPLIERS:
                    displayProductSuppliers();
                    break;
                case SHOW_SUPPLIER_PRODUCTS:
                    displaySupplierProducts();
                    break;
                case REMOVE_FROM_CART:
                    removeFromClientCart();
                    break;
                case CHANGE_PRODUCT_PRICE:
                    changeProductPrice();
                    break;
                case CHANGE_PRODUCT_QTY:
                    changeProductQty();
                    break;        
                case CLERK_MENU: 
                    ClerkMenu();
                    break;
                case LOGOUT: 
                    logout();
                    break;
                case HELP: 
                    help();
                    break;
            }
            help();
        }
        logout();
    }
    
    public void run() {
        process();
    }
}