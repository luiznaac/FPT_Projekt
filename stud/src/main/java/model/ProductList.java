package model;

import java.util.ArrayList;
import fpt.com.Product;

public class ProductList extends ArrayList<Product> implements fpt.com.ProductList {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean delete(Product product) {
		return remove(product.getId());
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

	@Override
	public String toString(){
		String products = "";
		for(Product p : this){
			products += p;
			products += "\n";
		}
		return products;
	}

}
