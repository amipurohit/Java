/*
 *   Program is created on : july 08 2019
 *   Description : This application manage Hotel booking System.
 *   This is a Java Assignment created by : Ami Purohit 
 *                                          Kalpesh Patel 
 *                                           Miral Patel
 *   
 *   parameter required 
 *   @param args[0] for Hotel name
 *   @param args[1] for Booking csv file name
 *   @param args[1] for Customer.txt file name
 * 
 */
package hotelbookingtest;

import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.System.exit;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author : Ami purohit, Miral patel and Kalpesh patel
 */
public class HotelBookingTest {

    /**
     * @param args[0] for Hotel name
     * @param args[1] for Booking csv file name
     * @param args[1] for Customer.txt file name
     */
    public static void main(String[] args){ //throws FileNotFoundException, IOException, ParseException {
        
        String hotelName = " The Five Star Hotel";
        String csvFileName= "HotelReservation.csv";
        String customerFileName= "customerNames.csv";
        
        Scanner input = new Scanner(System.in);
        
        if (args.length != 3){
            System.out.println("Invalid number of parameter !!");
            exit(2);
        }
        if(args[0].length() != 0 ){
            hotelName = args[0];
        }
        if(args[1].length() != 0 ){
            csvFileName = args[1];
        }
        if(args[2].length() != 0 ){
            customerFileName = args[2];
        }
        // create a hotel object
        Hotel hotel = new Hotel(hotelName, csvFileName, customerFileName);
        
        System.out.println("System retriving data from csv file...");
        
        //Read data from csv file
       if (!hotel.uploadBookingFromFile()){
           System.out.println("System can not retrive data from csv file...");
           exit(0);
       }
        System.out.println("/*********************************************************/");
        System.out.println("/********* Welcome to " + hotelName + " Hotel ******************/");
        System.out.println("/*********************************************************/");
     
        String cont= "y";
            
            while(cont.equalsIgnoreCase("y")) 
            {
                System.out.println();
                System.out.println("-----------------------------");
                System.out.println("1. Book Your Stay");
                System.out.println("2. Order Room Service");
                System.out.println("3. Payment/Check Bill");
                System.out.println("4. Exit");
                System.out.println("-----------------------------");
             
                int option = 0;
                try {
                     option = input.nextInt();
                     
                } catch(InputMismatchException e) {
                   System.out.println("Please enter valid value."); 
                   break;
                }
                catch (Exception e){
                    System.out.println(e.toString());
                    break;
                }   
                switch(option) {
                    case 1:

                       int idOfBooking;
                       int totalDaysOfBooking =0;
                       int numberOfPersons;
                       int roomType;
                       String name = null;
                       String email = null;
                       String phone = null;
                       String dateOfBooking = null;
                       
                        // Enter customer name
                        System.out.print("Enter First Name: ");
                        name = checkName(name);
                        // Enter customer phone
                        System.out.print("Enter Your Phone (e.g. 123-123-1234): ");
                        phone = checkPhone(phone);
                        // Enter customer email 
                        System.out.print("Enter Your Email (e.g. me@yahoo.com): ");
                        email = checkEmail(email);
                        // Enter number of person per room
                        System.out.print("Enter number of persons: ");
                        numberOfPersons = Integer.parseInt(checkPerson());
                        // Enter reservation start date
                        System.out.print("Please enter start from today to 2020-12-31: ");
                        dateOfBooking = getDate(dateOfBooking);
                        
                        do {
                           //Enter reservation end date
                           System.out.print("Please enter end date of your stay: ");
                           String dateOfBooking2 = getDate(dateOfBooking);
                           try{
                              //calculate number of days for booking
                              totalDaysOfBooking = getNumberOfDays(dateOfBooking, dateOfBooking2);
                           } catch(ParseException exp){
                              System.out.println(exp.toString());
                              break;
                          } catch(Exception ex){ 
                              System.out.println(ex.toString());
                              break;
                          }
                        }while (totalDaysOfBooking <= 0) ;   
                        
                        //Option to select room type
                         System.out.print("Select Room type Number.\n1. "
                                 + "for Basic Room:(50 CAD/day).\n"
                                 + "2. for Junior Suite:(125 CAD/day).\n3. "
                                 + "for Full Suite:(200 CAD/day).\n");
                        roomType = input.nextInt();
                        input.nextLine();
                        if (roomType == 1){
                            roomType = Room.ROOM_TYPE_BASIC_ROOM; 
                        } else if (roomType == 2){
                            roomType = Room.ROOM_TYPE_JUNIOR_SUITE;
                        }else if (roomType == 3){
                            roomType = Room.ROOM_TYPE_FULL_SUITE;
                        }
                        // check if room is available for rent
                        if (!hotel.isRoomAvailable( roomType)) {
                            System.out.println("All rooms are reserved for this room type!!!");
                             System.out.println("Plese select another room type!!");
                           break;
                        }
                        //create new booking
                        Booking newBooking = new Booking(dateOfBooking,
                                                         totalDaysOfBooking,
                                                         numberOfPersons, 
                                                         roomType,                                                                                      
                                                         name,
                                                         phone,
                                                         email);
                        
                        // add record to booking array
                        hotel.addReservedBookingArray(newBooking);
                        // add record to customer array
                        hotel.addCustomerArray(newBooking.getCustomer());
                        //reserved room for customer
                        int customerRoom =0;
                         switch (roomType) {
                             case  Room.ROOM_TYPE_BASIC_ROOM:
                                   hotel.addBasicRoomArray(newBooking.getBasicRoom());
                                   customerRoom = newBooking.getBasicRoom().getRoomNumber();
                                   break;
                             case Room.ROOM_TYPE_JUNIOR_SUITE:
                                   hotel.addJuniorSuiteArray(newBooking.getJuniorSuite()); 
                                   customerRoom = newBooking.getJuniorSuite().getRoomNumber();
                                   break;                
                             case  Room.ROOM_TYPE_FULL_SUITE:
                                   hotel.addFullSuiteArray(newBooking.getFullSuite());
                                   customerRoom = newBooking.getFullSuite().getRoomNumber();
                                   break;
                        }
                        //save information to csv file 
                        try { 
                            hotel.savetoFile(newBooking);
                        }catch(IOException ioe){
                            System.out.println(ioe.toString());
                        }catch(Exception ep){
                            System.out.println(ep.toString());
                        }    
                        System.out.println("/-----------------------------------------------------------/");
                        System.out.println("/          Room number " + customerRoom + " has been book for " + name );
                        System.out.println("/-----------------------------------------------------------/");
                        
                        break;
                        
                    case 2:
                        // Add room services
                        System.out.println("/*********************************************************/");
                        System.out.println("        Order Room services " + hotelName + " Hotel         ");
                        System.out.println("/*********************************************************/");
                        
                        // enter room number
                        System.out.println("Please enter your room Number:");
                        int customerRoomNumber =0;
                        
                        try{
                           customerRoomNumber = input.nextInt();
                        } catch (InputMismatchException ime){
                            System.out.print("Please enter valide room number");
                            customerRoomNumber = input.nextInt();
                       }    
                          
                        // validate if room is reserved
                        if (!hotel.isRoomReseved (customerRoomNumber)){
                            System.out.println("Invalid room number");
                            break;
                        } 
                        // Ask for room cleanning service     
                        System.out.println("Include room cleanning service: Y/N");
                        String includeRoomCleanning = input.next();
                       
                        // Ask for room break fast service  
                        System.out.println("Include breakFast service: Y/N");
                        String includeBreakFast = input.next();
                        // Ask for massage service
                        System.out.println("Include massage service: Y/N");
                        String includeMassage = input.next();
                        
                        // Get booking record
                        Booking booking = hotel.getReservedBookingByRoomNumber(customerRoomNumber);
                        int customerRoomType = booking.getRoomType();
                        boolean addBreakFast = false;
                        boolean addMassage = false;
                        boolean addRoomCleanning = false;
                        if (includeBreakFast.toUpperCase().charAt(0) == 'Y')
                            addBreakFast = true;
                        
                        if (includeRoomCleanning.toUpperCase().charAt(0) == 'Y')
                            addRoomCleanning = true;
                        
                        if (includeMassage.toUpperCase().charAt(0) == 'Y')
                           addMassage = true;
                        
                        if (customerRoomType == Room.ROOM_TYPE_BASIC_ROOM){
                           BasicRoom basicRoom = booking.getBasicRoom();
                           basicRoom.setRoomCleanning(addRoomCleanning);
                           basicRoom.setBreakFastInclude(addBreakFast);
                           basicRoom.setMassageInclude(addMassage);
                           
                        } else if (customerRoomType == Room.ROOM_TYPE_JUNIOR_SUITE){
                            JuniorSuite juniorSuite = booking.getJuniorSuite();
                            juniorSuite.setRoomCleanning(addRoomCleanning);
                            juniorSuite.setBreakFastInclude(addBreakFast);
                            juniorSuite.setMassageInclude(addMassage);
                            
                        }else if (customerRoomType == Room.ROOM_TYPE_FULL_SUITE){
                             FullSuite fullSuite = booking.getFullSuite();
                             fullSuite.setRoomCleanning(addRoomCleanning);
                             fullSuite.setBreakFastInclude(addBreakFast);
                             fullSuite.setMassageInclude(addMassage);
                        }
                        System.out.println("/-----------------------------------------------------------/");
                        System.out.println("/          Room number " 
                                          + customerRoomNumber + " room service has beed updated" );
                        System.out.println("/-----------------------------------------------------------/");
                        break;
                    case 3:
                       
                        //this IF will check if step 1 is performed.  If not, it will show menu again.
                        System.out.println("/*********************************************************/");
                        System.out.println("/*****Room Payment service " + hotelName + " Hotel*****/");
                        System.out.println("/*********************************************************/");
                        
                        // ask for room number
                        System.out.println("Please enter customer room Number:");
                        int paymentRoom =0;
                         do {
                           try{
                              paymentRoom = input.nextInt();
                           } catch (InputMismatchException ime){
                               System.out.print("Please enter valide room number");
                           }    
                        }while (paymentRoom <=0);  
                        
                        
                        
                        Booking paymentBooking = hotel.getReservedBookingByRoomNumber(paymentRoom);

                        if (paymentBooking == null) {
                            System.out.println("\nPlease Book Your Stay First!\n");
                            continue;
                        }
                        //this step will check new or returning customer and show bill accordingly.
                        double bill_amount =hotel.finalBill(paymentBooking.getCustomer().getCustomerID(),
                                                            paymentBooking.getTotalDaysOfBooking(),
                                                            paymentBooking.getRoomType(),
                                                            paymentBooking);
                        // ask for amount
                        System.out.print("Please enter payment amount : ");
                        double payment_amount = input.nextDouble();
                        while (payment_amount < bill_amount){
                              System.out.println("Amount must be greater to equal to Payment amount!!!");
                              System.out.print("\nPlease enter payment amount: ");
                              payment_amount = input.nextInt();
                        }
                        //following step will input amountreceived in booking class
                        hotel.setAmountReceived(payment_amount);
                        
                        //this step will show remaining balance or paid full.
                        hotel.showFinishedPayment();
                        // remove record from the Booking Arraylist
                        if (!hotel.removeRecordFromSystem(paymentRoom)){
                            System.out.println("Record can not be remove from system!!");
                        } else {
                            System.out.println("Record has been remove from system!!");
                        }
                        break;
                    
                    case 4: 
                     
                        // exit program
                        System.out.println("Are you sure you want to exit the program ?");
                        String out = (input.next()).toUpperCase();
                        if (out.charAt(0) == 'Y'){
                            System.out.println("         ****** THE END ******       ");
                            cont = "N";
                            break;
                        }else {
                            continue;
                        }
                    default:
                        //cont = "n";
                        System.out.println("Please select proper Value..");
                        break;
                }
            }
        
    }//last needed bracket
    
    //All methods to validate...
    //Method to get number of days
    public static int getNumberOfDays (String str, String str1) throws ParseException{
     
        java.text.SimpleDateFormat myFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        Date date1 = myFormat.parse(str);
        Date date2 = myFormat.parse(str1);
        long diff = date2.getTime() - date1.getTime();
        System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
    //method to validate and accept date
    public static String getDate(String dateOfBooking){
        
        java.util.Scanner input = new java.util.Scanner(System.in);
        dateOfBooking = input.next();
        while (!validateDate(dateOfBooking)){
              System.out.println("Date enter is incorrect, Please enter valid date between today to 2020-12-31...");
              dateOfBooking = input.nextLine();
        }
        return dateOfBooking;
     }
    
     //this method will validate numberOfPersons
     public static String checkPerson(){
                        java.util.Scanner input = new java.util.Scanner(System.in);
                        String numberOfPersons = input.next();                       
                        while (!numberOfPersons.matches("\\d?[1-4]")){
                         System.out.print("Maximum 4 persons allowed in one room.  You can booking again for remaining persons :  ");
                         numberOfPersons = input.next();
                        }
                        
                return numberOfPersons;
                
     }//last of checkPerson method
     //this method will validate roomType input 1 to 3.
      public static String checkRoomType(){
                        java.util.Scanner input = new java.util.Scanner(System.in);
                        String roomType = input.next();                       
                        while (!roomType.matches("\\d?[1-3]")){
                         System.out.print("Choose value properly:  ");
                         roomType = input.next();
                        }
                        
                return roomType;
                
        }//last of checkPerson method
    
//this method will call checkRoomType and return integer
public static int checkRoomTypeToInt(){
        String tempStr = checkRoomType();
        
        return Integer.parseInt(tempStr);
        

}

//this method validates First and last name
public static String checkName(String name){
                        java.util.Scanner input = new java.util.Scanner(System.in);
                        name = input.next();
                        while (!name.matches("^[a-zA-Z]*$")){
                              System.out.print("Name should contain alphabets only.  Please enter again:  ");
                              name = input.next();
                        }
                        return name;
    }//last of checkName

//this method will validate phone for formate (123-123-1234)
public static String checkPhone(String phone){
                        java.util.Scanner input = new java.util.Scanner(System.in);
                        phone = input.next();
                        
                        while (!phone.matches("\\d{3}-\\d{3}-\\d{4}")){
                              System.out.print("Error.  Please enter again as per required format:  ");
                              phone = input.next();
                        }
                        return phone;
    }//last of checkPhone

//this method will validate email (me@yahoo.com).  It will check 1. some words then 2. @ sign then 3. some words then 4. (.)dot then 5. some words.
public static String checkEmail(String email){
                        java.util.Scanner input = new java.util.Scanner(System.in);
                        email = input.next();
                        while (!email.matches("\\w+[A-Za-z]@\\w+[A-Za-z].\\w+[A-Za-z]")){
                              System.out.print("Error.  Please enter again as per required format:  ");
                              email = input.next();
                        }
                        return email;
}//last of checkEmail
    
    //Methods part 1 ends
//Date validation methods
    public static boolean validateDate(String userDate){
        // user date lengh must be 10
        if (userDate.length() != 10)return false;
        
        String strYear = userDate.substring(0,4);
        String strMonth = userDate.substring(5,7);
        String strDay = userDate.substring(8,10);
        try {
            int year = Integer.parseInt(strYear);
            int month = Integer.parseInt(strMonth);
            int day = Integer.parseInt(strDay);
           //validate year month and days
           if ((year == 2019 || year == 2020) && (month >0 && month <=12) && (day >0 && day <31)){
              if (validateMonthDays(year, month, day)) {
        
                   LocalDate    currentDate = LocalDate.now();
                   DateTimeFormatter foramatter = DateTimeFormatter.ofPattern("yyy-MM-dd");
                   currentDate.format(foramatter);
                   LocalDate  bookingDate = LocalDate.parse(userDate, foramatter);
                   if (!currentDate.isAfter(bookingDate)) {
                       return true;
                  } else {
                       return false;
                  }
                   
              } else {
                  return false;
              }
           }
        }  catch(NumberFormatException e){
          System.out.println("Please enter vaid number");
          return false;
        }   
        
        return false;
    }
    
    public static boolean validateMonthDays(int year, int month, int days){
        
        // date must between 1 to 31 days
       if (month == 1 || month == 3 || month == 5 || month == 7 || 
           month == 8 || month == 10 || month == 12){
         if (days <=31) return true;
       }
       // date must between 1 to 30 days
       if (month == 4 || month == 6 || month == 9 || month == 11) {
          if (days <=30) return true;
       }   
       //check for feb month and if it is leap year
       if (month == 2) {
           if ( (year % 4) == 0 ) {
             if (days <=28) return true;
           }else {
              if (days <=29) return true;  
           }        
       }
           return false;
       
    }
   
}
