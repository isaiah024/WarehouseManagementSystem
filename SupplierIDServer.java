package warehouseproject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class SupplierIDServer implements Serializable {
  private int idCounter;
  private static SupplierIDServer server;

  private SupplierIDServer() {
    idCounter = 1;
  }

  // Singleton class initializes
  public static SupplierIDServer instance() {
    if (server == null) {
      return (server = new SupplierIDServer());
    } else {
      return server;
    }
  }

  public int getId() {
    return idCounter++;
  }

  public String toString() {
    return ("IdServer" + idCounter);
  }

  public static void retrieve(ObjectInputStream input) {
    try {
      server = (SupplierIDServer) input.readObject();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } catch (Exception cnfe) {
      cnfe.printStackTrace();
    }
  }

  private void writeObject(java.io.ObjectOutputStream output) throws IOException {
    try {
      output.defaultWriteObject();
      output.writeObject(server);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  private void readObject(java.io.ObjectInputStream input) throws IOException, ClassNotFoundException {
    try {
      input.defaultReadObject();
      if (server == null) {
        server = (SupplierIDServer) input.readObject();
      } else {
        input.readObject();
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
