import java.io.Serializable;

public class WaitingQueue implements Serializable {

   //Attributes
   private int front;
   private int rear;
   private final int maxSize;
   private int numCustomers;
   private final Passenger[] waitingQueue;

   //Constructor
   public WaitingQueue(int maxSize) {
      this.maxSize = maxSize;
      this.waitingQueue = new Passenger[maxSize];
      this.front = 0;
      this.rear = -1;
      this.numCustomers = 0;
   }

   //Method to add customer to waiting queue
   public void enQueue(Passenger data) {
      rear = (rear + 1) % maxSize;
      waitingQueue[rear] = data; //Adding Passenger object to array index with rear pointer.
      numCustomers++; //Increases number of customers by 1
   }

   //Method to remove customer from waiting queue
   public Passenger deQueue() {
      Passenger temp = waitingQueue[front]; //Getting Passenger object from front pointer
      front = (front + 1) % maxSize;
      numCustomers--; //Decreases number of customers by 1

      return temp;
   }

   //Methods to check if waiting queue is empty or full
   public boolean isEmpty(){
      return (numCustomers == 0);
   }
   public boolean isFull(){
      return (numCustomers == maxSize);
   }

}
