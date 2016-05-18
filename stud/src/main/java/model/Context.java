package model;

import fpt.com.SerializableStrategy;
import serialization.*;

public class Context {

	private SerializableStrategy strategy;
	int option;

	public Context(SerializableStrategy strategy, int option){
		this.strategy = strategy;
		this.option = option;
	}

	public ProductList load(){
		return ((BinaryStrategy)strategy).readList();
	}

	public void save(ProductList products){
		switch(option){
			case 0:
				((BinaryStrategy)strategy).writeList(products);
				break;
			case 1:
				((XMLStrategy)strategy).writeList(products);
				break;
			case 2:
				((XStreamStrategy)strategy).writeList(products);
				break;

		}

	}

}
