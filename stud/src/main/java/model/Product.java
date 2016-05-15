package model;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.DecimalFormat;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class Product implements fpt.com.Product, java.io.Externalizable {

	private long id;
	private StringProperty name = new SimpleStringProperty();
	private DoubleProperty price = new SimpleDoubleProperty();
	private IntegerProperty quantity = new SimpleIntegerProperty();

	public Product(){

	}

	public Product(String name, double price, int quantity){
		setPrice(price);
		setQuantity(quantity);
		setName(name);
	}

	public Product(long id, String name, double price, int quantity){
		this(name, price, quantity);
		setId(id);
	}

	/**************
	 * Methoden von fpt.com.Product
	 *
	 **************/

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
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
		return getName() + " | ‎€" + (new DecimalFormat("0.00")).format(getPrice()) + " | " + getQuantity();
	}

	/**************
	 * Methoden von java.io.Externalizable
	 *
	 **************/

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeLong(getId());
		out.writeObject(getName());
		out.writeObject(getPrice());
		out.writeObject(getQuantity());
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.setId((Long)in.readLong());
		this.setName((String) in.readObject());
		this.setPrice((Double) in.readObject());
		this.setQuantity((Integer) in.readObject());
	}

}
