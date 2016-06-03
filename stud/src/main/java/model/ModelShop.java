package model;

import javafx.collections.ModifiableObservableListBase;
import serialization.*;

public class ModelShop extends ModifiableObservableListBase<Product> {

	private ProductList list;
	private Context context;

	public ModelShop(){
		list = new ProductList();
	}

	@Override
	public Product get(int index) {
		return (Product) list.get(index);
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	protected void doAdd(int index, Product e) {
		list.add(index, e);
	}

	@Override
	protected Product doSet(int index, Product element) {
		return null;
	}

	@Override
	protected Product doRemove(int index) {
		return (Product) list.remove(index);
	}

	public void setStrategy(int option){
		switch(option){
			case 0:
				context = new Context(new BinaryStrategy("products.ser"));
				break;
			case 1:
				context = new Context(new XMLStrategy("products.xml"));
				break;
			case 2:
				context = new Context(new XStreamStrategy("products.xml"));
				break;
		}
	}

	public void load(){
		ProductList read = new ProductList();
		boolean flag = true;
		while(flag){
			try {
				read.add((Product)context.load());
			} catch (Exception e) {
				flag = false;
			}
		}
		if(!read.isEmpty()){
			list.clear();
			for(fpt.com.Product p : read)
				this.add((Product) p);
		}
		context.close();
	}

	public void save(){
		for(fpt.com.Product p : list)
			context.save((Product)p);
		context.close();
	}

}
