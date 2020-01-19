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
class BasicRoom extends Room implements CleanningService{
    public static final int BASIC_ROOM_PRICE_PER_DAY = 55;
    private static int roomNumber = ROOM_TYPE_BASIC_ROOM;
    
    private static final int ROOM_CLEANNING_RATE  = 20;
    private static final int BREAKFAST_PER_PERSON = 15;
    private static final int MASSAGE_PER_PERSON   = 75;
    
    BasicRoom( int newNumberOfPerson, String newCustomerID){
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
