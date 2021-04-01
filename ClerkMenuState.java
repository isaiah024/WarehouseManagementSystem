
package warehouseproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.StringTokenizer;

public class ClerkMenuState extends WarehouseState{
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));  
    private static ClerkMenuState clerkState;
    private static Warehouse warehouse;
    private static final int EXIT = 0;
    private static final int ADDCLIENT = 1;
    private static final int SHOWPRODUCTS = 2;
    private static final int SHOWCLIENTS = 3;
    private static final int SHOWOUTCLIENTS = 4;
    private static final int BECOMECLIENT = 5;
    private static final int DISPLAYWAITLIST = 6;
    private static final int SHIPMENT = 7;
    private static final int LOGOUT = 8;
    private static final int HELP = 222;
    
    public ClerkMenuState(){
        warehouse = Warehouse.instance();
    }
    
    public static ClerkMenuState instance() {
        if (clerkState == null) {
            return clerkState = new ClerkMenuState();
        } else {
            return clerkState;
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

    // Checks if the users answer is yes or no
    private boolean yesOrNo(String prompt) {
        String more = getToken(prompt + " (Y|y)[es] or anything else for no: ");
        if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
            return false;
        }
        return true;
    }
    
    // Retrieves the previous save state of the warehouse
    // Always says the warehouse doesn't exist but it still retrieves the saved data
    private void retrieve() {
        try {
            Warehouse tempWarehouse = Warehouse.retrieve();
            if (tempWarehouse == null) {
                System.out.println("File doesnt exist; creating new warehouse");
                warehouse = Warehouse.instance();
            } else {
                System.out.println("The warehouse has been successfully retrieved from the file WarehouseData \n");
                warehouse = tempWarehouse;
            }
        } catch (Exception cnfe) {
            cnfe.printStackTrace();
        }
    }
    
    public int getCommand() {
        do {
            try {
                int value = Integer.parseInt(getToken("\nEnter command: " ));
                if (value >= EXIT && value <= HELP) {
                    return value;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Enter a number");
            }
        } while (true);
  }

    public void addClient(){
        String name = getToken("Enter client name: ");
        Client result;
        result = warehouse.addClient(name);
        if (result == null) {
            System.out.println("Could not add client");
        }
        System.out.println(result);
    }
    
    public void showProducts(){
        Iterator allProducts = warehouse.getProducts();
        while (allProducts.hasNext()) {
            Product product = (Product) (allProducts.next());
            System.out.println(product);
        }
    }
    
    public void showClients(){
        Iterator allClients = warehouse.getClients();
        while (allClients.hasNext()) {
            Client client = (Client) (allClients.next());
            System.out.println(client);
        }
    }
    
    public void showOutClients(){
        Iterator allClients = warehouse.getClients();
        while (allClients.hasNext()) {
            Client client = (Client) (allClients.next());
            if(client.getBalance() != 0)
                System.out.println(client);
        }
    }
    
    public void becomeClient(){

    String client_id = getToken("Please input the client id: ");
    if (Warehouse.instance().searchMembership(client_id) != null){
      (WarehouseContext.instance()).setClient(client_id);      
      (WarehouseContext.instance()).changeState(1);
    }
    else 
      System.out.println("Invalid client id.");
  
    }
    
    public void displayWaitlist(){
        String product_id = getToken("\nEnter product id");
        Iterator it = warehouse.getProductWaitlist(product_id);
        System.out.println("The product's waitlist contains: \n");
        while(it.hasNext()) {
            System.out.println(it.next());
        }
    }
    
    public void receiveShipment(){
        String productID = getToken("Enter productID: ");
        int quantity = Integer.parseInt(getToken("Enter quantity: "));
        Product result = warehouse.getProduct(productID);
        
        //Checks if product exists
        if(result == null) {
            System.out.println("Product does not exist.");
            //End function here
        }
        
        //Gets a products waitlist list
        Iterator waitlist = result.getWaitlist();
        WaitlistProduct waitlistedClient;
        
        //Gets the waitlisted clients orders and asks if the user wants to full fll the order
        while(waitlist.hasNext()){
            waitlistedClient = (WaitlistProduct) waitlist.next();
            if(yesOrNo("Would you like to full fill the order for client: " + waitlistedClient.getClient().getId())){
                if(waitlistedClient.getQuantity() <= quantity){
                    System.out.println(warehouse.fullfillWaitlistedOrder(waitlistedClient));
                    quantity -= waitlistedClient.getQuantity();
                }
                else
                    System.out.println("The order quantity is more than the available quantity");
            }
            if(quantity == 0){
                break;
            }
        }
        
        //Remove all waitlisted orders from client and product
        warehouse.removeWaitlistedOrders();
        
        //Sets any remaining quantity to the product
        if(quantity > 0)
            result.setQuantity(quantity);
    }
    
    public void logout(){
        if ((WarehouseContext.instance()).getLogin() == WarehouseContext.IsManager)
           { 
             (WarehouseContext.instance()).changeState(3);
            }
        else 
           (WarehouseContext.instance()).changeState(2); 
    }
    
    public void help(){
        System.out.println("\nCLERK MENU");
        System.out.println(+ ADDCLIENT + " to add a client to the system.");
        System.out.println(SHOWPRODUCTS + " to show all of the prducts and their prices in the system.");
        System.out.println(SHOWCLIENTS + " to show all of the clients in the system.");
        System.out.println(SHOWOUTCLIENTS + " to show all of the clients with an oustanding balance.");
        System.out.println(BECOMECLIENT + " to become a client.");
        System.out.println(DISPLAYWAITLIST + " to show all the waitlisted orders for a product.");
        System.out.println(SHIPMENT + " to accept a shipment.");
        System.out.println(LOGOUT + " to logout.");
    }
    
    public void process(){
        int command;
        help();
        while((command = getCommand())!= EXIT){
            switch(command){
                case ADDCLIENT:
                    addClient();
                    break;
                case SHOWPRODUCTS:
                    showProducts();
                    break;
                case SHOWCLIENTS:
                    showClients();
                    break;
                case SHOWOUTCLIENTS:
                    showOutClients();
                    break;
                case BECOMECLIENT:
                    becomeClient();
                    break;
                case DISPLAYWAITLIST:
                    displayWaitlist();
                    break;
                case SHIPMENT:
                    receiveShipment();
                    break;
                case LOGOUT:
                    logout();
                    break;
            }
            help();
        }
        logout();
    }

    @Override
    public void run() {
        process();
    }
}
