package com.example.task4;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * COPYRIGHT (C) 2022 Akindu Karunaratne (w1898951/20211364). All Rights Reserved.
 * JavaFX for a Fuel Queue Management System in a fuel center.
 * Solves Software Development 2 (4COSC010.3) Coursework 1 Task 4.
 *
 * @author Akindu Karunaratne
 * @version 1.0 2022-08-08.
 **/
public class Task4Controller {
   @FXML
   private TextArea fuelQueues;
   @FXML
   private TextArea waitingQueue;
   @FXML
   private TextField searchBar;

   @FXML
   protected void viewQueues() {
      String queueDetails = "";  //String to store queue details
      String waitingDetails = "";   //String to store waiting queue details.
      FuelQueue[] queueNames = FuelCenter.getFuelQueues();  //Getting Fuel Queue data from Main class
      Passenger[] waitingNames = WaitingQueue.getWaitingQueue(); //Getting Waiting queue data from Main class

      for (int i = 0; i < FuelCenter.numberOfPumps; i++) {   //Outer loop in for loop which loops through the rows in fuelQueue array.
         queueDetails += "Fuel Queue " + (i + 1) + "\n";

         for (int j = 0; j < FuelCenter.maxQueueSize; j++) { //Inner loop in for loop which loops through the columns in fuelQueue array.
            if (queueNames[i].getCustomerDetails(j).getFirstName().equals("empty")) {
               queueDetails += "\tQueue slot " + (j + 1) + " is empty.\n";

            } else {
               queueDetails += "\tQueue slot " + (j + 1) + " is occupied by; \n";
               queueDetails += queueNames[i].getCustomerDetails(j);
            }
            queueDetails += "\n";
         }
         fuelQueues.setText(queueDetails);
      }
      if (!WaitingQueue.isEmpty()) {
         for (int i = 0; i < waitingNames.length; i++) {
            if (waitingNames[i] != null) {
               waitingDetails += "Customer " + (i + 1) + "\n";
               waitingDetails += "\tFirst name           : " + waitingNames[i].getFirstName() + "\n";
               waitingDetails += "\tLast name            : " + waitingNames[i].getLastName() + "\n";
               waitingDetails += "\tVehicle number  : " + waitingNames[i].getVehicleNumber() + "\n";
               waitingDetails += "\tAmount of fuel   : " + waitingNames[i].getFuelAmount() + " litres\n";
               waitingDetails += "\n";
            }
         }
      } else {
         waitingDetails += "Waiting queue is empty! \n";
      }
      waitingQueue.setText(waitingDetails);
   }

   @FXML
   protected void searchCustomer() {
      String queueDetails = "";
      String waitingDetails = "";
      boolean isQueueNameFound = false;
      boolean isWaitingNameFound = false;

      //Setting text areas to empty to display the results
      fuelQueues.setText("");
      waitingQueue.setText("");

      FuelQueue[] queueNames = FuelCenter.getFuelQueues();
      Passenger[] waitingNames = WaitingQueue.getWaitingQueue();

      String customerName = searchBar.getText(); //Getting input from user

      for (int i = 0; i < FuelCenter.numberOfPumps; i++) {
         for (int j = 0; j < FuelCenter.maxQueueSize; j++) {
            //Checking if customer found in fuel queue
            if (queueNames[i].getCustomerDetails(j).getFirstName().equalsIgnoreCase(customerName)) {
               queueDetails += "Fuel Queue " + queueNames[i].getQueueNumber() + "\n";
               queueDetails += queueNames[i].getCustomerDetails(j) + "\n";
               isQueueNameFound = true;
            }
         }
      }

      for (int x = 0; x < waitingNames.length; x++) {
         //Checking if customer found in waiting queue
         if (waitingNames[x] != null && waitingNames[x].getFirstName().equalsIgnoreCase(customerName)) {
            waitingDetails += "Customer " + (x + 1) + "\n";
            waitingDetails += "\tFirst name           : " + waitingNames[x].getFirstName() + "\n";
            waitingDetails += "\tLast name            : " + waitingNames[x].getLastName() + "\n";
            waitingDetails += "\tVehicle number  : " + waitingNames[x].getVehicleNumber() + "\n";
            waitingDetails += "\tAmount of fuel   : " + waitingNames[x].getFuelAmount() + " litres\n";
            waitingDetails += "\n";
            isWaitingNameFound = true;
         }
      }
      //Error if customer not found in fuel queue
      if (!isQueueNameFound){
         queueDetails += "Customer not found in fuel queue\n";
      }

      //Error if customer not found in waiting queue.
      if (!isWaitingNameFound){
         waitingDetails += "Customer not found in waiting queue\n";
      }

      fuelQueues.setText(queueDetails);
      waitingQueue.setText(waitingDetails);
   }
}

