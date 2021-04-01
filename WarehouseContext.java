
package warehouseproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class WarehouseContext {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Warehouse warehouse;
    private static WarehouseContext context;
    private String client_id;
    private int currentUser;
    private int currentState;

    public static final int IsClient = 0;
    public static final int IsClerk = 1;
    public static final int IsManager = 2;
    private WarehouseState[] states;
    private int[][] nextState;
    
    private WarehouseContext() { //constructor
        System.out.println("In WarehouseContext constructor");
        if (yesOrNo("Look for saved data and  use it?")) {
            retrieve();
        } else {
            warehouse = Warehouse.instance();
        }
        // set up the FSM and transition table;
        //Will be size 4
        states = new WarehouseState[4];
        states[0] = ClerkMenuState.instance();
        states[1] = ClientState.instance();
        states[2] = Loginstate.instance();
        states[3] = ManagerState.instance();
        nextState = new int[4][4];
        nextState[0][0] = -2;nextState[0][1] = 1;nextState[0][2] = 2;nextState[0][3] = 3;
        nextState[1][0] = 0;nextState[1][1] = -2;nextState[1][2] = 2;nextState[1][3] = -2;
        nextState[2][0] = 0;nextState[2][1] = 1;nextState[2][2] = -1;nextState[2][3] = 3;
        nextState[3][0] = 0;nextState[3][1] = -2;nextState[3][2] = 2;nextState[3][3] = -2;

        currentState = 2;
    }

    public void changeState(int transition){
            //System.out.println("current state " + currentState + " \n \n ");
            currentState = nextState[currentState][transition];
            if (currentState == -2) 
                {System.out.println("Error has occurred"); terminate();}
            if (currentState == -1) 
                terminate();
            //System.out.println("current state " + currentState + " \n \n ");
            states[currentState].run();
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
    
    private void retrieve() {
        try {
            Warehouse tempWarehouse = Warehouse.retrieve();
            if(tempWarehouse != null){
                System.out.println(" The library has been successfully retrieved from the file LibraryData \n" );
                warehouse = tempWarehouse;
            }else{
                System.out.println("File doesnt exist; creating new library" );
                warehouse = Warehouse.instance();
            }
        }catch(Exception cnfe){
            cnfe.printStackTrace();
        }
    }

    public void setLogin(int code)
        {currentUser = code;}

    public void setClient(String cID)
        { client_id = cID;}

    public int getLogin()
        { return currentUser;}

    public String getClient()
        { return client_id;}
    
    

    private void terminate(){
     if (yesOrNo("Save data?")) {
        if (warehouse.save()) {
           System.out.println(" The library has been successfully saved in the file LibraryData \n" );
         } else {
           System.out.println(" There has been an error in saving \n" );
         }
       }
     System.out.println(" Goodbye \n "); System.exit(0);
    }

    public static WarehouseContext instance() {
      if (context == null) {
         System.out.println("calling constructor");
        context = new WarehouseContext();
      }
      return context;
    }

    public void process(){
      states[currentState].run();
    }
  
    public static void main (String[] args){
      WarehouseContext.instance().process(); 
    }
    
}
