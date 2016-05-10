package model;

import javafx.collections.ModifiableObservableListBase;

public class ModelShop extends ModifiableObservableListBase<fpt.com.Product> {

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
	protected void doAdd(int index, fpt.com.Product e) {
		list.add(index, e);
	}

	@Override
	protected fpt.com.Product doSet(int index, fpt.com.Product element) {
		return null;
	}

	@Override
	protected Product doRemove(int index) {
		return (Product) list.remove(index);
	}

}
