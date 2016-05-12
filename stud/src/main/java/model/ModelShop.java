package model;

import javafx.collections.ModifiableObservableListBase;

public class ModelShop extends ModifiableObservableListBase<Product> {

	ProductList list = new ProductList();

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

}
