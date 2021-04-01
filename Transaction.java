/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package warehouseproject;

import java.io.Serializable;
import java.util.Date;

class Transaction implements Serializable{
    private static final long serialVersionUID = 1L;
    private String description;
    private double totalCost;
    private Date date;

    public Transaction() {

    }

    public Transaction(String description, double totalCost) {

      this.date = new Date();
      this.description = description;
      this.totalCost = totalCost;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String Description) {
      this.description = description;
    }

    public double getTotalCost() {
      return totalCost;
    }

    public void setTotalCost(double TotalCost) {
      this.totalCost = TotalCost;
    }

    public Date getDate() {
      return date;
    }

    public String toString() {
      return "Transaction: " + this.description + ", Date: " + this.date + ", Total cost: " + this.totalCost;
    }
}
