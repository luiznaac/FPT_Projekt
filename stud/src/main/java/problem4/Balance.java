package problem4;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Balance {

	private Map<Integer, Double> cashiers;

	public Balance(){
		cashiers = new TreeMap<>();
	}

	public void update(int id, double cash){
		//wenn es schon einen Wert auf diesem Key (id) gibt,
		//dann berechnet es
		if(cashiers.get(id) != null)
			cashiers.put(id, cashiers.get(id) + cash);
		//sonst, erzeugt es den Key (id)
		else cashiers.put(id, cash);
		print();
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
		//Erzeugung eines Maps, das die geordnete Liste beibehält
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
