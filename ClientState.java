/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Chandler
 */

package warehouseproject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.StringTokenizer;


public class ClientState extends WarehouseState{
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));  
    private static ClientState clientState;
    private static Warehouse warehouse;
    private static final int EXIT = 0;
    private static final int SHOWDETAILS = 1;
    private static final int SHOWPRODUCTS = 2;
    private static final int SHOWTRANSACTIONS = 3;
    private static final int CARTMENU = 4;
    private static final int SHOWWAITLIST = 5;
    private static final int LOGOUT = 6;

    private static final int HELP = 222;
    
    public ClientState(){
        warehouse = Warehouse.instance();
    }
    
    public static ClientState instance() {
        if (clientState == null) {
            return clientState = new ClientState();
        } else {
            return clientState;
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

    public void showDetails(){
        String client_id = WarehouseContext.instance().getClient();
        System.out.println(Warehouse.instance().searchMembership(client_id).getDetails());
    }
    
    public void showProducts(){
        Iterator allProducts = warehouse.getProducts();
        while (allProducts.hasNext()) {
            Product product = (Product) (allProducts.next());
            System.out.println(product);
        }
    }

    public void showTransactions() {
        String client_id = WarehouseContext.instance().getClient();
        Iterator it = warehouse.getClientTransactions(client_id);
        System.out.println("\nThe transactions are: ");
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public void cartMenu() {
        System.out.println("TODO: Implement cart state in stage 2");
    }

    public void showClientWaitlist() {
        String client_id = WarehouseContext.instance().getClient();
        Iterator it = warehouse.getClientWaitlist(client_id);
        System.out.println("The client's waitlist contains: \n");
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
    
    
    public void logout(){
        if ((WarehouseContext.instance()).getLogin() == WarehouseContext.IsClerk)
           { 
             (WarehouseContext.instance()).changeState(0);
            }
        else if (WarehouseContext.instance().getLogin() == WarehouseContext.IsManager)
           {  
            (WarehouseContext.instance()).changeState(0);
           }
        else 
           (WarehouseContext.instance()).changeState(2); 
    }
    
    
    public void help(){
        System.out.println("\nCLIENT MENU");
        System.out.println(SHOWDETAILS + " to show client details.");
        System.out.println(SHOWPRODUCTS + " to show all products and prices.");
        System.out.println(SHOWTRANSACTIONS + " to show all client transactions.");
        System.out.println(CARTMENU + " to enter cart menu.");
        System.out.println(SHOWWAITLIST + " to show client waitlist.");
        System.out.println(LOGOUT + " to logout.");
        
    }
    
    public void process(){
        int command;
        help();
        while((command = getCommand())!= EXIT){
            switch(command){
                case SHOWDETAILS:
                    showDetails();
                    break;
                case SHOWPRODUCTS:
                    showProducts();
                    break;
                case SHOWTRANSACTIONS:
                    showTransactions();
                    break;
                case CARTMENU:
                    cartMenu();
                    break;
                case SHOWWAITLIST:
                    showClientWaitlist();
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





