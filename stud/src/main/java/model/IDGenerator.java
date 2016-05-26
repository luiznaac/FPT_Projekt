package model;

import serialization.IDOverflowException;

public class IDGenerator {

	private long gen;

	public IDGenerator(){
		gen = 0;
	}

	public long getId() throws IDOverflowException {
		gen++;
		if(gen > 999999){
			gen--;
			throw new IDOverflowException();
		}
		return gen;
	}

}
