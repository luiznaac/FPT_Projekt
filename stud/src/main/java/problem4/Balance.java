package problem4;

import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Balance {

	private Map<Integer, Double> cashiers;
	private boolean busy = false;
	private Random rn = new Random();

	public Balance(){
		cashiers = new TreeMap<>();
	}

	//wenn ein Thread schon hier ist, muss die anderen Warten
	public synchronized void update(int id, double cash){
		//prüft ob ein Thread schon hier ist (busy = true)
		while(busy){
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		busy = true;
		//wenn es schon einen Wert auf diesem Key (id) gibt,
		//dann berechnet es
		if(cashiers.get(id) != null)
			cashiers.put(id, cashiers.get(id) + cash);
		//sonst, erzeugt es den Key (id)
		else cashiers.put(id, cash);
		//die Abarbeitung eines Kundes kann von 6 bis 10 Sekunden dauern
		try {
			Thread.sleep(rn.nextInt(6000) + 4000);
		} catch (InterruptedException e) {
		}
		print();
		busy = false;
		notify();
	}

	private void print(){
		//Erzeugung eines Vergleichers, um die Kassen nach ihrer Umsätze einzuordnen
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer k1, Integer k2) {
				double result = cashiers.get(k1) - cashiers.get(k2);
				if(result < 0)
					return 1;
				else
					return -1;
			}
		};
		//Erzeugung eines Map, das die geordnete Liste beibehält
		Map<Integer, Double> sortedCashiers = new TreeMap<Integer, Double>(comparator);
	    sortedCashiers.putAll(cashiers);
	    //da der Druck aller Kassen dauern kann, muss System.out gesperrt werden
	    synchronized(System.out){
		    System.out.print("Bilanz: ");
		    for (Map.Entry<Integer, Double> entry : sortedCashiers.entrySet()) {
		        System.out.print("Kasse " + entry.getKey() + ":  €" + entry.getValue() + ";");
		    }
		    System.out.println("");
	    }
	}

}
