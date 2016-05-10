package shop;

import java.util.ArrayList;
import java.util.Iterator;

import fpt.com.Product;

public class ProductList extends ArrayList<Product> implements fpt.com.ProductList {

	public ProductList(){
		super();
	}

	@Override
	public Iterator<Product> iterator() {
		return super.iterator();
	}

	@Override
	public boolean delete(Product product) {
		return super.remove(product);
	}

	@Override
	public boolean add(Product e){
		e.setId(size());
		return super.add(e);
	}

	@Override
	public int size() {
		return super.size();
	}

	@Override
	public Product findProductById(long id) {
		for(Product temp : this)
			if(temp.getId() == id) return temp;
		return null;
	}

	@Override
	public Product findProductByName(String name) {
		for(Product temp : this)
			if(temp.getName() == name) return temp;
		return null;
	}

}
