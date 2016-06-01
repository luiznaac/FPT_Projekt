package problem4;

import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Balance {

	TreeMap<Integer, Double> cashiers;
	boolean busy = false;
	Random rn = new Random();

	public Balance(){
		cashiers = new TreeMap<>();
	}

	public synchronized void update(int id, double cash){
		while(busy){
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		busy = true;
		if(cashiers.get(id) != null)
			cashiers.put(id, cashiers.get(id) + cash);
		else cashiers.put(id, cash);
		try {
			Thread.sleep(rn.nextInt(6001) + 4000);
		} catch (InterruptedException e) {
		}
		print();
		busy = false;
		notify();
	}

	private void print(){
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer k1, Integer k2) {
				double result = cashiers.get(k1) - cashiers.get(k2);
				if(result < 0)
					return 1;
				else if(result > 0)
					return -1;
				else
					return 0;
			}
		};
		Map<Integer, Double> sortedCashiers = new TreeMap<Integer, Double>(comparator);
	    sortedCashiers.putAll(cashiers);
	    System.out.print("Bilanz: ");
	    for (Map.Entry<Integer, Double> entry : sortedCashiers.entrySet()) {
	        System.out.print("Kasse " + entry.getKey() + ":  €" + entry.getValue() + ";");
	    }
	    System.out.println("");
	}

}
