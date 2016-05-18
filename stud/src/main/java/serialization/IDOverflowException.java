package serialization;

public class IDOverflowException extends Exception {

	public IDOverflowException(){
		super("ID reached its maximum");
	}

}
