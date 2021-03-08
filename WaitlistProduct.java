/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package warehouseProject;

import java.util.*;
import java.io.*;
import java.lang.*;

public class WaitlistProduct implements Serializable {
  private static final long serialVersionUID = 1L;
  private Product product;
  private Client client;
  private int quantity;

  public WaitlistProduct(Client c, Product p, int q) {
    this.client = c;
    this.product = p;
    this.quantity = q;
  }

  public Product getProduct() {
    return product;
  }

  public Client getClient() {
    return client;
  }

  public int getQuantity() {
    return quantity;
  }

  public void updateQuantity(int newQ) {
    this.quantity = newQ;
  }

  public String toString() {
    return "Client: " + getClient().getId() + ", Product: " + getProduct().getName() + ", Quantity: " + getQuantity();
  }
}
