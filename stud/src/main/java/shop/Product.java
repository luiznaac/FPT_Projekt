package shop;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class Product implements fpt.com.Product {

	private LongProperty id = new SimpleLongProperty();
	private DoubleProperty price = new SimpleDoubleProperty();
	private IntegerProperty quantity = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();

	public Product(long id, double price, int quantity, String name){
		setId(id);
		setPrice(price);
		setQuantity(quantity);
		setName(name);
	}

	Product(double price, int quantity, String name){
		setPrice(price);
		setQuantity(quantity);
		setName(name);
	}

	@Override
	public long getId() {
		return id.get();
	}

	@Override
	public void setId(long id) {
		this.id.set(id);
	}

	@Override
	public double getPrice() {
		return price.get();
	}

	@Override
	public void setPrice(double price) {
		this.price.set(price);
	}

	@Override
	public int getQuantity() {
		return quantity.get();
	}

	@Override
	public void setQuantity(int quantity) {
		this.quantity.set(quantity);
	}

	@Override
	public String getName() {
		return name.get();
	}

	@Override
	public void setName(String name) {
		this.name.set(name);
	}

	@Override
	public ObservableValue<String> nameProperty() {
		return name;
	}

	@Override
	public ObservableValue<Number> priceProperty() {
		return price;
	}

	@Override
	public ObservableValue<Number> quantityProperty() {
		return quantity;
	}

	@Override
	public String toString(){
		return getName() + " | ‎€" + getPrice() + " | " + getQuantity();
	}

}
