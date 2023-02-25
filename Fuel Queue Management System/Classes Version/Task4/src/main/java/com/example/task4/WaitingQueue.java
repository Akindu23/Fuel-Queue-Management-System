package com.example.task4;

import java.io.Serializable;

public class WaitingQueue implements Serializable {
   private int front;
   private int rear;
   private final int maxSize;
   private static int numCustomers;
   private static Passenger[] waitingQueue;

   public WaitingQueue(int maxSize) {
      this.maxSize = maxSize;
      waitingQueue = new Passenger[maxSize];
      this.front = 0;
      this.rear = -1;
      numCustomers = 0;
   }
   public static Passenger[] getWaitingQueue(){
      return waitingQueue;
   }
   public void enQueue(Passenger data) {
      rear = (rear + 1) % maxSize;
      waitingQueue[rear] = data;
      numCustomers++;
   }

   public Passenger deQueue() {
      Passenger temp = waitingQueue[front];
      front = (front + 1) % maxSize;
      numCustomers--;

      return temp;
   }

   public static boolean isEmpty(){
      return (numCustomers == 0);
   }
   public boolean isFull(){
      return (numCustomers == maxSize);
   }

}
