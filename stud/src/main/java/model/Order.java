package shop;

import java.util.Iterator;

import fpt.com.Product;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Order implements fpt.com.Order {

	private ProductList list;
	private IntegerProperty quantity = new SimpleIntegerProperty();
	private DoubleProperty sum = new SimpleDoubleProperty();

	public Order(){
		list = new ProductList();
		quantity.set(0);
		sum.set(0);
	}

	@Override
	public Iterator<Product> iterator() {
		return list.iterator();
	}

	@Override
	public boolean add(Product e) {
		if(list.add(e)){
			sum.add(e.getPrice());
			quantity.add(1);
			return true;
		}
		else return false;
	}

	@Override
	public boolean delete(Product p) {
		if(list.delete(p)){
			sum.subtract(p.getPrice());
			quantity.subtract(1);
			return true;
		}
		else return false;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public Product findProductById(long id) {
		return list.findProductById(id);
	}

	@Override
	public Product findProductByName(String name) {
		return list.findProductByName(name);
	}

	@Override
	public double getSum() {
		return sum.get();
	}

	@Override
	public int getQuantity() {
		return quantity.get();
	}

}
