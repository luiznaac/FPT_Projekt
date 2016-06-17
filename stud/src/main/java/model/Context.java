package model;

import java.io.IOException;

import fpt.com.SerializableStrategy;

public class Context {
//	private int loadedProducts=50;
//	private int i;
//	private void setLoadedProducts(int loadedProducts){
//			this.loadedProducts=loadedProducts;
//	}
	private SerializableStrategy strategy;

	public Context(SerializableStrategy strategy){
		this.strategy = strategy;
	}

	public Product load() throws IOException{
		Product read = new Product();
		read = (Product)strategy.readObject();
//		++i;
//		if(i>loadedProducts){
//			throw (new IOException());
//		}
		if(read == null)
			throw(new IOException());
		System.out.println(read + " ADDED");
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
