/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelbookingtest;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author kp
 */
public class Booking {
    private static final int BOOKING_ID_INDEX = 1000;
    private java.util.Date date = new java.util.Date();
    private static int idOfBooking = BOOKING_ID_INDEX;
    private String dateOfBooking;
//    private LocalTime timeOfReservation;
    private int totalDaysOfBooking;
    private int numberOfPersons;
    private int roomType;
    private int roomNumber;
    private String customerID;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private Customer customer;
    
    private BasicRoom   basicRoom ;
    private JuniorSuite juniorSuite;
    private FullSuite   fullSuite;
    
    private static int count = 0;
    private int amountReceived;
    private int bill;
    
    public Booking() {
        count++;
    }

    public Booking(String dateOfBooking,
                   int totalDaysOfBooking,
                   int numberOfPersons,
                   int roomType,
                   String customerName,
                   String customerPhone,
                   String customerEmail) {
        this.idOfBooking++;
        this.dateOfBooking      = dateOfBooking;
        this.totalDaysOfBooking = totalDaysOfBooking;
        this.numberOfPersons    = numberOfPersons;
        this.roomType           = roomType;
        
        this.customerName       = customerName;
        this.customerPhone      = customerPhone;
        this.customerEmail      = customerEmail;
        
       customer = new Customer(customerName, customerEmail, customerPhone);
       switch (roomType) {
            case  Room.ROOM_TYPE_BASIC_ROOM:
                 basicRoom = new BasicRoom(numberOfPersons, customer.getCustomerID());
                 roomNumber=basicRoom.getRoomNumber();
                 break;
           case Room.ROOM_TYPE_JUNIOR_SUITE:
                            
                juniorSuite = new JuniorSuite(numberOfPersons, customer.getCustomerID());
                roomNumber=juniorSuite.getRoomNumber();
                break;                
           case  Room.ROOM_TYPE_FULL_SUITE:
                 fullSuite = new FullSuite(numberOfPersons, customer.getCustomerID());
                 roomNumber=fullSuite.getRoomNumber();
                 break;
       }
      
        count++;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdOfBooking() {
        return idOfBooking;
    }

    public void setIdOfBooking(int idOfBooking) {
        this.idOfBooking = idOfBooking;
    }

    public String getDateOfBooking() {
        return dateOfBooking;
    }

    public void setDateOfBooking(String dateOfBooking) {
        this.dateOfBooking = dateOfBooking;
    }

    public int getTotalDaysOfBooking() {
        return totalDaysOfBooking;
    }

    public void setTotalDaysOfBooking(int totalDaysOfBooking) {
        this.totalDaysOfBooking = totalDaysOfBooking;
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
    public int getRoomType() {
        return roomType;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }
    
    public Customer getCustomer() {
        return customer;
    }

    public BasicRoom getBasicRoom(){
        return basicRoom;
    }
    public JuniorSuite getJuniorSuite(){
        return juniorSuite;
    }
    public FullSuite getFullSuite(){
        return fullSuite;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getAmountReceived() {
        return amountReceived;
    }

   
    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }
    
        
    @Override
    public String toString(){

    String bookingInfo = String.format("%20s%10d%10d%10d%30s",
                                       getDateOfBooking(),
                                       getIdOfBooking(),
                                       getTotalDaysOfBooking(),
                                       getNumberOfPersons(),
                                        convertRoomType());
    bookingInfo =bookingInfo + getCustomer().toString();
    bookingInfo =bookingInfo + "\n";
    return bookingInfo;
    

    }
    
    //this will change integer selection of room type to room names for file input.
    public String convertRoomType(){
           String str = null;
            if (this.roomType == Room.ROOM_TYPE_BASIC_ROOM){
               str = "Basic Room";
            }
            if (this.getRoomType() == Room.ROOM_TYPE_JUNIOR_SUITE) {
                str = "Junior Suite";
            }
            if (this.getRoomType() == Room.ROOM_TYPE_FULL_SUITE) {
                str = "Full Suite";
            }
            return str;
    }
    
}
    

