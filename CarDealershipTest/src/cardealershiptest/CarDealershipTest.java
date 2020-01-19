/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardealershiptest;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.math.*;
import java.util.ArrayList;

/**
 *
 * @author 1696347
 */
public class CarDealershipTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final String TYPE_COMPACT = "COMPACT";
        final String TYPE_SUV = "SUV";
        final String TYPE_VAN = "VAN";
        final int TOTAL_CARS = 100;  
        
    
         int categId =0;
         int carCount =0;
         int[] cars = new int[3];
         final int COMPACT_COUNT =0;
         final int SUV_COUNT     =1;
         final int VAN_COUNT     =2;
         
            while (carCount < TOTAL_CARS) {
               categId  = 1+(int)(Math.random()*3);
               switch (categId){
                   case 1:
                        cars[COMPACT_COUNT]++;                    
                        carCount++;
                        break;
                   case 2:
                        cars[SUV_COUNT]++;
                        carCount++;                        
                     break;
                   case 3:
                         cars[VAN_COUNT]++;
                         carCount++;
                     break;                   
                   default:  
                     System.out.println("Plese select vaild number:");
                     break;
            } 
        }       
               
            System.out.println("Inventory");
            System.out.println("_____________________________________________________");
            System.out.println();
            double temp = (double)cars[0]/100;
                   temp = temp*100;
            System.out.printf("%20S \t %3d\t %d ", TYPE_COMPACT, cars[COMPACT_COUNT],(int) temp);       
            //System.out.printf("%20S \t %3d\t %d ", TYPE_COMPACT, cars[COMPACT_COUNT], cars[COMPACT_COUNT]);
            System.out.println();
            temp = (double)cars[1]/100;
            temp = temp*100;
            System.out.printf("%20S \t %3d\t %d ", TYPE_SUV, cars[SUV_COUNT], (int)temp);
            System.out.println();
            temp = (double)cars[2]/100;
            temp = temp*100;
            System.out.printf("%20S \t %3d\t %d ", TYPE_VAN, cars[VAN_COUNT], (int)temp);
            System.out.println(); 
            System.out.println("_____________________________________________________");
            
            System.out.println();  
            System.out.printf("Total:              %d", (cars[COMPACT_COUNT] + cars[SUV_COUNT] +cars[VAN_COUNT]));  
            System.out.println(); 
            
            printToFile(cars, carCount);
         
    } 
    public static void printToFile(int[] carSold, int totalCarCount){
       try {
            FileWriter fw = new FileWriter("carDealer.txt");
        
            PrintWriter outputFile = new PrintWriter(fw);
            outputFile.println("Inventory");
            outputFile.println("_____________________________________________________");
            outputFile.println();
           
            outputFile.printf("COMPACT             %3d\t %d %%", carSold[0],carSold[0]);
          
            outputFile.println();
           
            outputFile.printf("SUV                 %3d\t %d %%",  carSold[1],  carSold[1]);
            outputFile.println();
            outputFile.printf("VAN                 %3d\t %d %%",  carSold[2],  carSold[2]);
            outputFile.println();
            outputFile.println("_____________________________________________________");
            outputFile.println();  
            outputFile.printf("Total:              %d", (carSold[0]+carSold[1]+carSold[2]));  
            outputFile.println();
            outputFile.close();
        } catch(IOException e){
              System.out.println("System File can not be save...");
        }           
 
    }    
}
    
    
    
