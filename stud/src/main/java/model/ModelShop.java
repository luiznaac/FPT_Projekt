package model;

import javafx.collections.ModifiableObservableListBase;
import serialization.*;

public class ModelShop extends ModifiableObservableListBase<Product> {

	ProductList list;
	Context context;

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
				context = new Context(new BinaryStrategy());
				break;
		}
	}

	public void load(){
		ProductList read = context.load();
		if(!read.isEmpty()) //if there's nothin to load, the actual list will not be cleared
			list.clear();
		for(fpt.com.Product p : read){
			this.add((Product) p);
		}
	}

	public void save(){
		context.save(list);
	}

}
