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
class FullSuite extends Room implements CleanningService{
     public static final int FULL_SUITE_PRICE_PER_DAY = 200;
     private static int roomNumber = ROOM_TYPE_FULL_SUITE;
     
    private static final int ROOM_CLEANNING_RATE  = 5;
    private static final int BREAKFAST_PER_PERSON = 0;
    private static final int MASSAGE_PER_PERSON   = 45;
     
     FullSuite( int newNumberOfPerson, String newCustomerID){
       super. setRoomNumber( roomNumber++);
       super. setNumberOfPerson(newNumberOfPerson);
       super. setCustomerID(newCustomerID);
       super. setIsReserved( true);
    }

    @Override
    public double calculateCleanningServiceCharges() {
        double fees=0;
        if (super.getRoomCleanning() == true){
            fees =ROOM_CLEANNING_RATE;
        }
        return fees;
    }

    @Override
    public double calculateBreakFastCharges() {
        double fees=0;
        if (super.getBreakFastInclude()==true){
            fees = (BREAKFAST_PER_PERSON * super.getNumberOfPerson());
        }
        return fees;
    }

    @Override
    public double calculateMassageServiceCharges() {
        double fees =0;
        if (super.getMassageInclude()==true){
            fees = (MASSAGE_PER_PERSON * super.getNumberOfPerson());
        }
        return fees;
    }
  
}
