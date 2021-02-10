package warehouseproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class UserInterface {

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static UserInterface userInterface;
    private static Warehouse warehouse;
    private static final int EXIT = 0;
    private static final int ADD_CLIENT = 1;
    private static final int ADD_PRODUCT = 2;
    private static final int ADD_SUPPLIER = 3;
    private static final int HELP = 10;
    
    //Check for previously saved data or create new warehouse instance
    private UserInterface() {
        if (yesOrNo("Look for saved data and  use it?")) {
        retrieve();
        } else {
        warehouse = Warehouse.instance();
        }
    }
    
    //Creates an instance of the UI
    public static UserInterface instance() {
        if (userInterface == null) {
            return userInterface = new UserInterface();
        }else {
            return userInterface;
        }
    }
    
    //Will get the users input
    public String getToken(String prompt) {
        do {
            try {
                System.out.println(prompt);
                String line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
                if (tokenizer.hasMoreTokens()) {
                    return tokenizer.nextToken();
                }
            }catch(IOException ioe){
            System.exit(0);
            }
        }while(true);
    }
    
    //Checks if the users answer is yes or no
    private boolean yesOrNo(String prompt) {
        String more = getToken(prompt + " (Y|y)[es] or anything else for no: ");
        if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
            return false;
        }
        return true;
    }
    
    //Retrieves the previous save state of the warehouse
    private void retrieve() {
        try {
            Warehouse tempWarehouse = Warehouse.retrieve();
            if (tempWarehouse != null) {
                System.out.println("The warehouse has been successfully retrieved from the file WarehouseData \n" );
                warehouse = tempWarehouse;
            }else{
                System.out.println("File doesnt exist; creating new warehouse" );
                warehouse = Warehouse.instance();
            }
        }catch(Exception cnfe){
            cnfe.printStackTrace();
        }
    }
    
    //Add a client to the clientList 
    public void addClient() {
        String name = getToken("Enter client name: ");
        Client result;
        result = warehouse.addClient(name);
        if (result == null) {
            System.out.println("Could not add client");
        }
        System.out.println(result);
    }
    
    //Add a product to the productList 
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
    
    //Add a supplier to the supplierList
    public void addSupplier(){
        String name = getToken("Enter the suppliers name: ");
        Supplier result;
        result = warehouse.addSupplier(name);
        if(result == null){
            System.out.println("Could not add product");
        }
        System.out.println(result);
    }
    
    public void help() {
        System.out.println("Enter a number between 0 and 1 as explained below: ");
        System.out.println(EXIT + " to Exit\n");
        System.out.println(ADD_CLIENT + " to add a client");
        System.out.println(ADD_PRODUCT + " to add a product");
        System.out.println(ADD_SUPPLIER + " to add a supplier");
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
        }while(true);
    }
    
    public void process(){
        int command;
        while((command = getCommand()) != EXIT ){
            switch(command){
                case ADD_CLIENT:        addClient();
                                        break;
                case HELP:              help();
                                        break;
                case ADD_PRODUCT:       addProduct();
                                        break;
                case ADD_SUPPLIER:      addSupplier();
                                        break;
            }
        }
    }
    
    public static void main(String[] args) {
        UserInterface.instance().process();
    }
    
}
