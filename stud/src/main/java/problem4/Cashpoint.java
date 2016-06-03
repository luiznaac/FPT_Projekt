package problem4;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Lock;

public class Cashpoint implements Runnable {

	private ArrayList<Integer> customers;
	private int id;
	private static Balance balance = new Balance();
	private Lock lock;
	private Random rn;

	public Cashpoint(int id, Lock lock){
		customers = new ArrayList<>();
		rn = new Random();
		this.id = id;
		this.lock = lock;
	}

	@Override
	public void run(){
		//das Öffnen einer Kasse dauert 6 Sekunden
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e1) {
		}
		System.out.println("Kasse " + this.id + ": geöffnet");
		/*
		 * Es KÖNNTE passieren, dass es noch keinen Kunde in
		 * der Warteschlange gibt und dann würde es zur Schluss
		 * der Kasse führen, aber da das Öffnen einer Kasse
		 * genau 6 Sekunden dauert und das Beschaffung eines Kundes
		 * nur von 0 bis 2 Sekunden dauern kann, wird es einen
		 * Kunde immer geben, wenn der Thread zur Schleife ankommt.
		 * Also prüft es nur, ob alle Kunden abgearbeitet wurden
		*/
		while(!customers.isEmpty()){
			int value = customers.remove(0)*20;
			System.out.println("Kasse " + id + ": Abarbeiten eines Kundes");
			System.out.println("         Noch " + customers.size() + " Kunden warten");
			lock.lock();
			//die Abarbeitung eines Kundes kann von 6 bis 10 Sekunden dauern
			try {
				Thread.sleep(rn.nextInt(6000) + 4000);
			} catch (InterruptedException e) {
			}
			balance.update(this.id, value);
			lock.unlock();
		}
		System.out.println("Kasse " + id + ": geschlossen");
	}

	public void addCustomer(int data){
		customers.add(data);
		System.out.println("Kasse " + id + ": Kunde eingereiht");
	}

	public int lineLength(){
		return customers.size();
	}

	public int getId(){
		return id;
	}

}
