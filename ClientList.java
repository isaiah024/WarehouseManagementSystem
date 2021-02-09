package warehouseproject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ClientList implements Serializable{
    private static final long serialVersionUID = 1L;
    private ArrayList<Client> clients = new ArrayList<Client>();
    private static ClientList clientList;
    
    public ClientList(){
    }
    
    //Singleton class initializes
    public static ClientList instance() {
        if (clientList == null) {
            return (clientList = new ClientList());
        }else{
            return clientList;
        }
  }
    
    public boolean insertClient(Client client){
        clients.add(client);
        return true;
    }
    
    public Iterator getClients(){
        return clients.iterator();
    }
    
    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(clientList);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    private void readObject(java.io.ObjectInputStream input) {
        try {
            if(clientList != null) {
                return;
            }else{
                input.defaultReadObject();
                if(clientList == null) {
                    clientList = (ClientList) input.readObject();
                }else{
                    input.readObject();
                }
            }
        }catch(IOException ioe) {
        ioe.printStackTrace();
        }catch(ClassNotFoundException cnfe) {
        cnfe.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "ClientList:" + " clients = " + Arrays.toString(clients.toArray());
    }
    
    
}
