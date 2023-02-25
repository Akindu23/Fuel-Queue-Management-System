import java.io.Serializable;

public class Passenger implements Serializable {

   //Attributes
   private String firstName;
   private String lastName;
   private String vehicleNumber;
   private int fuelAmount;

   //Constructor
   public Passenger(String firstName, String lastName, String vehicleNumber, int fuelAmount) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.vehicleNumber = vehicleNumber;
      this.fuelAmount = fuelAmount;
   }
   public String getFirstName() {
      return this.firstName;
   }
   public String getFullName() {
      return firstName + " " + lastName;
   }
   public String getVehicleNumber() {
      return this.vehicleNumber;
   }
   public int getFuelAmount() {
      return this.fuelAmount;
   }
   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }
   public void setLastName(String lastName) {
      this.lastName = lastName;
   }
   public void setVehicleNumber(String vehicleNumber) {
      this.vehicleNumber = vehicleNumber;
   }
   public void setFuelAmount(int fuelAmount) {
      this.fuelAmount = fuelAmount;
   }

   //Overriding the toString() method
   public String toString(){
      return "\t\tFirst Name     :  " + firstName +
              "\n\t\tLast Name      :  " + lastName +
              "\n\t\tVehicle Number :  " + vehicleNumber +
              "\n\t\tAmount of Fuel :  " + fuelAmount + " litres\n";
   }
}
