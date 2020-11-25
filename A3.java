
/** 
 * COMP 2503 Fall 2019 Assignment 3 
 * 
 * This program reads a list of buy and sell transactions and 
 * computes the total capital gain in FIFO and LIFO order. 
 * 
 * It uses a Stack class and a Queue class, 
 * both of which are implemented using a Singly Linked List class.
 * 
 * Last updated by @author Maryam Elahi
 * @date Fall 2019
*/

import java.util.Scanner; // Worked on with Soren Bradley
public class A3
{
   private Scanner inp = new Scanner(System.in);
   private int capitalGainFIFO = 0;
   private int capitalGainLIFO = 0;
   private Queue<Transaction> transQ = new Queue<>();
   private Stack<Transaction> transS = new Stack<>();

   public static void main(String[] args)
   {
      A3 a3 = new A3();
      a3.run();
   }
   
   private int getCapitalGGOwned (Transaction owned, Transaction sell) {
	   int sumOfBought = owned.getPrice() * sell.getShares();
	   int sumToSell = sell.getPrice() * sell.getShares();
	   return sumToSell - sumOfBought;
   }
   
   private int getCapitalGGSell(Transaction owned, Transaction sell) {
	   int sumOfBought = owned.getPrice() * owned.getShares();
	   int sumToSell = sell.getPrice() * sell.getShares();
	   return sumToSell - sumOfBought;
   }
   
   private void sellFIFO(Transaction sell) {
	   int selling = sell.getShares();
	   int owned = transQ.peak().getShares();
	   
	   if(owned < selling) {
		   capitalGainFIFO += getCapitalGGSell(transQ.dequeue(), sell);
		   sell.sellShares(owned);
		   sellFIFO(sell);
	   } else if(owned > selling) {
		   capitalGainFIFO += getCapitalGGOwned(transQ.peak(), sell);
		   transQ.peak().sellShares(selling);	  
	   } else {
		   capitalGainFIFO += getCapitalGGOwned(transQ.dequeue(),sell);
	   }
   }
   
   private void sellLIFO(Transaction sell) {
	   int selling = sell.getShares();
	   int owned = transS.peak().getShares();
	   
	   if(owned < selling) {
		   capitalGainLIFO += getCapitalGGSell(transS.pop(), sell);
		   sell.sellShares(owned);
		   sellLIFO(sell);
	   } else if(owned > selling) {
		   capitalGainLIFO += getCapitalGGOwned(transS.peak(), sell);
		   transS.peak().sellShares(selling);	  
	   } else {
		   capitalGainLIFO += getCapitalGGOwned(transS.pop(),sell);
	   }
	   
   }
   
   private boolean buy(String line) {
	   return line.toUpperCase().contains("BUY");
   }
   
   private boolean sell(String line) {
	   return line.toUpperCase().contains("SELL");
   }
   
   private void run() 
   {
      String line;
      while (inp.hasNext()) 
      {
         line = inp.nextLine();
         if(!line.isEmpty()) {
        	 line = line.trim().replaceAll("[ ]{2,}", " ");
        	 int space = line.indexOf(" ");
        	 String w = line.substring(0, space).trim();
        	 
        	 line = line.substring(space);
        	 
        	 String n = line.substring(0,line.length()).trim();
        	 int one = Integer.parseInt(n.substring(0,n.indexOf(" ")).trim());
        	 int two = Integer.parseInt(n.substring(n.indexOf(" "), n.length()).trim());
        	 
        	 Transaction d = new Transaction(one, two);
        	 Transaction d1 = new Transaction(one, two);
        	 
        	 if(buy(w)) {
        		transS.push(d); 
        		transQ.enqueue(d1);
        	 }
        	 
        	 if(sell(w)) {
        		 sellLIFO(d);
        		 sellFIFO(d1);
        	 }
        	 
         }
      }
      System.out.println("FIFO Capital Gain: " + capitalGainFIFO);
      System.out.println("LIFO Capital Gain: " + capitalGainLIFO);
   }   
}
