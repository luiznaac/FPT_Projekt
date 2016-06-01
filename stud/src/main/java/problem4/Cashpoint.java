package problem4;

import java.util.ArrayList;

public class Cashpoint implements Runnable {

	ArrayList<Integer> customers;
	int id;
	boolean added = false;
	static Balance balance = new Balance();

	public Cashpoint(int id){
		customers = new ArrayList<>();
		this.id = id;
	}

	@Override
	public void run(){
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e1) {
		}
		System.out.println("Kasse " + this.id + ": geöffnet");
		while(!added || !customers.isEmpty()){
			if(!customers.isEmpty()){
				int value = customers.remove(0)*20;
				System.out.println("Kasse " + id + ": Abarbeiten eines Kundes");
				System.out.println("         Noch " + customers.size() + " Kunden warten");
				balance.update(this.id, value);
			}
		}
		System.out.println("Kasse " + id + ": geschlossen");
	}

	public void addCustomer(int data){
		customers.add(data);
		System.out.println("Kasse " + id + ": Kunde eingereiht");
		added = true;
	}

	public int lineLength(){
		return customers.size();
	}

	public int getId(){
		return id;
	}

}
