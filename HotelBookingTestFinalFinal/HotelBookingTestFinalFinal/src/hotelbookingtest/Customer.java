/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelbookingtest;

import java.io.FileNotFoundException;

/**
 *
 * @author kp
 */
public class Customer {
    private String customerId;
    private String name;
    private String email;
    private String phone;
   
    public Customer(String newName, String newEmail, String newPhone) {
        
        this.customerId = newPhone + newName;
        this.name       = newName;
        this.email      = newEmail;
        this.phone      = newPhone;
    }
    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
    public Customer getCustomer() {
        return this;
    }
    
    public String getCustomerID() {
        return customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
        
    @Override
    public String toString() {
        
    String customerInfo = String.format( "%35s%15s%25s%15s", 
                                        getCustomerID(), 
                                        getName(), 
                                        getEmail(),
                                        getPhone());
      
     return customerInfo;
    }
    
  
}