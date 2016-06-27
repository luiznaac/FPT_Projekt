package model;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.persistence.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import org.apache.openjpa.persistence.*;
import org.apache.openjpa.persistence.jdbc.Strategy;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAliasType;

@XStreamAliasType("ware")
@XStreamAlias("ware")
@Entity()
@Table(name = "products")

public class Product implements fpt.com.Product, java.io.Externalizable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "products_SEQ")
	private long id;

	@Persistent
	@Strategy("fpt.com.db.StringPropertyValueHandler")
	private SimpleStringProperty name;

	@Persistent
	@Strategy("fpt.com.db.DoublePropertyValueHandler")
	private SimpleDoubleProperty price;

	@Persistent
	@Strategy("fpt.com.db.IntegerPropertyValueHandler")
	private SimpleIntegerProperty quantity;

	public Product(){
		name = new SimpleStringProperty();
		price = new SimpleDoubleProperty();
		quantity = new SimpleIntegerProperty();
	}

	public Product(String name, double price, int quantity){
		this.name = new SimpleStringProperty(name);
		this.price = new SimpleDoubleProperty(price);
		this.quantity = new SimpleIntegerProperty(quantity);
	}

	public Product(long id, String name, double price, int quantity){
		this.id = id;
		this.name = new SimpleStringProperty(name);
		this.price = new SimpleDoubleProperty(price);
		this.quantity = new SimpleIntegerProperty(quantity);
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
		this.setId((Long) in.readLong());
		this.setName((String) in.readObject());
		this.setPrice((Double) in.readObject());
		this.setQuantity((Integer) in.readObject());
	}

	@Override
	public String toString() {
		return getName() + " | " + getPrice() + " | " + getQuantity();
	}

}
