package problem4;

public class MainThread {

	public static void main(String[] args){
		Acquisition ac = new Acquisition(new WaitingQueue());
		Thread t1 = new Thread(ac);
		t1.start();
	}

}
