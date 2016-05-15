package model;

import fpt.com.SerializableStrategy;
import serialization.*;

public class Context {

	private SerializableStrategy strategy;

	public Context(SerializableStrategy strategy){
		this.strategy = strategy;
	}

	public ProductList load(){
		return ((BinaryStrategy)strategy).readList();
	}

	public void save(ProductList products){
		((BinaryStrategy)strategy).writeList(products);
	}

}
