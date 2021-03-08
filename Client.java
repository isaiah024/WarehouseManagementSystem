package warehouseproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String clientID;
    private static final String MEMBER_STRING = "C";
    private Cart cart = new Cart();
    private ArrayList<Cart> orders = new ArrayList<Cart>();
    private ArrayList<WaitlistProduct> waitlist = new ArrayList<WaitlistProduct>();
    private ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private double balance = 0;

    public Client(String name) {
        this.name = name;
        this.clientID = MEMBER_STRING + (ClientIDServer.instance()).getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return clientID;
    }

    public Cart getCart() {
        return cart;
    }

    public void createOrder(double totalCost) {
        orders.add(cart);
        Transaction t = new Transaction("Transaction for " + this.name, totalCost);
        transactions.add(t);
        cart = new Cart();
    }

    public Iterator getTransactions() {
        return transactions.iterator();
    }

    public double getBalance() {
        return balance;
    }

    public double setBalance(double bal) {
        balance = bal;
        return balance;
    }

    public Iterator getWaitlist() {
        return waitlist.iterator();
    }

    public void addToWaitlist(WaitlistProduct product) {
        waitlist.add(product);
    }

    public boolean addToCart(Product product, int qty) {
        return cart.addToCart(product, qty);
    }

    public boolean removeFromCart(String product_id, int qty) {
        return cart.removeFromCart(product_id, qty);
    }

    public Iterator getCartContents() {
        return cart.getCartContents();
    }

    @Override
    public String toString() {
        return "Client: " + "name = " + this.getName() + ", id = " + this.getId() + ", balance = " + this.getBalance();
    }

}