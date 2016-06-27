package model;

import java.io.IOException;
import fpt.com.SerializableStrategy;

public class Context {

	private SerializableStrategy strategy;

	public Context(SerializableStrategy strategy){
		this.strategy = strategy;
	}

	public Product load() throws IOException{
		Product read = new Product();
		read = (Product)strategy.readObject();
		System.out.println(read.getId());
		if(read == null)
			throw(new IOException());
		return read;
	}

	public void save(Product product){
		try {
			strategy.writeObject(product);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void close(){
		try {
			strategy.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
