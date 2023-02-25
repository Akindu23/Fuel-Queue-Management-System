import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * COPYRIGHT (C) 2022 Akindu Karunaratne (w1898951/20211364). All Rights Reserved.
 * Array version for a Fuel Queue Management System in a fuel center.
 * Solves Software Development 2 (4COSC010.3) Coursework 1 Task 1.
 *
 * @author Akindu Karunaratne
 * @version 1.1 2022-08-08.
 **/

public class FuelCenter {
   private static final int numberOfPumps = 3;
   private static final int maxQueueSize = 6;

   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);
      int fuelStock = 6600; //Fuel Stock in Litres

      String[][] fuelQueue = new String[numberOfPumps][maxQueueSize]; //2D Array for each fuel pump and its 6 queue slots

      //Initialising the empty queue slots for the 3 pumps.
      for (int i = 0; i < numberOfPumps; i++) {
         for (int j = 0; j < maxQueueSize; j++) {
            fuelQueue[i][j] = "empty";
         }
      }
      //To check if data exists from a previous run
      File temp = new File("Task1_Data.txt");
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
                 Enter 103 or RCQ to Remove a customer from a Queue(From a specific location)
                 Enter 104 or PCQ to Remove a served customer.
                 Enter 105 or VCS to View Customers Sorted in alphabetical order.
                 Enter 106 or SPD to Store Program Data into file.
                 Enter 107 or LPD to Load Program Data from file.
                 Enter 108 or STK to View Remaining Fuel Stock.
                 Enter 109 or AFS to Add Fuel Stock.
                 Enter 999 or EXT to Exit the Program
                 """);

         System.out.print("Enter your option: "); //Taking user input for the menu option
         String option = input.nextLine();
         System.out.println();

         //Nested if conditions to execute based on users input above
         if (option.equals("100") || option.equalsIgnoreCase("VFQ")) {
            viewAllQueues(fuelQueue); //To view all fuel queues

         } else if (option.equals("101") || option.equalsIgnoreCase("VEQ")) {
            viewEmptyQueues(fuelQueue); //To view fuel queues only with their empty slots

         } else if (option.equals("102") || option.equalsIgnoreCase("ACQ")) {
            fuelStock = addCustomer(fuelStock, fuelQueue);

         } else if (option.equals("103") || option.equalsIgnoreCase("RCQ")) {
            fuelStock = removeCustomer(fuelStock, fuelQueue);

         } else if (option.equals("104") || option.equalsIgnoreCase("PCQ")) {
            removeServedCustomer(fuelQueue);

         } else if (option.equals("105") || option.equalsIgnoreCase("VCS")) {
            customerSorted(fuelQueue);

         } else if (option.equals("106") || option.equalsIgnoreCase("SPD")) {
            storeProgramData(fuelStock, fuelQueue);   //Stores queue and fuel stock information to "Task1_Data.txt" file.

         } else if (option.equals("107") || option.equalsIgnoreCase("LPD")) {
            fuelStock = loadProgramData(fuelStock, fuelQueue);    //Loads data from previous run

         } else if (option.equals("108") || option.equalsIgnoreCase("STK")) {
            System.out.println(fuelStock + " Litres of Fuel remaining"); //Displays remaining fuel stock.

         } else if (option.equals("109") || option.equalsIgnoreCase("AFS")) {
            fuelStock = addFuelStock(fuelStock);

         } else if (option.equals("999") || option.equalsIgnoreCase("EXT")) {
            System.out.println("Exiting the Program...");
            menu = false;  //Ending loop to exit program.

         } else {    //Checking if an option from the menu was entered. If not program will loop back to take user input.
            System.out.println("Invalid Option!!!\n");
         }
      }
   } //End of Main Method

   //My Methods
   /**
    * This method is to view all fuel queues with customer name if a queue slot is occupied.
    *
    * @param fuelQueue 2D Array collection Fuel Queues (Each Pump and its 6 Slots).
    */
   public static void viewAllQueues(String[][] fuelQueue) {
      System.out.println("Viewing all Fuel Queues\n");
      System.out.println("---------------------------------------------------------------------------------------------");

      for (int i = 0; i < fuelQueue.length; i++) {   //Outer loop in for loop which loops through the rows in fuelQueue array.
         System.out.println("Fuel Queue " + (i + 1));
         for (int j = 0; j < fuelQueue[i].length; j++) { //Inner loop in for loop which loops through the columns in fuelQueue array.
            if (fuelQueue[i][j].equals("empty")) {
               System.out.println("\tQueue slot " + (j + 1) + " is empty.");
            } else {
               System.out.println("\tQueue slot " + (j + 1) + " is occupied by " + fuelQueue[i][j]);
            }
         }
         System.out.println();
         System.out.println("---------------------------------------------------------------------------------------------");
      }
   }

   /**
    * This method is to view all the empty slots in the fuel queues.
    *
    * @param fuelQueue 2D Array collection Fuel Queues (Each Pump and its 6 Slots).
    */
   public static void viewEmptyQueues(String[][] fuelQueue) {
      System.out.println("Viewing all Empty Queues\n");
      System.out.println("---------------------------------------------------------------------------------------------");

      //Checks if all queues are full as there will be no empty queues to display.
      if (!fuelQueue[0][5].equals("empty") && !fuelQueue[1][5].equals("empty") && !fuelQueue[2][5].equals("empty")) {
         System.out.println("All Queues are full! No empty queues to view");
      } else {
         for (int i = 0; i < fuelQueue.length; i++) {
            System.out.println("Fuel Queue " + (i + 1));
            for (int j = 0; j < fuelQueue[i].length; j++) {
               if (fuelQueue[i][j].equals("empty")) {    //Check if index in fuelQueue array has any elements. If not display only the empty queues
                  System.out.println("\tQueue slot " + (j + 1) + " is empty.");
               }
            }
            System.out.println();
            System.out.println("---------------------------------------------------------------------------------------------");
         }
      }
   }

   /**
    * This method is used to add a customer to a fuel queue.
    *
    * @param stock     Remaining fuel stock.
    * @param fuelQueue 2D Array collection Fuel Queues (Each Pump and its 6 Slots).
    * @return Updated fuel stock value after customer is added (10 litres of fuel for each customer).
    */
   public static int addCustomer(int stock, String[][] fuelQueue) {
      Scanner input = new Scanner(System.in);
      String select = "";
      int stockWarningLevel = 500;

      //do-while loop to allow user to enter multiple customers at once without returning to the menu.
      do {
         //checks if all queues are full and returns to menu if it is.
         if (!fuelQueue[0][5].equals("empty") && !fuelQueue[1][5].equals("empty") && !fuelQueue[2][5].equals("empty")) {
            System.out.println("WARNING!!! All queues are full!");
            System.out.println("Please wait for customers to be served to add more customers to the Queues\n");
            break;

         } else {
            //Method to validate queue number called.
            int pump = validateQueueSlotNumber(3, "Enter fuel queue (1,2 or 3): ", "Queue");

            //checks if a specific fuel queue is full and gives user option to enter another fuel queue to add customer
            if (!fuelQueue[pump - 1][maxQueueSize - 1].equals("empty")) {
               System.out.println("Queue " + pump + " is full! Please enter customer to another queue\n");
               continue;

            } else {
               //Method to validate customer name called.
               String name = validateString("Customer name: ", "Customer name");

               for (int i = 0; i < maxQueueSize; i++) {    //Looping through the columns of the fuelQueue array
                  if (fuelQueue[(pump - 1)][i].equals("empty")) { //Checking if slot is empty based on the queue number user inputs.
                     fuelQueue[(pump - 1)][i] = name;             //If queue slot is empty customer name will be set to that index position
                     System.out.println(name + " was successfully added to fuel queue " + pump + "\n");
                     stock -= 10;         //Reducing stock level if customer is added.
                     break;
                  }
               }
            }
            System.out.print("Enter 'A' to add another customer or press enter to return to the menu: ");
            select = input.nextLine();  //Asks user to add another customer or exit to the option menu

         }
         System.out.println();

         if (stock <= stockWarningLevel) {
            System.out.println("WARNING! Fuel stock is below 500 Litres!\n"); //Warning message if stock reaches 500 Litres.
         }

      } while (select.equalsIgnoreCase("A"));

      return stock;
   }

   /**
    * This method is used to remove a customer from a specific queue location.
    *
    * @param stock     Fuel stock available.
    * @param fuelQueue 2D Array collection Fuel Queues (Each Pump and its 6 Slots).
    * @return Updated fuel stock after customer was removed. (Adding 10 litres as customer was not served).
    */
   public static int removeCustomer(int stock, String[][] fuelQueue) {
      Scanner input = new Scanner(System.in);
      String select;

      do {
         //Methods to validate queue number and slot number called.
         int pump = validateQueueSlotNumber(3, "From which queue do you want to remove customer: ", "Queue");
         int slot = validateQueueSlotNumber(6, "From which queue slot?: ", "Slot");

         //Checks if entered queue slot is already empty. Displays error message and returns to menu.
         if (fuelQueue[pump - 1][slot - 1].equals("empty")) {
            System.out.println("\nQueue slot is already empty! Please re-check the queues and try again.\n");
            break;
         } else {
            System.out.println("\n" + fuelQueue[pump - 1][slot - 1] + " was successfully removed from fuel pump "
                    + pump + " queue slot " + slot + "\n");

            for (int i = (slot - 1); i < maxQueueSize - 1; i++) {          //for loop starts based on slot number entered by user.
               fuelQueue[(pump - 1)][i] = fuelQueue[(pump - 1)][i + 1];   //Shifts elements up by 1 index depending on which slot was removed.
            }
            fuelQueue[(pump - 1)][maxQueueSize - 1] = "empty";     //Sets the last index back to "empty" to avoid printing "null"
            stock += 10;                                          //Adding back the stock as customer was not served.

            System.out.print("Enter 'R' to remove another customer from a specific location or press enter to return to the menu: ");
            select = input.nextLine();  //Asks user to remove another customer or exit to the option menu
            System.out.println();
         }
      } while (select.equalsIgnoreCase("R"));

      return stock;
   }

   /**
    * This method is to remove a served customer from a queue (Served customer is the first in line in that particular queue).
    *
    * @param fuelQueue 2D Array collection Fuel Queues (Each Pump and its 6 Slots).
    */
   public static void removeServedCustomer(String[][] fuelQueue) {
      Scanner input = new Scanner(System.in);
      String select;

      do {
         int pump = validateQueueSlotNumber(3, "From which queue was this customer served?: ", "Queue");

         if (fuelQueue[pump - 1][0].equals("empty")) {
            System.out.println("\nNo customer in this queue to serve! Please re-check the queues and try again.\n");
            break;

         } else {
            System.out.println(fuelQueue[pump - 1][0] + " from queue " + pump + " was served fuel");

            for (int i = 0; i < maxQueueSize - 1; i++) {    //Served customer by logic is the first in queue, hence for loop starts from first slot.
               fuelQueue[(pump - 1)][i] = fuelQueue[(pump - 1)][i + 1];   //Shifts elements up by 1 index after first slot was removed.
            }
            fuelQueue[(pump - 1)][maxQueueSize - 1] = "empty";   //Sets the last index back to "empty" to avoid printing "null"

            System.out.print("Enter 'S' to remove another served customer or press enter to return to the menu: ");
            select = input.nextLine();  //Asks user to serve another customer or exit to the option menu
            System.out.println();
         }
      } while (select.equalsIgnoreCase("S"));
   }

   /**
    * This method is to sort customer names in Alphabetical Order.
    *
    * @param fuelQueue 2D Array collection Fuel Queues (Each Pump and its 6 Slots).
    */
   public static void customerSorted(String[][] fuelQueue) {
      String[][] sortedCustomers = new String[numberOfPumps][maxQueueSize]; //Creating new array to store sorted customers

      //Checks if all queues are empty before sorting. If first slot is empty that means the entire queue is empty.
      if (fuelQueue[0][0].equals("empty") && fuelQueue[1][0].equals("empty") && fuelQueue[2][0].equals("empty")) {
         System.out.println("All Queues are empty! No customers to sort");
      } else {
         for (int a = 0; a < fuelQueue.length; a++) {       //This loop copies the customer names of fuelQueue array to sortedCustomers array
            for (int b = 0; b < fuelQueue[a].length; b++) {
               if (!(fuelQueue[a][b].equals("empty"))) {    //Checks if slot is not empty and copies the name
                  sortedCustomers[a][b] = fuelQueue[a][b];
               } else {
                  sortedCustomers[a][b] = "e";               //If slot is empty sets to 'e' (Used later in code)
               }
            }
         }
         for (int x = 0; x < sortedCustomers.length; x++) {             //Loop used to bubble sort sortedCustomers array
            for (int y = 0; y < sortedCustomers[x].length; y++) {
               for (int z = 0; z < sortedCustomers[x].length - y - 1; z++) {
                  if (sortedCustomers[x][z].compareToIgnoreCase(sortedCustomers[x][z + 1]) > 0) {     //Compares to elements next to each other ignoring case.
                     String temp = sortedCustomers[x][z];
                     sortedCustomers[x][z] = sortedCustomers[x][z + 1];
                     sortedCustomers[x][z + 1] = temp;
                  }
               }
            }
         }
         System.out.println("\nViewing Customers sorted in alphabetical order \n");
         for (int i = 0; i < sortedCustomers.length; i++) {          //Loop to display Queue and sorted customer names.
            System.out.println("Fuel Queue " + (i + 1));
            for (int j = 0; j < sortedCustomers[i].length; j++) {
               if (!(sortedCustomers[i][j].equals("e"))) {              //Skips if slot is empty (Line 292).
                  System.out.println("\t" + sortedCustomers[i][j]);
               }
            }
            System.out.println();
         }
      }
   }

   /**
    * This method is to store queue data and fuel stock to a text file.
    *
    * @param stock     Remaining fuel stock.
    * @param fuelQueue 2D Array collection Fuel Queues (Each Pump and its 6 Slots).
    */
   public static void storeProgramData(int stock, String[][] fuelQueue) {
      try {
         FileWriter dataFile = new FileWriter("Task1_Data.txt");  //Creating text file "Task1_Data.txt"
         for (int i = 0; i < fuelQueue.length; i++) {
            for (int j = 0; j < fuelQueue[i].length; j++) {
               dataFile.write(fuelQueue[i][j] + "\n");   //Writing queue data to file
            }
         }
         dataFile.write(String.valueOf(stock)); //Writing remaining stock level to file
         dataFile.close();
         System.out.println("File Updated Successfully!");

      } catch (IOException e) {
         System.out.println("An error occurred!: " + e);
      }
   }

   /**
    * This method loads the saved file back into the program.
    *
    * @param stock     Remaining fuel stock.
    * @param fuelQueue 2D Array collection Fuel Queues (Each Pump and its 6 Slots).
    * @return Fuel stock level.
    */
   public static int loadProgramData(int stock, String[][] fuelQueue) {
      try {
         File loadedFile = new File("Task1_Data.txt");
         Scanner readFile = new Scanner(loadedFile);

         while (readFile.hasNext()) {
            for (int i = 0; i < fuelQueue.length; i++) {
               for (int j = 0; j < fuelQueue[i].length; j++) {
                  fuelQueue[i][j] = readFile.nextLine();    //Reading the queue data from "Task1_Data.txt" file and loads back to fuelQueue array.
               }
            }
            stock = readFile.nextInt();   //Reading the stock level from "Task1_Data.txt" file.
         }
         readFile.close();
         System.out.println("Data has successfully bean loaded!");

      } catch (FileNotFoundException e) {
         System.out.println("File does not exist!\n");   //Try-catch to display error message if file is not created first
      }
      return stock;
   }

   /**
    * This method is used to update the existing fuel stock level.
    *
    * @param stock Available fuel stock.
    * @return Updated fuel stock level.
    */
   public static int addFuelStock(int stock) {
      int maxStock = 6600;
      int newStock = validateFuelAmount();

      //Checking if total stock will be less than 6600 litres to add if not display error message
      if (stock + newStock <= maxStock){
         stock += newStock;   //Adds the stock amount added by the user.
         System.out.println(newStock + " litres of fuel added to stock");

      }else {
         System.out.println("WARNING!!! Maximum stock fuel center can hold is 6600 Litres");
         System.out.println("Current stock level is " + stock + " litres.");
      }

      return stock;
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
         System.out.print("Enter " + displayMessage); //User input for string.
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
            System.out.print(displayMessage);
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
    * This method is used to validate the fuel amount.
    *
    * @return Validated fuel amount.
    */
   public static int validateFuelAmount() {
      Scanner input = new Scanner(System.in);
      int fuelAmount = 0;
      boolean validateFuel = true;

      while (validateFuel) {  // If conditions not satisfied program will loop back to ask for input.
         try {
            System.out.print("Enter amount of fuel to be added: ");
            fuelAmount = Integer.parseInt(input.nextLine());

            if (fuelAmount > 0) {
               validateFuel = false;
            }else {
               System.out.println("Fuel amount has to be greater than 0 litres\n");
            }
         } catch (NumberFormatException e) {
            System.out.println("Amount of fuel has to be an integer!\n");
         }
      }
      return fuelAmount;
   }
}


