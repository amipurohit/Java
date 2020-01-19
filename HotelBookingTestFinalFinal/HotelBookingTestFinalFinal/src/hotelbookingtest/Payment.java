/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelbookingtest;

/**
 *
 * @author dhari_q7ewyb3
 */
public interface Payment {
   
    public void   setAmountReceived(double amountReceived);
    public double doBilling(int totalDaysOfBooking, int roomType , Booking booking, boolean isReturningCustomer);
    public double finalBill(String idOfCustomer, int totalDaysOfBooking, int roomType, Booking booking);
    public void   showFinishedPayment();
}
