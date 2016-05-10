package shop;

import javafx.collections.ModifiableObservableListBase;

public class ModelShop extends ModifiableObservableListBase<fpt.com.Product> {

	private ProductList products = new ProductList();

	@Override
	public Product get(int index) {
		return (Product)products.findProductById(index);
	}

	@Override
	public int size() {
		return products.size();
	}

	@Override
	protected void doAdd(int index, fpt.com.Product e) {
		products.add(e);
	}

	@Override
	protected fpt.com.Product doSet(int index, fpt.com.Product element) {
		return null;
	}

	@Override
	protected Product doRemove(int index) {
		Product product = (Product)products.findProductById(index);
		if(products.delete(product))
			return product;
		else return null;
	}

}
