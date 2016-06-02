package problem4;

import java.util.Random;

public class Acquisition implements Runnable {

	private WaitingQueue wq;
	private Random rn;

	public Acquisition(WaitingQueue wq){
		this.wq = wq;
		this.rn = new Random();
	}

	@Override
	public void run() {
		System.out.println("Start der Kundenakquise");
		//prüft ob die Beschaffung von Kunden gestoppt werden muss
		while(!wq.stop()){
			//es kann von 0 bis 2 Sekunden dauern, bis ein neuer Kunde fertig ist
			try {
				Thread.sleep(rn.nextInt(2001));
			} catch (InterruptedException e) {
			}

			System.out.println("Neue Kunde");
			//Die Kunden sind Werten, die bedeuten,
			//wie viele Artikel sie kaufen werden
			wq.put(rn.nextInt(20) + 1); //zufälliger Wert.
		}
		System.out.println("Ende der Kundenakquise");
	}

}
