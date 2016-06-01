package problem4;

import java.util.Random;

public class Acquisition implements Runnable {

	WaitingQueue wq;
	Random rn;

	public Acquisition(WaitingQueue wq){
		this.wq = wq;
		this.rn = new Random();
		System.out.println("Start der Kundenakquise");
	}

	@Override
	public void run() {
		while(!wq.stop()){
			try {
				Thread.sleep(rn.nextInt(2001));
			} catch (InterruptedException e) {
			}
			wq.put(rn.nextInt(20) + 1);
			System.out.println("Neue Kunde");
		}
		System.out.println("Ende der Kundenakquise");
	}

}
