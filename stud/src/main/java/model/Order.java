package model;

import java.util.ArrayList;

import fpt.com.Product;


public class Order extends ArrayList<Product> implements fpt.com.Order {

	@Override
	public boolean delete(Product p) {
		return remove(p);
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
	public double getSum() {
		double sum = 0;
		for(Product temp : this){
			sum += temp.getPrice();
		}
		return sum;
	}

	@Override
	public int getQuantity() {
		int quantity = 0;
		for(Product temp : this){
			quantity += temp.getQuantity();
		}
		return quantity;
	}
}
