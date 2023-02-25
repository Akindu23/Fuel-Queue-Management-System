import java.io.*;
import java.util.Scanner;

/**
 * COPYRIGHT (C) 2022 Akindu Karunaratne (w1898951/20211364). All Rights Reserved.
 * Classes version for a Fuel Queue Management System in a fuel center.
 * Solves Software Development 2 (4COSC010.3) Coursework 1 Task 2.
 *
 * @author Akindu Karunaratne
 * @version 1.0 2022-08-08.
 **/

public class FuelCenter implements Serializable {
   private static final double fuelPrice = 430.00;
   private static final int numberOfPumps = 5;
   private static final int maxQueueSize = 6;

   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);
      int fuelStock = 6600; //Fuel Stock in Litres

      FuelQueue[] fuelQueues = new FuelQueue[numberOfPumps]; //Creating array of FuelQueue objects with size for the number of pumps (5).

      initialise(fuelQueues); //Calling initialise method

      //To check if data exists from a previous run
      File temp = new File("Task2_Data.txt");
      if (temp.exists()) {
         System.out.println("\nWARNING! Data from a previous run has been found. If you want to load that data enter '107' or 'LPD' in the menu below");
      }
      boolean menu = true; //Boolean variable menu which allows the program to loop the displayed menu options
      while (menu) {
         //Menu options to be displayed on the console
         System.out.println("""
                                  
                 ------- OPTION MENU -------
                                 
                 Enter 100 or VFQ to View all Fuel Queues.
                 Enter 101 or VEQ to View all Empty Queues.
                 Enter 102 or ACQ to Add customer to a Queue.
                 Enter 103 or RCQ to Remove a customer from a Queue (From a specific location).
                 Enter 104 or PCQ to Remove a served customer.
                 Enter 105 or VCS to View Customers Sorted in alphabetical order.
                 Enter 106 or SPD to Store Program Data into file.
                 Enter 107 or LPD to Load Program Data from file.
                 Enter 108 or STK to View Remaining Fuel Stock.
                 Enter 109 or AFS to Add Fuel Stock.
                 Enter 110 or IFQ to View income of each fuel queue.
                 Enter 999 or EXT to Exit the Program
                 """);

         System.out.print("Enter your option: "); //Taking user input for the menu option
         String option = input.nextLine();
         System.out.println();

         if (option.equals("100") || option.equalsIgnoreCase("VFQ")) {
            viewAllQueues(fuelQueues); //To view all fuel queues

         } else if (option.equals("101") || option.equalsIgnoreCase("VEQ")) {
            viewEmptyQueues(fuelQueues); //To view fuel queues only with their empty slots

         } else if (option.equals("102") || option.equalsIgnoreCase("ACQ")) {
            fuelStock = addCustomer(fuelQueues, fuelStock); //Add customer method called.

         } else if (option.equals("103") || option.equalsIgnoreCase("RCQ")) {
            fuelStock = removeCustomer(fuelQueues, fuelStock); //Remove customer(specific location) method called.

         } else if (option.equals("104") || option.equalsIgnoreCase("PCQ")) {
            removeServedCustomer(fuelQueues); //Remove served customer method called.

         } else if (option.equals("105") || option.equalsIgnoreCase("VCS")) {
            customerSorted(fuelQueues);   //customerSorted method called to view customers sorted in alphabetical order.

         } else if (option.equals("106") || option.equalsIgnoreCase("SPD")) {
            storeProgramData(fuelQueues, fuelStock);   //Stores queue and fuel stock information to "Task2_Data.txt" file.

         } else if (option.equals("107") || option.equalsIgnoreCase("LPD")) {
            fuelStock = loadProgramData(fuelQueues, fuelStock);    //Loads data from previous run

         } else if (option.equals("108") || option.equalsIgnoreCase("STK")) {
            System.out.println(fuelStock + " Litres of Fuel remaining"); //Displays remaining fuel stock.

         } else if (option.equals("109") || option.equalsIgnoreCase("AFS")) {
            fuelStock = addFuelStock(fuelStock); //addFuelStock method called to add fuel to stock.

         } else if (option.equals("110") || option.equalsIgnoreCase("IFQ")) {
            viewIncome(fuelQueues); //viewIncome method called to view income of all fuel queues.

         } else if (option.equals("999") || option.equalsIgnoreCase("EXT")) {
            System.out.println("Exiting the Program...");
            menu = false;  //Ending loop to exit program.

         } else {    //Checking if an option from the menu was entered. If not program will loop back to take user input.
            System.out.println("Invalid Option!!!\n");
         }
      }
   } //End of Main Method

   //My methods

   /**
    * This method initialises the FuelQueue object arrays and assigns a Passenger object for each queue
    *
    * @param queueRef FuelQueue object array.
    */
   private static void initialise(FuelQueue[] queueRef) {
      for (int i = 0; i < queueRef.length; i++) {
         queueRef[i] = new FuelQueue(i + 1, maxQueueSize, 0); //Assigns FuelQueue object to each array index for each pump
         for (int j = 0; j < queueRef[i].getQueueSize(); j++) {
            //Assigns Passenger object to each queue slot in each pump.
            queueRef[i].assignCustomer(new Passenger("empty", "empty", "empty", 0));
         }
      }
   }

   /**
    * This method is to view all fuel queues with customer name if a queue slot is occupied.
    *
    * @param queueRef FuelQueue object array.
    */
   public static void viewAllQueues(FuelQueue[] queueRef) {
      System.out.println("Viewing all Fuel Queues\n");
      System.out.println("---------------------------------------------------------------------------------------------");

      for (FuelQueue fuelQueue : queueRef) {   //Outer loop in for loop which loops through the rows in fuelQueue array.
         System.out.println("Fuel Queue " + fuelQueue.getQueueNumber() + "\n");
         for (int i = 0; i < fuelQueue.getQueueSize(); i++) { //Inner loop in for loop which loops through the columns in fuelQueue array.
            if (fuelQueue.getCustomerDetails(i).getFirstName().equals("empty")) {
               System.out.println("\tQueue slot " + (i + 1) + " is empty.");
            } else {
               System.out.println("\tQueue slot " + (i + 1) + " is occupied by; ");
               System.out.println(fuelQueue.getCustomerDetails(i)); //Printing details of each customer in queues
            }
         }
         System.out.println();
         System.out.println("---------------------------------------------------------------------------------------------");
      }
   }

   /**
    * This method is to view all the empty slots in the fuel queues.
    *
    * @param queueRef FuelQueue object array.
    */
   public static void viewEmptyQueues(FuelQueue[] queueRef) {
      System.out.println("Viewing all Empty Queues\n");
      System.out.println("---------------------------------------------------------------------------------------------");

      for (FuelQueue fuelQueue : queueRef) {
         System.out.println("Fuel Queue " + fuelQueue.getQueueNumber() + "\n");

         for (int i = 0; i < fuelQueue.getQueueSize(); i++) {

            //Check if index in queueRef array has any elements. If not display only the empty queues
            if (fuelQueue.getCustomerDetails(i).getFirstName().equals("empty")) {
               System.out.println("\tQueue slot " + (i + 1) + " is empty.");
            }
         }
         System.out.println();
         System.out.println("---------------------------------------------------------------------------------------------");
      }
   }

   /**
    * This method is used to add a customer to a fuel queue.
    *
    * @param queueRef FuelQueue object array.
    * @param stock    Remaining fuel stock.
    * @return Updated fuel stock.
    */
   public static int addCustomer(FuelQueue[] queueRef, int stock) {
      Scanner input = new Scanner(System.in);
      String select = "";
      int stockWarningLevel = 500;
      int fullSlots = 0;   //Number of queue slots full
      int fullQueues;
      int[] occupiedQueueSlots = new int[numberOfPumps]; //Array to store available slots in each pump.

      do {
         //For loop to check each fuel queue to check how many slots are full
         for (int i = 0; i < queueRef.length; i++) {
            for (int j = 0; j < queueRef[i].getQueueSize(); j++) {
               if (!queueRef[i].getCustomerDetails(j).getFirstName().equals("empty")) {
                  fullSlots++;
               }
            }
            occupiedQueueSlots[i] = fullSlots; //Assigns number of full slots to relevant pump
            fullSlots = 0;
         }
         int pump = 0;
         int minLength = occupiedQueueSlots[pump]; //Sets fuel pump with minimum queue length to the first pump by default.

         //For loop to check which pump has the minimum number of full slots (Queue with minimum length).
         for (int i = 1; i < occupiedQueueSlots.length; i++) {
            if (occupiedQueueSlots[i] < minLength) {
               minLength = occupiedQueueSlots[i];
               pump = i; //Sets pump to queue with minimum length to be used later.
            }
         }
         //Calling method to check how many queues are full
         fullQueues = fullQueueChecker(occupiedQueueSlots);

         //Check if all queues are full and if so returns user to menu. If not adds customer
         if (fullQueues == numberOfPumps) {
            System.out.println("WARNING!!! All queues are full!");
            System.out.println("Please wait for customers to be served to add more customers to the Queues\n");

         } else {
            //Validation methods for customers first name, last name, vehicle number and requested fuel amount called.
            String firstName = validateString("customer's first name: ", "Customer first name");
            String lastName = validateString("customer's last name: ", "Customer last name");
            String vehicleNumber = validateVehicle();
            int fuelAmount = validateFuelAmount("requested by customer");

            if (fuelAmount < stock) {
               for (int i = 0; i < maxQueueSize; i++) {    //Looping through the columns of the fuelQueue array
                  if (queueRef[pump].getCustomerDetails(i).getFirstName().equals("empty")) { //Checks if slot is empty to add and replace

                     //Adds customer using setters from Passenger class to pump assigned from line 198.
                     queueRef[pump].getCustomerDetails(i).setFirstName(firstName);
                     queueRef[pump].getCustomerDetails(i).setLastName(lastName);
                     queueRef[pump].getCustomerDetails(i).setVehicleNumber(vehicleNumber);
                     queueRef[pump].getCustomerDetails(i).setFuelAmount(fuelAmount);

                     stock -= queueRef[pump].getCustomerDetails(i).getFuelAmount(); //Stock updated with requested fuel amount reduced
                     System.out.println("\n" + queueRef[pump].getCustomerDetails(i).getFullName() + " was successfully added to fuel queue " + (pump + 1) + "\n");
                  }
               }
            } else {
               System.out.println("WARNING!!! Cannot add customer as requested fuel amount is more than available stock!");
               System.out.println("Press enter to return to menu and update stock to add more customers!\n");
            }
            System.out.print("Enter 'A' to add another customer or press enter to return to the menu: ");
            select = input.nextLine();  //Asks user to add another customer or exit to the option menu
            System.out.println();
         }
         if (stock <= stockWarningLevel) {
            System.out.println("WARNING! Fuel stock is below 500 Litres!\n"); //Warning message if stock reaches 500 Litres.
         }

      } while (select.equalsIgnoreCase("A"));

      return stock;
   }

   /**
    * This method is used to remove a customer from a specific queue location.
    *
    * @param queueRef FuelQueue object array.
    * @param stock    Remaining fuel stock.
    * @return Updated fuel stock.
    */
   public static int removeCustomer(FuelQueue[] queueRef, int stock) {
      Scanner input = new Scanner(System.in);
      String select = "";
      do {
         //Calling validation method for Queue number and Slot number.
         int pump = validateQueueSlotNumber(5, "fuel queue do you want to remove customer: ", "Queue");
         int slot = validateQueueSlotNumber(6, "queue slot do you want to remove customer: ", "Slot");

         //Checks if slot is already empty. If so returns user to menu.
         if (queueRef[pump - 1].getCustomerDetails(slot - 1).getFirstName().equals("empty")) {
            System.out.println("\nQueue slot is already empty! Please re-check the queues and try again.\n");

         } else {
            System.out.println("\n" + queueRef[pump - 1].getCustomerDetails(slot - 1).getFullName() + " was successfully removed from fuel pump "
                    + pump + " queue slot " + slot + "\n");
            //Adding back customers requested fuel to stock as customer was not served
            stock += queueRef[pump - 1].getCustomerDetails(slot - 1).getFuelAmount();

            //Removing customer by calling removeCustomer method from FuelQueue class
            queueRef[pump - 1].removeCustomer(slot - 1);

            //Initialising last index in ArrayList with new Passenger object to avoid printing null.
            queueRef[pump - 1].assignCustomer(new Passenger("empty", "empty", "empty", 0));

            System.out.print("Enter 'R' to remove another customer from a specific location or press enter to return to the menu: ");
            select = input.nextLine();  //Asks user to remove another customer or exit to the option menu
            System.out.println();
         }

      } while (select.equalsIgnoreCase("R"));

      return stock;
   }

   /**
    * This method is to remove a served customer from a queue.
    *
    * @param queueRef FuelQueue object array.
    */
   public static void removeServedCustomer(FuelQueue[] queueRef) {
      Scanner input = new Scanner(System.in);
      String select = "";
      do {
         //Queue number validation method called.
         int pump = validateQueueSlotNumber(5, "fuel queue was customer served?: ", "Queue");

         //Stores amount of fuel the customer requested using getter from Passenger class.
         int requestedFuel = queueRef[pump - 1].getCustomerDetails(0).getFuelAmount();

         //Sets the requested fuel to the total fuel amount served in that queue. Used when calculating income for each queue.
         queueRef[pump - 1].setTotalFuel(requestedFuel);

         //Checks if queue is already empty
         if (queueRef[pump - 1].getCustomerDetails(0).getFirstName().equals("empty")) {
            System.out.println("\nNo customer in this queue to serve! Please re-check the queues and try again.\n");

         } else {
            System.out.println("\n" +
                    queueRef[pump - 1].getCustomerDetails(0).getFullName() + " (Vehicle Number: " +
                    queueRef[pump - 1].getCustomerDetails(0).getVehicleNumber() + ") from queue " + pump + " was served " +
                    queueRef[pump - 1].getCustomerDetails(0).getFuelAmount() + " litres of fuel\n"
            );

            //Removes customer using removeCustomer method from FuelQueue class. Slot is 0 as the customer served is the first customer in the queue.
            queueRef[pump - 1].removeCustomer(0);

            //Initialising last index in ArrayList with new Passenger object to avoid printing null.
            queueRef[pump - 1].assignCustomer(new Passenger("empty", "empty", "empty", 0));

            System.out.print("Enter 'S' to remove another served customer or press enter to return to the menu: ");
            select = input.nextLine();  //Asks user to serve another customer or exit to the option menu
            System.out.println();
         }
      } while (select.equalsIgnoreCase("S"));
   }

   /**
    * This method is to sort customer names of each queue in Alphabetical Order.
    *
    * @param queueRef FuelQueue object array.
    */
   public static void customerSorted(FuelQueue[] queueRef) {
      System.out.println("Viewing Customers sorted in alphabetical order\n");

      //For each loop to print each queue separately.
      for (FuelQueue fuelQueue : queueRef) {
         System.out.println("Fuel Queue " + fuelQueue.getQueueNumber());
         fuelQueue.customersSorted(); //Calling customersSorted method from Fuel Queue class.
         System.out.println();
      }
   }

   /**
    * This method stores queue data and fuel stock to a text file using serialization.
    * <a href="https://www.geeksforgeeks.org/serialization-in-java/">Code for serialization adapted from this link.</a>
    *
    * @param queueRef FuelQueue object array.
    * @param stock    Remaining fuel stock.
    */
   public static void storeProgramData(FuelQueue[] queueRef, int stock) {
      try {
         FileOutputStream fileOutputStream = new FileOutputStream("Task2_Data.txt");
         ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

         for (FuelQueue fuelQueue : queueRef) {
            objectOutputStream.writeObject(fuelQueue);
         }
         objectOutputStream.writeObject(stock);
         objectOutputStream.close();
         System.out.println("Data successfully stored to file!");

      } catch (IOException e) {
         System.out.println("An error occurred!: " + e);
      }
   }

   /**
    * This method loads the saved file back into the program using deserialization.
    * <a href="https://www.geeksforgeeks.org/serialization-in-java/">Code for deserialization adapted from this link.</a>
    *
    * @param queueRef FuelQueue object array
    * @param stock    Remaining fuel stock.
    * @return Updated fuel stock.
    */
   public static int loadProgramData(FuelQueue[] queueRef, int stock) {
      try {
         FileInputStream fileInputStream = new FileInputStream("Task2_Data.txt");
         ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

         for (int i = 0; i < queueRef.length; i++) {
            queueRef[i] = (FuelQueue) objectInputStream.readObject();
         }
         stock = (int) objectInputStream.readObject();
         objectInputStream.close();
         System.out.println("File successfully loaded!");

      } catch (IOException e) {
         System.out.println("File does not exist!" + e);
      } catch (ClassNotFoundException e1) {
         System.out.println("Class not found!");
      }
      return stock;
   }

   /**
    * This method is used to add fuel to the existing stock.
    *
    * @param stock Remaining fuel stock.
    * @return Updated fuel stock.
    */
   public static int addFuelStock(int stock) {
      int maxStock = 6600;
      int newStock = validateFuelAmount("to be added to stock");

      //Checking if total stock will be less than 6600 litres to add if not display error message
      if (stock + newStock <= maxStock) {
         stock += newStock;   //Adds the stock amount added by the user.
         System.out.println(newStock + " litres of fuel added to stock");

      } else {
         System.out.println("WARNING!!! Maximum stock fuel center can hold is 6600 Litres");
         System.out.println("Current stock level is " + stock + " litres.");
      }

      return stock;
   }

   /**
    * This method is used to view the total income of all queues.
    *
    * @param queueRef FuelQueue object array.
    */
   public static void viewIncome(FuelQueue[] queueRef) {
      System.out.println("Viewing Incomes of all Queues\n");

      //For each loop to display total income of each queue.
      for (FuelQueue fuelQueue : queueRef) {
         //printf used to format income to 2 decimal places. Total fuel from each queue is taken from getTotalFuel method from FuelQueue class.
         System.out.printf("\tFuel Queue " + fuelQueue.getQueueNumber() + ": Rs. %.2f\n", (fuelQueue.getTotalFuel() * fuelPrice));
      }
   }

   /**
    * This method is used to validate strings in this code.
    *
    * @param displayMessage The text to display to user when asking for string input.
    * @param errorMessage   Text to display if error occurs.
    * @return Validated string name.
    */
   public static String validateString(String displayMessage, String errorMessage) {
      Scanner input = new Scanner(System.in);
      String stringName = "";
      boolean validateString = true;

      while (validateString) {   // If conditions not satisfied program will loop back to ask for input.
         System.out.print("Enter " + displayMessage);
         stringName = input.nextLine();

         //stringName.matches("[a-zA-Z]+") section of code taken from https://www.tutorialkart.com/java/how-to-check-if-string-contains-only-alphabets-in-java/
         if (stringName.matches("[a-zA-Z]+") && !(stringName.isEmpty())) {
            validateString = false;
         } else {
            System.out.println(errorMessage + " is in an incorrect format!\n");
         }
      }
      return stringName;
   }

   /**
    * This method is used to validate customer's vehicle number.
    *
    * @return Validated vehicle number.
    */
   public static String validateVehicle() {
      Scanner input = new Scanner(System.in);
      String vehicleName = "";
      boolean validateVehicle = true;

      while (validateVehicle) {  // If conditions not satisfied program will loop back to ask for input.
         System.out.print("Enter customer's vehicle number: ");
         vehicleName = input.nextLine();

         if (!(vehicleName.isEmpty())) {
            validateVehicle = false;
         } else {
            System.out.println("Vehicle number cannot be empty! Please re-enter vehicle number\n");
         }
      }
      return vehicleName;
   }

   /**
    * This method is used to validate queue number or slot number.
    *
    * @param value          Used to check the range depending on queue (3) or queue slot (6).
    * @param displayMessage The text to display to user when asking for input.
    * @param errorMessage   Text to display if error occurs.
    * @return Validated integer value for queue number or slot number
    */
   public static int validateQueueSlotNumber(int value, String displayMessage, String errorMessage) {
      Scanner input = new Scanner(System.in);
      int number = 0;
      boolean validateInteger = true;

      while (validateInteger) {  // If conditions not satisfied program will loop back to ask for input.
         try {
            System.out.print("From which " + displayMessage);
            number = Integer.parseInt(input.nextLine());

            if (number >= 1 && number <= value) {
               validateInteger = false;
            } else {
               System.out.println(errorMessage + " number has to be between 1 - " + value + "\n");
            }
         } catch (NumberFormatException e1) {
            System.out.println("Please enter an Integer Value!\n");
         }
      }
      return number;
   }

   /**
    * This method is used to validate the fuel amount entered by user.
    *
    * @param displayMessage The text to display to user when asking for amount of fuel for customer or adding to stock.
    * @return Validated fuel amount.
    */
   public static int validateFuelAmount(String displayMessage) {
      Scanner input = new Scanner(System.in);
      int fuelAmount = 0;
      boolean validateFuel = true;

      while (validateFuel) {  // If conditions not satisfied program will loop back to ask for input.
         try {
            System.out.print("Enter amount of fuel " + displayMessage + ": ");
            fuelAmount = Integer.parseInt(input.nextLine());

            if (fuelAmount > 0) {
               validateFuel = false;
            } else {
               System.out.println("Fuel amount has to be greater than 0 litres\n");
            }
         } catch (NumberFormatException e) {
            System.out.println("Amount of fuel has to be an integer!\n");
         }
      }
      return fuelAmount;
   }

   /**
    * This method checks how many queues are full.
    *
    * @param occupiedSlots Array with available slots of each queue.
    * @return Number of full queues.
    */
   public static int fullQueueChecker(int[] occupiedSlots) {
      int fullQueues = 0;

      for (int fullSlots : occupiedSlots) {
         if (fullSlots == maxQueueSize) {
            fullQueues++;
         }
      }
      return fullQueues;
   }
}

