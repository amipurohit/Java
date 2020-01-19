/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelbookingtest;

/**
 *
 * @author kp
 */
public abstract class Room {
    public static final int ROOM_TYPE_BASIC_ROOM   = 100;
    public static final int ROOM_TYPE_JUNIOR_SUITE = 200;
    public static final int ROOM_TYPE_FULL_SUITE   = 300;
    
    private int roomNumber;
    private int numberOfPerson;
    private String customerID;
    private boolean isReserved = false;
    
    private boolean roomCleanning    = false;
    private boolean breakFastInclude = false;
    private boolean massageInclude   = false;
    
    Room( ) {}
    
    public int getRoomNumber()
    {
        return roomNumber;
    }        
    public int getNumberOfPerson(){
        return numberOfPerson;
    }
    public String getCustomerID(){
        return customerID;
    }
    public boolean getIsReserved(){
        return isReserved;
    }
       
     public void setRoomNumber(int newRoomNumber)
    {
       roomNumber = newRoomNumber;
    }        
    public void setNumberOfPerson(int newNumberOfPerson){
        numberOfPerson = newNumberOfPerson;
    }
    public void setCustomerID(String newCustomerID){
        customerID = newCustomerID;
    }
    public void  setIsReserved(boolean newReserved){
         isReserved = newReserved;
    }
   
    public boolean getRoomCleanning(){
        return roomCleanning;
    } 
    public boolean getBreakFastInclude(){
        return breakFastInclude;
    } 
    public boolean getMassageInclude(){
        return massageInclude;
    } 
    
    public void setRoomCleanning(boolean value){
       roomCleanning = value;
    } 
    public void setBreakFastInclude(boolean value){
        breakFastInclude = value;
    } 
    public void setMassageInclude(boolean value){
         massageInclude = value;
    } 

}
