/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelbookingtest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


/**
 *
 * @author kp
 */
public class Hotel implements Payment {
   
   public static final String TEMP_FILE_NAME   = "temp_csv_file.csv";
   public static final int CUSTOMER_DISCOUNT      = 10;
   public static final int NUMBER_OF_BASIC_ROOM   = 20;
   public static final int NUMBER_OF_JUNIOR_SUITE = 20;
   public static final int NUMBER_OF_FULL_SUITE   = 10;
   public static final int TOTAL_NUMBER_OF_ROOM   = NUMBER_OF_BASIC_ROOM +
                                                    NUMBER_OF_JUNIOR_SUITE +
                                                    NUMBER_OF_FULL_SUITE ;
   
   private static int basicRoomAvailable   = NUMBER_OF_BASIC_ROOM; 
   private static int juniorSuiteAvailable = NUMBER_OF_JUNIOR_SUITE;
   private static int fullSuiteAvailable   = NUMBER_OF_FULL_SUITE;
   String hotelName;
   String csvFilename;
   String customerFilename;
   
   private double amountReceived;
   private double bill;
          
   private ArrayList<Booking> reservedBooking = new ArrayList<Booking>(TOTAL_NUMBER_OF_ROOM);
   private ArrayList<Customer> customer       = new ArrayList<Customer>();
  
   // constuctor     
   Hotel(String newName, String newCsvFilename, String newCustomerFileName){
       hotelName        = newName;
       csvFilename      = newCsvFilename;
       customerFilename = newCustomerFileName;
   }
   
    public Booking getReservedBookingByRoomNumber(int roomNumber){
        Booking newBooking = null;
        for(int i=0; i <reservedBooking.size(); i++  ){
            if (reservedBooking.get(i).getRoomNumber() == roomNumber) {
                newBooking = reservedBooking.get(i);
                break;                
            }
        }
        return newBooking;
                
    }
   
   public ArrayList<Booking> getReservedBookingArray(){
       return reservedBooking;
   }
   public ArrayList<Customer> getCustomerArray(){
       return customer;
   }
   
   
   public Booking getReservedBookingArray(int index){
       return reservedBooking.get(index);
   }
   public Customer getCustomerArray(int index){
       return customer.get(index);
   }
      
   public void addReservedBookingArray(Booking newBooking){
       if (reservedBooking.size() < TOTAL_NUMBER_OF_ROOM){
          reservedBooking.add(newBooking);
       } 
   }
   public void addCustomerArray( Customer newCustomer){
       if (customer.contains(newCustomer)) return;
       customer.add(newCustomer);
   }
   
   public  void addRoomToArray(Room newRoom, int roomType){
       
       switch(roomType) {
           case Room.ROOM_TYPE_BASIC_ROOM:
               addBasicRoomArray( (BasicRoom)newRoom);
           case Room.ROOM_TYPE_JUNIOR_SUITE:
               addJuniorSuiteArray((JuniorSuite) newRoom);
           case Room.ROOM_TYPE_FULL_SUITE:    
               addFullSuiteArray( (FullSuite)newRoom);
       }
   }
   
    public  boolean addBasicRoomArray(BasicRoom newRoom){
        if(basicRoomAvailable > 0){
          basicRoomAvailable--;
          return true;
        }  
        return false;
    }
 
    public  boolean addJuniorSuiteArray(JuniorSuite newRoom ){
        if(juniorSuiteAvailable > 0){
           juniorSuiteAvailable--;
           return true;
        }   
        return false;
    }
    public  boolean addFullSuiteArray(FullSuite newRoom){
        if(fullSuiteAvailable > 0){
           juniorSuiteAvailable--;
           return true;
        }   
        return false;
    }
    
    public boolean uploadBookingFromFile ()  {
        //Get file from system
        File file = new File(csvFilename);
        if (!file.exists()) {
            System.out.println("System file " + csvFilename + "does not exist");
            return true;
        }
        ArrayList<String> fileRecords = new ArrayList<>();
        Scanner input = null;
        try{
            input =  new Scanner(file);
            while (input.hasNextLine()){ 
                  fileRecords.add(input.nextLine());           
            } 
        }  catch (FileNotFoundException ex) {
           System.out.println( ex.toString());
           return false;
        } finally {
          input.close();
        }  
        // upload data to file
        if (!uploadDataToSystem(fileRecords)) return false;
        // make current csvFile to old csv file
        if(!renameTheFile(csvFilename, ("old" + csvFilename))) return false;
        // make temp file to current csv file
        if (!renameTheFile(TEMP_FILE_NAME, csvFilename)) return false;
        
        return true;
    }    
    
    
    
    public boolean uploadDataToSystem(ArrayList<String> fileRecords) {  
        
        LocalDate    bookingDate =null;
        LocalDate    currentDate = LocalDate.now();
        
        DateTimeFormatter foramatter = DateTimeFormatter.ofPattern("yyy-MM-dd");
        currentDate.format(foramatter);
        
        String date;
        int    bookingId;
        int    daysOfBooking;
        int    numberOfPerson;
        String roomType;
        String customerId;
        String customerName;
        String customerEmail;
        String customerPhone;
        PrintWriter output = null;
        
        File file = new File(TEMP_FILE_NAME);
    try {
         output = new PrintWriter(file);
        
        //create today's booking objects
        for (int i=0; i<fileRecords.size();i++){
            if (fileRecords.get(i).isEmpty()) continue;
             date       = fileRecords.get(i).substring(0, 20).trim();
             bookingDate = LocalDate.parse(date, foramatter);
               
             bookingId         = Integer.parseInt(fileRecords.get(i).substring(20, 30).trim());
             daysOfBooking     = Integer.parseInt(fileRecords.get(i).substring(30, 40).trim());
             numberOfPerson    = Integer.parseInt(fileRecords.get(i).substring(40, 50).trim());
             roomType          = fileRecords.get(i).substring(50,80).trim();
             customerId        = fileRecords.get(i).substring(80,115).trim();
             customerName      = fileRecords.get(i).substring(115,130).trim();
             customerEmail     = fileRecords.get(i).substring(130,155).trim();
             customerPhone     = fileRecords.get(i).substring(155, 170).trim();
             int type = 0;
             if (roomType.compareToIgnoreCase("Basic Room") == 0){
                 type = Room.ROOM_TYPE_BASIC_ROOM; 
             } else if (roomType.compareToIgnoreCase("Junior Suite") == 0){
                 type = Room.ROOM_TYPE_JUNIOR_SUITE;
             } else if (roomType.compareToIgnoreCase("Full Suite") == 0){
                 type = Room.ROOM_TYPE_FULL_SUITE;
             }
             
              //create new booking
              Booking newBooking = new Booking(date,
                                               daysOfBooking,
                                               numberOfPerson, 
                                               type,                                                                                      
                                               customerName,
                                               customerPhone,
                                               customerEmail);
       
             // create record in Array
             if (currentDate.equals(bookingDate)) {
                 addReservedBookingArray(newBooking);
                 addCustomerArray(newBooking.getCustomer());
                 
                 // reserve a room for booking
                 int customerRoom =0;
                 switch (type) {
                       case  Room.ROOM_TYPE_BASIC_ROOM:
                            addBasicRoomArray(newBooking.getBasicRoom());
                            customerRoom = newBooking.getBasicRoom().getRoomNumber();
                            break;
                       case Room.ROOM_TYPE_JUNIOR_SUITE:
                            addJuniorSuiteArray(newBooking.getJuniorSuite()); 
                            customerRoom = newBooking.getJuniorSuite().getRoomNumber();
                            break;                
                       case  Room.ROOM_TYPE_FULL_SUITE:
                            addFullSuiteArray(newBooking.getFullSuite());
                            customerRoom = newBooking.getFullSuite().getRoomNumber();
                            break;
                        }
                        System.out.println("/-----------------------------------------------------------/");
                        System.out.println("/          Room number " + customerRoom + " has been book for " + customerName );
                        System.out.println("/-----------------------------------------------------------/");
                  }
                  // save data to file;
                 output.write(newBooking.toString());
             } 
            
    } catch(FileNotFoundException ex){
       System.out.println(ex.toString());
       return false;
    }  finally {
        if (output != null){
           output.close();
        }   
    }
    
    return true;
}
        
       
    public boolean renameTheFile(String oldFileName, String newFileName){    
        // Reaname HotelBooking.csv to oldHotelBooking.csv
        // File (or directory) with old name
        File oldFile = new File(oldFileName);

        // File (or directory) with new name
        File newFile = new File(newFileName);
        
        if (newFile.exists()) {
            newFile.delete();
        }
       
        // Rename file (or directory)
        boolean success = oldFile.renameTo(newFile);

       if (!success) {
           System.out.println("Previous file was not successfully renamed");
           return false;
        }       
       return true;  
   }    

    public boolean isRoomAvailable(int roomType){
         switch (roomType) {
            case BasicRoom.ROOM_TYPE_BASIC_ROOM:
                if(basicRoomAvailable >0) return true; 
                break;
            case JuniorSuite.ROOM_TYPE_JUNIOR_SUITE: 
                if(juniorSuiteAvailable >0) return true; 
                break;
            
            case FullSuite.ROOM_TYPE_FULL_SUITE: 
               if(fullSuiteAvailable > 0)return true;
               break;
        }
        return false;
    }
    
    public boolean isRoomReseved (int customerRoomNumber){
         Booking booking = getReservedBookingByRoomNumber(customerRoomNumber);
         if (booking == null)return false;
         
        return true;
        
    }
     //This will write all booking information to file
    public void savetoFile(Booking newBooking)throws IOException{
        
           java.io.FileWriter output = new java.io.FileWriter(csvFilename,true);
           output.write(newBooking.toString());
           output.close();

    }
  
     //This method will check if returning or new customer and call accordingly method to finalize billing
   @Override
    public double finalBill(String idOfCustomer, int totalDaysOfBooking, int roomType, Booking booking) {
        bill =0;
        try{
           //PrintWriter output = new PrintWriter(customerFilename, true);
            File file = new File (customerFilename);
            FileWriter output = new FileWriter(file, true);
                     
           Scanner s = new Scanner(new File(customerFilename));
           ArrayList<String> list = new ArrayList<>();
           while (s.hasNext()){
             list.add(s.next());
           }
           if (list.contains(idOfCustomer)){
              output.close();
               bill = doBilling(totalDaysOfBooking, roomType , booking, true);
            }
             else {
               output.write(idOfCustomer);
               output.write("\n");
               output.flush();
               output.close();
               bill = doBilling(totalDaysOfBooking, roomType, booking, false);
            }
        }catch ( IOException ex) {
            System.out.println(ex.toString());
        }
        return bill;
    }
    
    //billing for new customer.  It will be called by Final bill method.
   @Override
    public double doBilling(int totalDaysOfBooking, int roomType, Booking booking, boolean isReturningCustomer) {
       
        double roomCharge =0;
        double cleanningService=0;
        double breakfast=0;
        double massageService =0;
        double returningCustomerDiscount = 0;
        double totalPaymentAmount=0;
        
        switch (roomType) {
            case BasicRoom.ROOM_TYPE_BASIC_ROOM:
                roomCharge       = totalDaysOfBooking * BasicRoom.BASIC_ROOM_PRICE_PER_DAY;
                cleanningService = booking.getBasicRoom().calculateCleanningServiceCharges();
                breakfast        = booking.getBasicRoom().calculateBreakFastCharges();
                massageService   = booking.getBasicRoom().calculateMassageServiceCharges();
                returningCustomerDiscount= (roomCharge * 10 /100);      
                break;
            
            case JuniorSuite.ROOM_TYPE_JUNIOR_SUITE: 
                roomCharge = totalDaysOfBooking * JuniorSuite.JUNIOR_SUITE_PRICE_PER_DAY;
                cleanningService = booking.getJuniorSuite().calculateCleanningServiceCharges();
                breakfast        = booking.getJuniorSuite().calculateBreakFastCharges();
                massageService   = booking.getJuniorSuite().calculateMassageServiceCharges();
                returningCustomerDiscount= (roomCharge * 10 /100);      
                break;
            
            case FullSuite.ROOM_TYPE_FULL_SUITE: 
                roomCharge = totalDaysOfBooking * FullSuite.FULL_SUITE_PRICE_PER_DAY;
                cleanningService = totalDaysOfBooking *booking.getJuniorSuite().calculateCleanningServiceCharges();
                breakfast        = totalDaysOfBooking *booking.getJuniorSuite().calculateBreakFastCharges();
                massageService   = booking.getJuniorSuite().calculateMassageServiceCharges();
                returningCustomerDiscount= (roomCharge * CUSTOMER_DISCOUNT /100); 
                break;
        }
         totalPaymentAmount = roomCharge +cleanningService +breakfast+massageService;
         if (isReturningCustomer){
             totalPaymentAmount = totalPaymentAmount-returningCustomerDiscount;
         } else {
             returningCustomerDiscount=0;
         }
         System.out.println("/--------------------------------------------------------------------------/");
         System.out.println("/             Room payment information for "+ booking.getCustomer().getName() +"         /");  
         System.out.println("/----------------------------------------------------------------------------/");
         System.out.println("              Room Charge :                   "  +roomCharge+"$"      );
         System.out.println("              Cleanning Service Charge :     +"  +cleanningService+"$");
         System.out.println("              Breakfast Charges :            +"  +breakfast+"$"       );
         System.out.println("              MassageService :               +"  +massageService+"$"  );
         System.out.println( );
         System.out.println("              Returning Customer Discount :  -"  +returningCustomerDiscount+"$" );
         System.out.println("                                              ----------------------" );
         System.out.println("              Total Room Charges          :   "  +totalPaymentAmount+"$");
         
        return totalPaymentAmount;
        }

    //this will show finished payment and balance if any
    @Override
    public void  showFinishedPayment() {
        
        bill = bill - amountReceived;

         System.out.println("              Payment received            :  -"  + amountReceived);
         System.out.println("                                              ----------------------" );
         System.out.println("              Change Amount                   "  + bill +"$");
         System.out.println("\n----------Amount Received  Thank you! ----------\n");

    }
    
   @Override
    public void setAmountReceived(double amountReceived) {
        
        this.amountReceived = amountReceived;
        
    }
    
    public boolean removeRecordFromSystem(int roomNumber){
         
        // get booking reservation
        int count;
        for(count=0; count <reservedBooking.size(); count++  ){
           if (reservedBooking.get(count).getRoomNumber() == roomNumber) {
               break;
           }
        }
        int reservationID = reservedBooking.get(count).getIdOfBooking();
        
        //Get file from system
        File file = new File(csvFilename);
        
        if (!file.exists()) {
            System.out.println("System file " + csvFilename + "does not exist");
            return false;
        }
        Scanner input = null;
        PrintWriter output = null;
        try{
            String fileRecord;
            input =  new Scanner(file);
            output = new PrintWriter(new File(TEMP_FILE_NAME));
            int  bookingId;
            while (input.hasNextLine()){ 
                  fileRecord =input.nextLine();
                  bookingId  = Integer.parseInt(fileRecord.substring(20, 30).trim());
                  if (bookingId != reservationID) {
                     output.write(fileRecord);
                     output.write("\n");
                  } 
            } 
        }  catch (FileNotFoundException ex) {
           System.out.println( ex.toString());
           return false;
        } finally {
          if (input  != null) input.close();
          if (output != null) output.close();
        }  
          // make current csvFile to old csv file
        if(!renameTheFile(csvFilename, ("old" + csvFilename))) return false;
        
        // make temp file to current csv file
        if (!renameTheFile(TEMP_FILE_NAME, csvFilename)) return false;
        
        // remove record from array
        reservedBooking.remove(count);
        return true;
        
//        for(int i=0; i <reservedBooking.size(); i++  ){
//            if (reservedBooking.get(i).getRoomNumber() == roomNumber) {
//               reservedBooking.remove(i);
//               break;                
//            }
//        }
        
    }
   
}
