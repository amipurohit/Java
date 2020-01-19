/* Ask user to enter case register date.
 * This program will ask user to enter product, amount and quantity to generate transaction.
 * This transcation are save in CaseRegister.txt file in project directory.
 * Also display productQuantity by productCode.
 */
package cashregister;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Student number :1696347
 * @author Ami purohit
 * Created date :June 16 2019
 * Description : Java I Class Assignment
 */
public class CashRegister {

    /**
     * @param args the command line arguments
     */
     String userDate;
    
    // main method
    public static void main(String[] args) {
         //Intializing arrays size    
         final int NUMBER_OF_RECORDS = 1000;
         String[] code        = new String[NUMBER_OF_RECORDS];
         double[] amount      = new double[NUMBER_OF_RECORDS];
         int[] quantity       = new int[NUMBER_OF_RECORDS];
         double[] totalAmount = new double[NUMBER_OF_RECORDS];
         int count =0; // number of records created by user
         boolean fileAppend = false; // only for first time new file has to created
         
            String cont = "y";
            //validate date value enter by user.
            Scanner input = new Scanner(System.in);
            System.out.println("****************************************************************" );
            System.out.println("*************      CASH REGISTER PROGRAM      ******************" );
            System.out.println("****************************************************************" );
            System.out.println("please enter Today's date (ex. yyyy-mm-dd)");
            String userDate = input.nextLine();
            
            while (!validateDate(userDate)){
                System.out.println("Date enter is incorrect, Please enter valid date between 2019-01-01 to 2020-12-31...");
                userDate = input.nextLine();
            }
            
            // while loop for selecting transction different options
            while(cont.equalsIgnoreCase("y")) 
            {
                                       
                System.out.println("Please select one of the following option:");
                System.out.println("0. Exit program");
                System.out.println("1. Register Transactions");
                System.out.println("2. Payment");
                System.out.println("3. Close Day");
                int option = 0;
                try {
                     option = input.nextInt();
                } catch (Exception e){
                    System.out.println("Please enter valid value.");
                    break;
                }    
                switch(option) {
                    case 0:
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
                     
                    case 1:
                                             
                        char contTrans = 'Y';
                        // create transcation                  
                        System.out.println("------------------ REGISTER TRASACTION--------------------------" );
                        
                        do {
                            while(!registerTransaction(code, amount, quantity, totalAmount, count)){
                                 System.out.println("Not a valid number");
                            }
                            
                            System.out.println("Do you want to continue ? Enter 'Y': ");
                            String nextTrans = (input.next()).toUpperCase();
                            if (nextTrans.charAt(0) == 'Y'){
                                contTrans = nextTrans.charAt(0);
                                count++;
                            }  else {
                                contTrans = 'N';
                                count++;
                            } 
                        } while (contTrans == 'Y' && count < NUMBER_OF_RECORDS ) ;
                        // display trasction info on console
                        displayTransactionInfo(code, amount, quantity, totalAmount, count);
                        break;
                    case 2:
                        // Calculate total payment and receive payment amount.
                        System.out.println("------------------  TRANSACTION PAYMENT--------------------------" );
                        double totalAmountPayable = calculateTotalAmountPayable(totalAmount, count);
                        
                        
                        System.out.printf("Total amount Payable        : %1.2f \n", totalAmountPayable);
                        System.out.print("Please enter payment amount : ");
                        double amountReceive;
                        try{
                           amountReceive = input.nextDouble();
                        } catch(Exception e){
                             System.out.println("Please enter valid data...");
                             continue;
                        }         
                        System.out.println();
                        System.out.printf("                    Balance : %1.2f \n", (totalAmountPayable- amountReceive ));
                        System.out.println();
                        
                        //save all information to caseregiste.txt 
                        if (SaveToFile(code, amount, quantity, totalAmount,  totalAmountPayable, amountReceive, count, fileAppend, userDate)) {
                           System.out.println("File has been save");
                        }   
                        fileAppend = true;
                        System.out.println("------------------  TRANSACTION PAYMENT RECEIVE--------------------------" );
                        break;
                    case 3: 
                        // Display transction quntity by product code and total payment amount 
                        System.out.println("------------------CLOSE TRANSCATION--------------------------" );
                        System.out.print("Calculating total amount....\n");
                        totalAmountPayable = calculateTotalAmountPayable(totalAmount, count);
                        System.out.print("Displaying Quantity by Product code....\n");
                        displayQuantityByProductCode(totalAmountPayable, code, quantity, count, fileAppend , userDate);
                        fileAppend = true;
                        System.out.println("------------------DAY CLOSED----------------------------------\n" );
                        
                        break;
                    default:
                        System.out.println("Please select valid option ...");
                        
                        break;
                }
            }
       
    }
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
           if ((year == 2019 || year == 2020) && (month >0 && month <12) && (day >0 && day <31)){
              if (validateMonthDays(year, month, day)) {
                  return true;
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
    
       /*  Ask user to enter product code, amount and quantity */
       public static boolean registerTransaction(String[] productCode, double[] amount, int[] quantity, double[] total, int count){
           Scanner input = new Scanner(System.in);
           String icode;
           int iquantity;
           double damount;
           try {
              System.out.println("please enter product code :");
              icode = input.nextLine();
              
              System.out.println("please enter amount :");
              damount = input.nextDouble();
              if (damount <= 0) {
                  System.out.println("please enter value must be more then 0:");
                  return false;
              }
              System.out.println("please enter quantity :");
              iquantity = input.nextInt();
              if (iquantity <= 0) {
                  System.out.println("please enter value must be more then 0:");
                  return false;
              }
              // save date to Array
              productCode[count] = icode;
              amount[count]      = damount;
              quantity[count]    = iquantity;
              total[count]       = damount * iquantity;
              
           }catch(Exception e){
              return false;
           }
           return true;
       }
       /* Display transaction info to console program*/
       public static void displayTransactionInfo (String[]code, double[]amount, int[]quantity, double[]totalAmount, int count)
       {
           System.out.println("----------------------------------------------------------" );
           System.out.println("Product Code    price      Quantity      Total Amount     " );
           System.out.println("----------------------------------------------------------" );
           //display transactiondata from array to console
           for (int i =0; i < count; i++){
               System.out.printf("%-17s", code[i] ); 
               System.out.printf("%-13.2f", amount[i] ); 
               System.out.printf("%-15d", quantity[i] ); 
               System.out.printf("%-10.2f \n", totalAmount[i] ); 
           }
           System.out.println("----------------------------------------------------------" );
       }
       
       /* Calculate total payment amount and return value */
       public static double calculateTotalAmountPayable(double[] dtotalAmount , int count){    
             double dtotalAmountPayable =0;
             for (int i =0; i < count; i++){
                 dtotalAmountPayable += dtotalAmount[i];
             }
             return dtotalAmountPayable;
       }
       /* Save transation data from Array to file   */
       public static boolean SaveToFile(String[]code, 
                                        double[]amount, 
                                        int[]quantity,
                                        double[]totalAmount, 
                                        double totalPayment, 
                                        double amountRec,  
                                        int count,
                                        boolean fileAppend,
                                        String date){
            try{
                // create file name with date.
                String fileName ="RegisterDate".concat(date);
                       fileName = fileName.concat(".txt");
               
                FileWriter fw = new FileWriter(fileName, fileAppend);
                try (PrintWriter pw = new PrintWriter(fw)) {
                    pw.println( );
                    pw.println();
                    pw.println();
                    pw.println("                         Date :" + date );
                    pw.println("----------------------------------------------------------\n" );
                    pw.println("Product Code      Price         Quantity       Total Amount\n" );
                    pw.println("----------------------------------------------------------\n" );
                    for (int i =0; i < count; i++){
                        pw.printf("%-17S ", code[i]);
                        pw.printf("%-13.2f ", amount[i] );
                        pw.printf("%-15d", quantity[i] );
                        pw.printf("%-10.2f \n", totalAmount[i] );
                        pw.println();
                    }
                    pw.println("----------------------------------------------------------\n" );
                    pw.println();
                    pw.println("\tTotal Payment:  "  + totalPayment);
                    pw.println("\tTotal Receive: -" + amountRec);
                    pw.println("\t________________________________" );
                    pw.println("\t      Balance: =" + (amountRec - totalPayment));
                    pw.println();
                }
        } catch(IOException e){
              System.out.println("System File can not be save...");
              return false;
        }     
        return true;    
    }
       
    /* Calculate quantity  by product code */  
    public static void displayQuantityByProductCode(double amountPayable, 
                                                    String[] productCode, 
                                                    int[]productQuantity, 
                                                    int count,
                                                    boolean appendFile,
                                                    String userDate){ 
        try{
                // create file name with date.
                String fileName ="RegisterDate".concat(userDate);
                       fileName = fileName.concat(".txt");
                //Create fileWriter object
                FileWriter fw = new FileWriter(fileName, appendFile);
            try ( // pass fileWriter to printWriter
                    PrintWriter pw = new PrintWriter(fw)) {
                pw.println("                         Date :" + userDate + "\n\n"); 
                pw.println("----------------------------------------------------------" );
                pw.println("Product Code     Quantity                                 " );
                pw.println("----------------------------------------------------------" );
                // create temp arrays product code and quantity
                String[] tempCodeArray     = new String [productCode.length];
                int[] tempQuantityArray    = new int [productCode.length];
                //temp variables
                String tempCode;
                int tempQuantity =0;
                int productCount =0;
                
                boolean productExist;
                //cheack all the transaction with same product code
                for (int i=0; i < count ;i++) {
                    productExist= false;
                    // check if record already exist in temp productcode array
                    if (productCount >0 ){
                        for (int ii =0; ii < productCount; ii++){
                            if ( tempCodeArray[ii].equalsIgnoreCase(productCode[i])){
                                productExist= true;
                            }
                        }    
                    }
                    // if product code found skip the record, get next record.
                    if (productExist) continue;
                    
                    //copy prouctcode to temp variable
                    tempCode     = productCode[i];
                    
                    //check all trasaction with same produccode and accumulate product quntity
                    for (int j=0; j < count ; j++){
                        if (tempCode.equalsIgnoreCase(productCode[j])){
                            tempQuantity   += productQuantity[j];
                        }
                    }
                    // copy temp productcode and temp total quntity to temp arrays
                    tempCodeArray[productCount]     = tempCode;
                    tempQuantityArray[productCount] = tempQuantity;
                    // increase product counter for temp arrays;
                    productCount++;
                    // intialize thetemp value
                    tempCode = "";
                    tempQuantity = 0;
                }
                //save data from temp product array to file...
                for (int i =0; i < productCount; i++){
                    if (tempCodeArray[i].length()!= 0){
                        pw.printf("%-17S",tempCodeArray[i] );
                        pw.printf("%-15d", tempQuantityArray[i] );
                        pw.println();
                    }  
                }
                //print total sale amount
                pw.println("----------------------------------------------------------" );
                pw.printf("\tTotal Sale amount %10.2f:",amountPayable);
                pw.println();
                pw.println("----------------------------------------------------------" );
            }
        } catch(IOException e){
              System.out.println("System File can not be save...");
        }       
       
    }
}     