package com.example.task4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

/**
 * COPYRIGHT (C) 2022 Akindu Karunaratne (w1898951/20211364). All Rights Reserved.
 * JavaFX for a Fuel Queue Management System in a fuel center.
 * Solves Software Development 2 (4COSC010.3) Coursework 1 Task 4.
 *
 * @author Akindu Karunaratne
 * @version 1.0 2022-08-08.
 **/

public class Task4Application extends Application{
   @Override
   public void start(Stage stage) throws IOException {
      FXMLLoader fxmlLoader = new FXMLLoader(Task4Application.class.getResource("Task4.fxml"));
      Scene scene = new Scene(fxmlLoader.load(), 750, 550);
      stage.setTitle("Fuel Center");
      stage.setScene(scene);
      stage.show();
   }
   public static void main(String[] args) {
      launch();
   }
}