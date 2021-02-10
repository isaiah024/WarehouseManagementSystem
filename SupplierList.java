package warehouseproject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

class SupplierList {
    private static final long serialVersionUID = 1L;
    private ArrayList<Supplier> suppliers = new ArrayList<Supplier>();
    private static SupplierList supplierList;
    
    public SupplierList(){
    }
    
    //Singleton class initializes
    public static SupplierList instance() {
        if (supplierList == null) {
            return (supplierList = new SupplierList());
        }else{
            return supplierList;
        }
    }
    
    public boolean insertSupplier(Supplier supplier){
        suppliers.add(supplier);
        return true;
    }
    
    public Iterator getSuppliers(){
        return suppliers.iterator();
    }
    
    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(supplierList);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    private void readObject(java.io.ObjectInputStream input) {
        try {
            if(supplierList != null) {
                return;
            }else{
                input.defaultReadObject();
                if(supplierList == null) {
                    supplierList = (SupplierList) input.readObject();
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
    
}

