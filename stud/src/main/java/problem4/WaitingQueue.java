package problem4;

import java.util.ArrayList;

public class WaitingQueue {

	boolean empty, stop;
	ArrayList<Cashpoint> cashiers;

	public WaitingQueue() {
		empty = true;
		stop = false;
		cashiers = new ArrayList<>();
	}

	public void put(int data){
		Cashpoint cpactual = null;
		if(cashiers.size() == 0){
			newCashpoint();
			cpactual = cashiers.get(0);
		}else {
			int min = 9;
			for(Cashpoint cp : cashiers){
				if(cp.lineLength() < min){
					cpactual = cp;
				}
			}
		}
		cpactual.addCustomer(data);
		if(cpactual.lineLength() == 6)
			newCashpoint();
		else if(!stop)
			stop = (cpactual.lineLength() > 8) ? true:false;
	}

	private void newCashpoint(){
		if(cashiers.size() < 6){
			Cashpoint cptemp = new Cashpoint(cashiers.size() + 1);
			cashiers.add(cptemp);
			Thread ttemp = new Thread(cptemp);
			ttemp.start();
		}
	}

	public boolean stop(){
		return stop;
	}

}
