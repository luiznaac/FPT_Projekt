package model;

import java.util.ArrayList;

import fpt.com.Product;


public class Order extends ArrayList<Product> implements fpt.com.Order {

	private static final long serialVersionUID = 1L;
	private Double sum = 0.0;

	@Override
	public boolean add(Product e){
		if(super.add(e)){
			sum += e.getQuantity() * e.getPrice();
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(Product p) {
		if(super.remove(p)){
			sum -= p.getQuantity() * p.getPrice();
			return true;
		}
		return false;
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

	@Override
	public String toString() {
		String msg = "";
		for(Product p : this){
			msg += p.getName() + " " + p.getQuantity() + " " + p.getQuantity()*p.getPrice() + "EUR\n";
		}
		return msg;
	}

}
