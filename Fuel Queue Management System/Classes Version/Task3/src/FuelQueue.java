import java.io.Serializable;
import java.util.ArrayList;
public class FuelQueue implements Serializable {

   //Attributes
   private final ArrayList<Passenger> customerQueue;
   private int queueNumber;
   private final int queueSize;
   private int totalFuel;

   //Constructor
   public FuelQueue(int queueNumber, int queueSize, int totalFuel) {
      this.queueNumber = queueNumber;
      this.totalFuel = totalFuel;
      this.queueSize = queueSize;
      this.customerQueue = new ArrayList<>(queueSize);
   }
   public Passenger getCustomerDetails(int queueSlot) {
      return customerQueue.get(queueSlot);
   }
   public int getQueueNumber() {
      return queueNumber;
   }
   public void setQueueNumber(int queueNumber) {
      this.queueNumber = queueNumber;
   }
   public int getQueueSize() {
      return queueSize;
   }
   public int getTotalFuel() {
      return totalFuel;
   }
   public void setTotalFuel(int totalFuel) {
      this.totalFuel += totalFuel;
   }
   public void assignCustomer(Passenger customer) {
      customerQueue.add(customer);
   }
   public void removeCustomer(int slot) {
      customerQueue.remove(slot);
   }

   //Method to sort customers in alphabetical order
   public void customersSorted() {
      ArrayList<String> sortedCustomers = new ArrayList<>();

      for (Passenger customer : customerQueue) {
         if (!customer.getFirstName().equals("empty")) {
            sortedCustomers.add(customer.getFullName());
         }
      }
      sortedCustomers.sort(String.CASE_INSENSITIVE_ORDER);

      for (String sortedCustomer : sortedCustomers) {
         if (!(sortedCustomer.equals("empty"))) {
            System.out.println("\t" + sortedCustomer);
         }
      }
   }
}
