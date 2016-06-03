package problem4;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WaitingQueue {

	private final int MAX_CASHPOINTS = 6; 		//Max Anzahl der Kassen
	private final int MAX_LINE_CUSTOMERS = 8; 	//Max Anzahl der Kunden pro Kasse
	private final int MIN_LINE_CUSTOMERS = 6;	//Min Anzahl der Kunden, um eine neue Kasse zu öffnen
	private boolean stop;
	private ArrayList<Cashpoint> cashiers;
	private Lock lock;

	public WaitingQueue() {
		stop = false;
		cashiers = new ArrayList<>();
		lock = new ReentrantLock();
	}

	public void put(int data){
		Cashpoint cpactual = null;
		//es erzeugt die erste Kasse
		if(cashiers.size() == 0){
			newCashpoint();
			cpactual = cashiers.get(0);
		//sonst wenn es mindestens eine Kasse schon gibt
		}else {
			//es sucht nach der Kasse, die die kleine Anzahl der Kunden hat
			int min = MAX_LINE_CUSTOMERS;
			for(Cashpoint cp : cashiers){
				if(cp.lineLength() < min){
					cpactual = cp;
					min = cp.lineLength();
				}
			}
		}
		cpactual.addCustomer(data);
		//wenn die min Anzahl der Kunden erreicht ist,
		//muss dann eine neue Kasse geöffnet werden
		if(cpactual.lineLength() == MAX_CASHPOINTS)
			newCashpoint();
		//wenn die max Anzahl der Kunden pro Kasse erreicht ist,
		//muss die Beschaffung von Kunden gestoppt werden
		else if(!stop)
			stop = (cpactual.lineLength() == MAX_LINE_CUSTOMERS) ? true:false;
	}

	private void newCashpoint(){
		if(cashiers.size() < MIN_LINE_CUSTOMERS){
			Cashpoint cptemp = new Cashpoint(cashiers.size() + 1, lock);
			cashiers.add(cptemp);
			Thread ttemp = new Thread(cptemp);
			ttemp.start();
		}
	}

	public boolean stop(){
		return stop;
	}

}
