public class Transaction implements Comparable<Transaction> {   
   private int shares;
   private int price;
   
  public Transaction(int shares, int price) {
	  this.shares = shares;
	  this.price = price;
  }
  
  public int getShares() {
	  return shares;
  }
  
  public int getPrice() {
	  return price;
  }
  
  public void sellShares(int num) {
	  if(num < shares) {
		  shares -= num;
	  }
  }
  public void buyShares(int num) {
	  shares += num;
  }

@Override
public int compareTo(Transaction o) {
	return 0;
}
}
